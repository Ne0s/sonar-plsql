package de.ne0.sonar.plsql.plugin.colorizer;

import org.sonar.colorizer.InlineDocTokenizer;

public class SQLDocTokenizer  extends InlineDocTokenizer {

	  public SQLDocTokenizer(String tagBefore, String tagAfter) {
	    super("--", tagBefore, tagAfter);
	  }

	}