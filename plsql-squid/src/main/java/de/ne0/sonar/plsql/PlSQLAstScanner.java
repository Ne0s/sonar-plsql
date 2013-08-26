package de.ne0.sonar.plsql;

import java.io.File;
import java.util.Collection;

import org.sonar.squid.api.SourceCode;
import org.sonar.squid.api.SourceFile;
import org.sonar.squid.api.SourceFunction;
import org.sonar.squid.api.SourceProject;
import org.sonar.squid.indexer.QueryByType;
import org.sonar.sslr.parser.LexerlessGrammar;

import com.google.common.base.Charsets;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.impl.Parser;
import com.sonar.sslr.squid.AstScanner;
import com.sonar.sslr.squid.SourceCodeBuilderCallback;
import com.sonar.sslr.squid.SourceCodeBuilderVisitor;
import com.sonar.sslr.squid.SquidAstVisitor;
import com.sonar.sslr.squid.SquidAstVisitorContextImpl;
import com.sonar.sslr.squid.metrics.CommentsVisitor;
import com.sonar.sslr.squid.metrics.CounterVisitor;
import com.sonar.sslr.squid.metrics.LinesOfCodeVisitor;
import com.sonar.sslr.squid.metrics.LinesVisitor;

import de.ne0.sonar.plsql.api.PlSQLMetric;
import de.ne0.sonar.plsql.metrics.ComplexityVisitor;
import de.ne0.sonar.plsql.parser.PlSQLGrammar;
import de.ne0.sonar.plsql.parser.PlSQLParser;

public final class PlSQLAstScanner {

	private PlSQLAstScanner() {	}
	
	 /**
	   * Helper method for testing checks without having to deploy them on a Sonar instance.
	   */
	  public static SourceFile scanSingleFile(File file, SquidAstVisitor<LexerlessGrammar>... visitors) {
	    if (!file.isFile()) {
	      throw new IllegalArgumentException("File '" + file + "' not found.");
	    }
	    AstScanner<LexerlessGrammar> scanner = create(new PlSQLConfiguration(Charsets.UTF_8), visitors);
	    scanner.scanFile(file);
	    Collection<SourceCode> sources = scanner.getIndex().search(new QueryByType(SourceFile.class));
	    if (sources.size() != 1) {
	      throw new IllegalStateException("Only one SourceFile was expected whereas " + sources.size() + " has been returned.");
	    }
	    return (SourceFile) sources.iterator().next();
	  }
	
	 public static AstScanner<LexerlessGrammar> create(PlSQLConfiguration conf, SquidAstVisitor<LexerlessGrammar>... visitors) {
		    final SquidAstVisitorContextImpl<LexerlessGrammar> context = new SquidAstVisitorContextImpl<LexerlessGrammar>(new SourceProject("PlSQL Project"));
		    final Parser<LexerlessGrammar> parser = PlSQLParser.create(conf);

		    AstScanner.Builder<LexerlessGrammar> builder = AstScanner.<LexerlessGrammar> builder(context).setBaseParser(parser);

		    /* Metrics */
		    builder.withMetrics(PlSQLMetric.values());

		    /* Comments */
		    builder.setCommentAnalyser(new PlSQLCommentAnalyser());

		    /* Files */
		    builder.setFilesMetric(PlSQLMetric.FILES);

		    
		    
		    /* Functions */
		    builder.withSquidAstVisitor(CounterVisitor.<LexerlessGrammar> builder()
		        .setMetricDef(PlSQLMetric.FUNCTIONS)
		        .subscribeTo(PlSQLGrammar.FUNCTION_DECLARATION, PlSQLGrammar.FUNCTION_BODY)
		        .build());

		    builder.withSquidAstVisitor(new SourceCodeBuilderVisitor<LexerlessGrammar>(new SourceCodeBuilderCallback() {
		      public SourceCode createSourceCode(SourceCode parentSourceCode, AstNode astNode) {
		        AstNode identifier = astNode.getFirstChild(PlSQLGrammar.IDENTIFIER_NAME);
		        final String functionName = identifier == null ? "anonymous" : identifier.getTokenValue();
		        final String fileKey = parentSourceCode.isType(SourceFile.class) ? parentSourceCode.getKey() : parentSourceCode.getParent(SourceFile.class).getKey();
		        SourceFunction function = new SourceFunction(fileKey + ":" + functionName + ":" + astNode.getToken().getLine() + ":" + astNode.getToken().getColumn());
		        function.setStartAtLine(astNode.getTokenLine());
		        return function;
		      }
		    }, PlSQLGrammar.FUNCTION_BODY));

		    /* Metrics */
		    builder.withSquidAstVisitor(new LinesVisitor<LexerlessGrammar>(PlSQLMetric.LINES));
		    builder.withSquidAstVisitor(new LinesOfCodeVisitor<LexerlessGrammar>(PlSQLMetric.LINES_OF_CODE));
		    builder.withSquidAstVisitor(CommentsVisitor.<LexerlessGrammar> builder().withCommentMetric(PlSQLMetric.COMMENT_LINES)
		        .withNoSonar(true)
		        .withIgnoreHeaderComment(conf.getIgnoreHeaderComments())
		        .build());
		    builder.withSquidAstVisitor(CounterVisitor.<LexerlessGrammar> builder()
		        .setMetricDef(PlSQLMetric.STATEMENTS)
		        .subscribeTo(
		            PlSQLGrammar.INSERT_STATEMENT,
		            PlSQLGrammar.RETURN_STATEMENT,
		            PlSQLGrammar.ASSIGNMENT_STATEMENT,
		            PlSQLGrammar.IF_STATEMENT,
		            PlSQLGrammar.CALL_STATEMENT,
		            PlSQLGrammar.LOOP_STATEMENT,
		            PlSQLGrammar.CASE_STATEMENT,
		            PlSQLGrammar.BLOCK_STATEMENT
//		            PlSQLGrammar.WITH_STATEMENT,
//		            PlSQLGrammar.SWITCH_STATEMENT,
//		            PlSQLGrammar.THROW_STATEMENT,
//		            PlSQLGrammar.TRY_STATEMENT,
//		            PlSQLGrammar.DEBUGGER_STATEMENT
		            )
		        .build());

		    builder.withSquidAstVisitor(new ComplexityVisitor());

		    /* External visitors (typically Check ones) */
		    for (SquidAstVisitor<LexerlessGrammar> visitor : visitors) {
		      if (visitor instanceof CharsetAwareVisitor) {
		        ((CharsetAwareVisitor) visitor).setCharset(conf.getCharset());
		      }
		      builder.withSquidAstVisitor(visitor);
		    }

		    return builder.build();
		  }
}
