package de.ne0.sonar.plsql.parser.statement;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class LoopStatementTest {
	LexerlessGrammar g = PlSQLGrammar.createGrammar();



	@Test
	public void ok() {
		assertThat(g.rule(PlSQLGrammar.STATEMENT))
		.matches("for i in test.first .. test.last loop null; end loop;")
		.matches("FOR il IN 1..ui_bankresponseevent.u_references.t_additional_ref.count LOOP null; end loop;");
	}
}
