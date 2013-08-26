package de.ne0.sonar.plsql.parser.statement;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class BlockStatementTest {
	LexerlessGrammar g = PlSQLGrammar.createGrammar();

	@Test
	public void withVars() {
		assertThat(g.rule(PlSQLGrammar.BLOCK_STATEMENT))
		.matches("test varchar2(2); test2 date; BEGIN end;");
	}

	@Test
	public void withProcedures() {
		assertThat(g.rule(PlSQLGrammar.BLOCK_STATEMENT))
		.matches("Procedure test is begin end; BEGIN end;");
	}
	
	@Test
	public void easy() {
		assertThat(g.rule(PlSQLGrammar.BLOCK_STATEMENT))
		.matches("BEGIN end;")
		.matches("BEGIN end test;")
//		.matches("BEGIN myFunctionCall(test1 => 'value'); END;")
		;
	}
	
	@Test
	public void exceptionHandler() {
		assertThat(g.rule(PlSQLGrammar.EXCEPTION_HANDLER))
		.matches("WHEN no_data_found THEN \r\n"+
			"		nl_returncode := package_id +const.nc_no_data_found; \r\n"+
			"		RETURN nl_returncode;");
	}
	
	@Test
	public void exceptionTest() {
		assertThat(g.rule(PlSQLGrammar.BLOCK_STATEMENT))
		.matches(" BEGIN \r\n"+
			"	return 4; return 3;\r\n"+
			"END;")
			.matches(" BEGIN \r\n"+
			"	SELECT * \r\n"+
			"	INTO   details \r\n"+
			"	FROM   myTable t \r\n"+
			"	WHERE  t.field1 = col(j).ref.field; \r\n"+
			"EXCEPTION \r\n"+
			"	WHEN no_data_found THEN \r\n"+
			"		nl_returncode := package_id +const.nc_no_data_found; \r\n"+
			"		RETURN nl_returncode; \r\n"+
			"END;");
	}
	
	@Test
	public void multipleStatements() {
		assertThat(g.rule(PlSQLGrammar.BLOCK_STATEMENT))
		.matches("BEGIN " +
				"tests := new test1(); test2 := new test2(); end;");
	}
}
