package de.ne0.sonar.plsql.parser.tables;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class CreateTableTest {
	LexerlessGrammar g = PlSQLGrammar.createGrammar();

	@Test
	public void ok() {
		assertThat(g.rule(PlSQLGrammar.CREATE_TABLE))
				.matches("create table test;")
				.matches("create table test (column VARCHAR2(10) default 'test' not null);")
				.matches("create table test (column VARCHAR2(10) default 'test' not null) tablespace space1;")
				;
	} 
	

	@Test
	public void parallel() {
		assertThat(g.rule(PlSQLGrammar.CREATE_TABLE))
				.matches("create table test (col1 number) PARALLEL 1;")
				;
	} 

	@Test
	public void partitions() {
		assertThat(g.rule(PlSQLGrammar.CREATE_TABLE))
				.matches("create table test (col1 number) partition by list (col1) (partition c_my values ('T'), partition c_my2 values (DEFAULT));")
				;
	} 


	@Test
	public void partitionProperty() {
		assertThat(g.rule(PlSQLGrammar.PARTITION_PROPERTY))
				.matches("partition by list (col1) (partition c_my values ('T'))")
				.matches("partition by list (col1) (partition c_my values ('T'), partition c_my2 values (DEFAULT))")
				
				;
	} 

}
