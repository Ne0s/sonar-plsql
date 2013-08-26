package de.ne0.sonar.plsql.plugin.colorizer;

import java.util.List;

import org.sonar.api.web.CodeColorizerFormat;
import org.sonar.colorizer.CDocTokenizer;
import org.sonar.colorizer.CppDocTokenizer;
import org.sonar.colorizer.JavadocTokenizer;
import org.sonar.colorizer.KeywordsTokenizer;
import org.sonar.colorizer.StringTokenizer;
import org.sonar.colorizer.Tokenizer;

import com.google.common.collect.ImmutableList;

import de.ne0.sonar.plsql.api.PlSQLKeyword;
import de.ne0.sonar.plsql.plugin.core.PlSQL;

public class PlSQLColorizerFormat extends CodeColorizerFormat  {
	 public PlSQLColorizerFormat() {
		    super(PlSQL.KEY);
		  }

		  @Override
		  public List<Tokenizer> getTokenizers() {
			  
			  String[] keywords = PlSQLKeyword.keywordValues();
			  for (int i=0; i<keywords.length; i++) {
				  keywords[i] = keywords[i].toUpperCase();
			  }
			  
		    return ImmutableList.of(
		        new StringTokenizer("<span class=\"s\">", "</span>"),
		        new SQLDocTokenizer("<span class=\"cd\">", "</span>"),
		        new JavadocTokenizer("<span class=\"cppd\">", "</span>"),
		        new CppDocTokenizer("<span class=\"cppd\">", "</span>"),
		        new KeywordsTokenizer("<span class=\"k\">", "</span>", keywords));
		  }
}
