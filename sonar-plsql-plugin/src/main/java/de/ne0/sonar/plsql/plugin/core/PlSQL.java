package de.ne0.sonar.plsql.plugin.core;

import org.apache.commons.lang.StringUtils;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.AbstractLanguage;

import de.ne0.sonar.plsql.plugin.PlSQLPlugin;

public class PlSQL extends AbstractLanguage {
	 public static final String KEY = "plsql";

	  private Settings settings;

	  public PlSQL(Settings configuration) {
	    super(KEY, "PlSQL Language");
	    this.settings = configuration;
	  }

	  public Settings getSettings() {
	    return this.settings;
	  }

	public String[] getFileSuffixes() {
		 String[] suffixes = settings.getStringArray(PlSQLPlugin.FILE_SUFFIXES_KEY);
		    if (suffixes == null || suffixes.length == 0) {
		      suffixes = StringUtils.split(PlSQLPlugin.FILE_SUFFIXES_DEFVALUE, ",");
		    }
		    return suffixes;
		  }
}
