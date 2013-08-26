package de.ne0.sonar.plsql.parser.tables;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class CreateViewTest {
	LexerlessGrammar g = PlSQLGrammar.createGrammar();

	@Test
	public void ok() {
		assertThat(g.rule(PlSQLGrammar.CREATE_VIEW))
				.matches("create or replace view test (col1) as select 1 from dual;");
	} 
}
