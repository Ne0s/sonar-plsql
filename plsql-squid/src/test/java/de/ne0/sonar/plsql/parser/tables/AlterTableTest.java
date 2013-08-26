package de.ne0.sonar.plsql.parser.tables;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class AlterTableTest {
	LexerlessGrammar g = PlSQLGrammar.createGrammar();

	@Test
	public void ok() {
		assertThat(g.rule(PlSQLGrammar.ALTER_TABLE))
		.matches("ALTER TABLE test_t ADD CONSTRAINT my_pk PRIMARY KEY (col_id) USING INDEX TABLESPACE mySpace;")
		;
	} 
}
