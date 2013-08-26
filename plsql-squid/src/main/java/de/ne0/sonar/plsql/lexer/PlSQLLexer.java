package de.ne0.sonar.plsql.lexer;

import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.commentRegexp;
import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.regexp;

import com.sonar.sslr.api.GenericTokenType;
import com.sonar.sslr.impl.Lexer;
import com.sonar.sslr.impl.channel.BlackHoleChannel;
import com.sonar.sslr.impl.channel.IdentifierAndKeywordChannel;
import com.sonar.sslr.impl.channel.PunctuatorChannel;
import com.sonar.sslr.impl.channel.UnknownCharacterChannel;

import de.ne0.sonar.plsql.PlSQLConfiguration;
import de.ne0.sonar.plsql.api.PlSQLKeyword;
import de.ne0.sonar.plsql.api.PlSQLPunctuator;
import de.ne0.sonar.plsql.api.PlSQLTokenType;

public final class PlSQLLexer {
	
	 /**
	   * LF, CR, LS, PS
	   */
	  public static final String LINE_TERMINATOR = "\\n\\r\\u2028\\u2029";

	  /**
	   * Tab, Vertical Tab, Form Feed, Space, No-break space, Byte Order Mark, Any other Unicode "space separator"
	   */
	  public static final String WHITESPACE = "\\t\\u000B\\f\\u0020\\u00A0\\uFEFF\\p{Zs}";

	  
	  /** 
	   * Comments
	   */
	  //TODO: fix for PlSQL
	  public static final String SINGLE_LINE_COMMENT = "//[^\\n\\r]*+|--[^\\n\\r]*+";
	  public static final String MULTI_LINE_COMMENT = "/\\*[\\s\\S]*?\\*/";
	  public static final String MULTI_LINE_COMMENT_NO_LB = "/\\*[^\\n\\r]*?\\*/";

	  public static final String COMMENT = "(?:" + SINGLE_LINE_COMMENT + "|" + MULTI_LINE_COMMENT + ")";

	
	  /**
	   * Literals
	   */
	  //original
//	  public static final String LITERAL = "(?:"
//		      + "\"([^\"\\\\]*+(\\\\[\\s\\S])?+)*+\""
//		      + "|'([^'\\\\]*+(\\\\[\\s\\S])?+)*+'"
//		      + ")";
	  public static final String LITERAL = "(?:"
      + "'([^']|'')*+'"
      + ")";
	  public static final String NUMERIC_LITERAL = "(?:"
		      // Decimal
			  + "[0-9]++" //+ INT_SUFFIX + "?+" 
			  + "|[0-9]++(\\.[0-9]++)?+" /* TODO: fix + EXP + "?+" + FLOAT_SUFFIX + "?+"
		      // Decimal
		      + "|\\.[0-9]++" + EXP + "?+" + FLOAT_SUFFIX + "?+"
		      // Decimal
		      + "|[0-9]++" + FLOAT_SUFFIX
		      + "|[0-9]++" + EXP + FLOAT_SUFFIX + "?+"
		      // Hexadecimal
		      + "|0[xX][0-9a-fA-F]++\\.[0-9a-fA-F_]*+" + BINARY_EXP + "?+" + FLOAT_SUFFIX + "?+"
		      // Hexadecimal
		      + "|0[xX][0-9a-fA-F]++" + BINARY_EXP + FLOAT_SUFFIX + "?+"

		      // Integer Literals
		      // Hexadecimal
		      + "|0[xX][0-9a-fA-F]++" + INT_SUFFIX + "?+"
		      // Binary (Java 7)
		      + "|0[bB][01]++" + INT_SUFFIX + "?+"*/
		      // Decimal and Octal
		      + ")";
	  
	  private static final String UNICODE_LETTER = "\\p{Lu}\\p{Ll}\\p{Lt}\\p{Lm}\\p{Lo}\\p{Nl}";
	  private static final String UNICODE_DIGIT = "\\p{Nd}";
	  private static final String UNICODE_CONNECTOR_PUNCTUATION = "\\p{Pc}";

	  
	 // private static final String IDENTIFIER_START = "(?:[a-zA-Z])"; //"(?:[$_" + UNICODE_LETTER + "]"+/*TODO: required? "|\\\\" + UNICODE_ESCAPE_SEQUENCE + */ ")";
	  //private static final String IDENTIFIER_PART = "(?:[a-zA-Z])(?:[a-zA-Z0-9\\p{Pc}$])*+"; //"(?:" + IDENTIFIER_START + "|[" + UNICODE_DIGIT + UNICODE_CONNECTOR_PUNCTUATION + "])";

	  private static final String IDENTIFIER_PART = "[a-zA-Z][a-zA-Z0-9\\p{Pc}$]*"; 

	  public static final String IDENTIFIER = "(?:"+IDENTIFIER_PART + "|\""+IDENTIFIER_PART+"\")";

	  
	  private PlSQLLexer() {}
	
	public static Lexer create(PlSQLConfiguration conf) {
	    return Lexer.builder()
	        .withCharset(conf.getCharset())

	        .withFailIfNoChannelToConsumeOneCharacter(true)

	        // Channels, which consumes more frequently should come first.
	        // Whitespace character occurs more frequently than any other, and thus come first:
	        .withChannel(new BlackHoleChannel("[" + LINE_TERMINATOR + WHITESPACE + "]++"))

	        // Comments
	        .withChannel(commentRegexp(COMMENT))

	        // String Literals
	        .withChannel(regexp(GenericTokenType.LITERAL, LITERAL))

	        // Regular Expression Literals
	     //TODO: check if required   .withChannel(new PlSQLRegexpChannel())

	        .withChannel(regexp(PlSQLTokenType.NUMERIC_LITERAL, NUMERIC_LITERAL))

	        .withChannel(new IdentifierAndKeywordChannel(IDENTIFIER, false, PlSQLKeyword.values()))
	        .withChannel(new PunctuatorChannel(PlSQLPunctuator.values()))

	        .withChannel(new UnknownCharacterChannel(true))

	        .build();
	  }
	

}
