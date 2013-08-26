package de.ne0.sonar.plsql.parser.tables;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class CreateSynonymTest {
	LexerlessGrammar g = PlSQLGrammar.createGrammar();

	@Test
	public void ok() {
		assertThat(g.rule(PlSQLGrammar.CREATE_SYNONYM))
				.matches("create or replace public synonym test for apptest.test;")
				.matches("create synonym test for apptest.test;");
	} 
}
