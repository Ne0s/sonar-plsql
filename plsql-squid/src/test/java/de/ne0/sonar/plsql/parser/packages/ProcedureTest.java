package de.ne0.sonar.plsql.parser.packages;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class ProcedureTest {
	LexerlessGrammar g = PlSQLGrammar.createGrammar();

	@Test
	public void parameters() {
		assertThat(g.rule(PlSQLGrammar.PARAMETER_DECLARATION))
		.matches("test varchar2(4)")
		.matches("test IN varchar2(4)")
		.matches("test IN OUT varchar2(4)")
		.matches("test OUT varchar2(4)")
		.matches("test OUT NOCOPY varchar2(4)");
	}
	
	@Test
	public void ok() {
		assertThat(g.rule(PlSQLGrammar.PROCEDURE_BODY))
		.matches("Procedure test is begin end;")
		.matches("procedure test(test IN varchar2(4)) as begin return 0; end test;");
	}
}
