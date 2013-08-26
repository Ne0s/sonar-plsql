package de.ne0.sonar.plsql.plugin;

import java.util.List;

import org.sonar.api.Extension;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

import com.google.common.collect.ImmutableList;

import de.ne0.sonar.plsql.plugin.colorizer.PlSQLColorizerFormat;
import de.ne0.sonar.plsql.plugin.core.PlSQL;
import de.ne0.sonar.plsql.plugin.core.PlSQLSourceImporter;
import de.ne0.sonar.plsql.plugin.cpd.PlSQLCpdMapping;

@Properties({
	  // Global PlSQL settings
	 @Property(
			    key = PlSQLPlugin.FILE_SUFFIXES_KEY,
			    defaultValue = PlSQLPlugin.FILE_SUFFIXES_DEFVALUE,
			    name = "File suffixes",
			    description = "Comma-separated list of suffixes for files to analyze.",
			    global = true,
			    project = true),
})
public class PlSQLPlugin extends SonarPlugin {

	public List<Class<? extends Extension>> getExtensions() {
		return ImmutableList.of(
				PlSQL.class,
		        PlSQLSourceImporter.class,
		        PlSQLColorizerFormat.class,
		        PlSQLCpdMapping.class,

		        PlSQLSquidSensor.class,
		        PlSQLRuleRepository.class,
		        PlSQLProfile.class

//		        PlSQLCommonRulesEngineProvider.class
//
//		        LCOVSensor.class,
//		        JsTestDriverSensor.class,
//		        JsTestSensor.class
				);
	}
	
	 public static final String FILE_SUFFIXES_KEY = "sonar.plsql.file.suffixes";
	  public static final String FILE_SUFFIXES_DEFVALUE = ".sql,.pkb,.pkg";

}
