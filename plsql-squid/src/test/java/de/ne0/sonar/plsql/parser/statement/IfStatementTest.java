package de.ne0.sonar.plsql.parser.statement;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class IfStatementTest {
	LexerlessGrammar g = PlSQLGrammar.createGrammar();

	@Test
	public void ok() {
		assertThat(g.rule(PlSQLGrammar.STATEMENT))
				.matches("IF TRUE THEN null; end if;")
				.matches("IF var1 != var2 THEN return 5; end if;")
				.matches("IF var1 <= var2 THEN return 5; end if;")
				.matches(
						"IF var1 is not null and var1 <> var2 THEN return 5; end if;")
				.matches(
						"if col(3).var1 IS NOt NULL then begin return 5; EXCEPTION \n"
								+ " WHEN no_data_found THEN return -1; end; end if;");
	}

	@Test
	public void conditional_if() {
		assertThat(g.rule(PlSQLGrammar.STATEMENT)).matches(
				"$IF $$cond_flag $THEN null; $ELSE null; $end");
	}

	
}
