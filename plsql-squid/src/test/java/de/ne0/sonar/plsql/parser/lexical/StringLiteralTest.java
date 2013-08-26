package de.ne0.sonar.plsql.parser.lexical;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class StringLiteralTest {

	 LexerlessGrammar g = PlSQLGrammar.createGrammar();

	 @Test
	  public void ok() {
	    assertThat(g.rule(PlSQLGrammar.STRING_LITERAL))
	        .matches("'test'")
	        .matches("'test ''a'' value'");
	 }

}
