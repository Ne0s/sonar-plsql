package de.ne0.sonar.plsql.parser.statement;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class StatementsTest {
	LexerlessGrammar g = PlSQLGrammar.createGrammar();

	@Test
	public void executePlSQLBuffer() {
		assertThat(g.rule(PlSQLGrammar.EOS))
		.matches("\n/\n");
	}
	
	@Test
	public void exceptionDefintion() {
		assertThat(g.rule(PlSQLGrammar.EXCEPTION_DEFINITION))
		.matches("PRAGMA EXCEPTION_INIT(e_threshold_not_found,-20102);")
		;
	}
	
	@Test
	public void assignement() {
		assertThat(g.rule(PlSQLGrammar.ASSIGNMENT_STATEMENT))
		.matches("test := 1234;")
		.matches("ul_accttran   := NEW acct_bookingtranevent_o();")
		.matches("test := new TEST();")
		.matches("test := my.log(msg => 'blubb', level => 5);")
		;
	}
}
