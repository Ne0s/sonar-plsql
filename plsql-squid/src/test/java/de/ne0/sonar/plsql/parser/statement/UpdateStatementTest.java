package de.ne0.sonar.plsql.parser.statement;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class UpdateStatementTest {
	LexerlessGrammar g = PlSQLGrammar.createGrammar();

	@Test
	public void ok() {
		assertThat(g.rule(PlSQLGrammar.STATEMENT))
				.matches("update test set col1 = 5+1;")
		.matches("update test set col1 = 5;")
		.matches("update test t set t.col1 = 5;")

		
		
		;
	}
	
	}
