package de.ne0.sonar.plsql.parser.statement;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class CaseStatementTest {
	LexerlessGrammar g = PlSQLGrammar.createGrammar();

	@Test
	public void ok() {
		assertThat(g.rule(PlSQLGrammar.STATEMENT))
		.matches("CASE when true then return 0; end case;")
		.matches("CASE when true then return 0; when 4 then return 4; else return 1; end case;");
	}
}
