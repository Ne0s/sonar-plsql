package de.ne0.sonar.plsql.plugin;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.checks.AnnotationCheckFactory;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.measures.PersistenceMode;
import org.sonar.api.measures.RangeDistributionBuilder;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.resources.File;
import org.sonar.api.resources.InputFileUtils;
import org.sonar.api.resources.Project;
import org.sonar.api.rules.Violation;
import org.sonar.squid.api.CheckMessage;
import org.sonar.squid.api.SourceCode;
import org.sonar.squid.api.SourceFile;
import org.sonar.squid.api.SourceFunction;
import org.sonar.squid.indexer.QueryByParent;
import org.sonar.squid.indexer.QueryByType;
import org.sonar.sslr.parser.LexerlessGrammar;

import com.google.common.collect.Lists;
import com.sonar.sslr.squid.AstScanner;
import com.sonar.sslr.squid.SquidAstVisitor;

import de.ne0.sonar.plsql.PlSQLAstScanner;
import de.ne0.sonar.plsql.PlSQLConfiguration;
import de.ne0.sonar.plsql.api.PlSQLMetric;
import de.ne0.sonar.plsql.checks.CheckList;
import de.ne0.sonar.plsql.metrics.FileLinesVisitor;
import de.ne0.sonar.plsql.plugin.core.PlSQL;

public class PlSQLSquidSensor  implements Sensor {

	 private static final Number[] FUNCTIONS_DISTRIB_BOTTOM_LIMITS = {1, 2, 4, 6, 8, 10, 12, 20, 30};
	  private static final Number[] FILES_DISTRIB_BOTTOM_LIMITS = {0, 5, 10, 20, 30, 60, 90};

	  private final AnnotationCheckFactory annotationCheckFactory;
	  private final FileLinesContextFactory fileLinesContextFactory;

	  private Project project;
	  private SensorContext context;
	  private AstScanner<LexerlessGrammar> scanner;

	  public PlSQLSquidSensor(RulesProfile profile, FileLinesContextFactory fileLinesContextFactory) {
	    this.annotationCheckFactory = AnnotationCheckFactory.create(profile, CheckList.REPOSITORY_KEY, CheckList.getChecks());
	    this.fileLinesContextFactory = fileLinesContextFactory;
	  }

	  public boolean shouldExecuteOnProject(Project project) {
	    return PlSQL.KEY.equals(project.getLanguageKey());
	  }

	  public void analyse(Project project, SensorContext context) {
	    this.project = project;
	    this.context = context;

	    Collection<SquidAstVisitor<LexerlessGrammar>> squidChecks = annotationCheckFactory.getChecks();
	    List<SquidAstVisitor<LexerlessGrammar>> visitors = Lists.newArrayList(squidChecks);

	    //TODO: enable
	    visitors.add(new FileLinesVisitor(project, fileLinesContextFactory));
	    
	    this.scanner = PlSQLAstScanner.create(createConfiguration(project), visitors.toArray(new SquidAstVisitor[visitors.size()]));
	    scanner.scanFiles(InputFileUtils.toFiles(project.getFileSystem().mainFiles(PlSQL.KEY)));

	    Collection<SourceCode> squidSourceFiles = scanner.getIndex().search(new QueryByType(SourceFile.class));
	    save(squidSourceFiles);
	  }

	  private PlSQLConfiguration createConfiguration(Project project) {
	    return new PlSQLConfiguration(project.getFileSystem().getSourceCharset());
	  }

	  private void save(Collection<SourceCode> squidSourceFiles) {
	    for (SourceCode squidSourceFile : squidSourceFiles) {
	      SourceFile squidFile = (SourceFile) squidSourceFile;

	      File sonarFile = File.fromIOFile(new java.io.File(squidFile.getKey()), project);

	      saveFilesComplexityDistribution(sonarFile, squidFile);
	      saveFunctionsComplexityDistribution(sonarFile, squidFile);
	      saveMeasures(sonarFile, squidFile);
	      saveViolations(sonarFile, squidFile);
	    }
	  }

	  private void saveMeasures(File sonarFile, SourceFile squidFile) {
	    context.saveMeasure(sonarFile, CoreMetrics.FILES, squidFile.getDouble(PlSQLMetric.FILES));
	    context.saveMeasure(sonarFile, CoreMetrics.LINES, squidFile.getDouble(PlSQLMetric.LINES));
	    context.saveMeasure(sonarFile, CoreMetrics.NCLOC, squidFile.getDouble(PlSQLMetric.LINES_OF_CODE));
	  
	    //TODO: enable?
//	    context.saveMeasure(sonarFile, CoreMetrics.FUNCTIONS, squidFile.getDouble(PlSQLMetric.FUNCTIONS));
	    context.saveMeasure(sonarFile, CoreMetrics.STATEMENTS, squidFile.getDouble(PlSQLMetric.STATEMENTS));
	    context.saveMeasure(sonarFile, CoreMetrics.COMPLEXITY, squidFile.getDouble(PlSQLMetric.COMPLEXITY));
	    context.saveMeasure(sonarFile, CoreMetrics.COMMENT_LINES, squidFile.getDouble(PlSQLMetric.COMMENT_LINES));
	  }

	  private void saveFunctionsComplexityDistribution(File sonarFile, SourceFile squidFile) {
	    Collection<SourceCode> squidFunctionsInFile = scanner.getIndex().search(new QueryByParent(squidFile), new QueryByType(SourceFunction.class));
	    RangeDistributionBuilder complexityDistribution = new RangeDistributionBuilder(CoreMetrics.FUNCTION_COMPLEXITY_DISTRIBUTION, FUNCTIONS_DISTRIB_BOTTOM_LIMITS);
	    for (SourceCode squidFunction : squidFunctionsInFile) {
	      complexityDistribution.add(squidFunction.getDouble(PlSQLMetric.COMPLEXITY));
	    }
	    context.saveMeasure(sonarFile, complexityDistribution.build().setPersistenceMode(PersistenceMode.MEMORY));
	  }

	  private void saveFilesComplexityDistribution(File sonarFile, SourceFile squidFile) {
	    RangeDistributionBuilder complexityDistribution = new RangeDistributionBuilder(CoreMetrics.FILE_COMPLEXITY_DISTRIBUTION, FILES_DISTRIB_BOTTOM_LIMITS);
	    complexityDistribution.add(squidFile.getDouble(PlSQLMetric.COMPLEXITY));
	    context.saveMeasure(sonarFile, complexityDistribution.build().setPersistenceMode(PersistenceMode.MEMORY));
	  }

	  private void saveViolations(File sonarFile, SourceFile squidFile) {
	    Collection<CheckMessage> messages = squidFile.getCheckMessages();
	    if (messages != null) {
	      for (CheckMessage message : messages) {
	        Violation violation = Violation.create(annotationCheckFactory.getActiveRule(message.getCheck()), sonarFile)
	            .setLineId(message.getLine())
	            .setMessage(message.getText(Locale.ENGLISH));
	        context.saveViolation(violation);
	      }
	    }
	  }

	  @Override
	  public String toString() {
	    return getClass().getSimpleName();
	  }

}
