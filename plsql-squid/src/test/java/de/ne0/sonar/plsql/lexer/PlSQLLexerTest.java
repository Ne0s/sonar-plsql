package de.ne0.sonar.plsql.lexer;

import static com.sonar.sslr.test.lexer.LexerMatchers.hasComment;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.sonar.sslr.impl.Lexer;

import de.ne0.sonar.plsql.PlSQLConfiguration;

public class PlSQLLexerTest {

	 private static Lexer lexer;

	  @BeforeClass
	  public static void init() {
	    lexer = PlSQLLexer.create(new PlSQLConfiguration(Charsets.UTF_8));
	  }
	  
	  @Test
	  public void lexMultiLinesComment() {
	    assertThat(lexer.lex("/* My Comment \n*/"), hasComment("/* My Comment \n*/"));
	    assertThat(lexer.lex("/**/"), hasComment("/**/"));
	  }

	  @Test
	  public void lexInlineComment() {
	    assertThat(lexer.lex("-- My Comment \n new line"), hasComment("-- My Comment "));
	    assertThat(lexer.lex("--"), hasComment("--"));
	  }


}
