package de.ne0.sonar.plsql.plugin.core;

import org.sonar.api.batch.AbstractSourceImporter;
import org.sonar.api.batch.Phase;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.resources.InputFileUtils;
import org.sonar.api.resources.ProjectFileSystem;

@Phase(name = Phase.Name.PRE)
public class PlSQLSourceImporter  extends AbstractSourceImporter {
	 public PlSQLSourceImporter(PlSQL plsql) {
		    super(plsql);
		  }

		  protected void analyse(ProjectFileSystem fileSystem, SensorContext context) {
		    parseDirs(context, InputFileUtils.toFiles(fileSystem.mainFiles(PlSQL.KEY)), fileSystem.getSourceDirs(), false, fileSystem.getSourceCharset());
		  }

		  @Override
		  public String toString() {
		    return getClass().getSimpleName();
		  }
}
