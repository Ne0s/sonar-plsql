package de.ne0.sonar.plsql.parser.tables;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class DataTypesTest {
	LexerlessGrammar g = PlSQLGrammar.createGrammar();

	@Test
	public void rowtype() {
		assertThat(g.rule(PlSQLGrammar.DATATYPE))
		.matches("myTable%ROWTYPE")
		.matches("myTable%TYPE");
	}
	
	@Test
	public void numbers() {
		assertThat(g.rule(PlSQLGrammar.DATATYPE))
		.matches("number")
		.matches("number(18)")
		.matches("number(18,2)");
	}
	
	@Test
	public void raw() {
		assertThat(g.rule(PlSQLGrammar.DATATYPE))
		.matches("raw")
		;
	}
	
	
	@Test
	public void dates() {
		assertThat(g.rule(PlSQLGrammar.DATATYPE))
		.matches("date")
		;
	}
	
	@Test
	public void varchar2() {
		assertThat(g.rule(PlSQLGrammar.DATATYPE))
		.matches("VARCHAR2")
		.matches("VARCHAR2(10)")
		.matches("VARCHAR2(10 byte)");
	}
	
	@Test
	public void references() {
		assertThat(g.rule(PlSQLGrammar.DATATYPE))
		.matches("SYS.AQ$_REG_INFO");
	}
}
