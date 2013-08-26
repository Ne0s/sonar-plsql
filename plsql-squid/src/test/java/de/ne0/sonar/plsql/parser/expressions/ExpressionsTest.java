package de.ne0.sonar.plsql.parser.expressions;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class ExpressionsTest {
	LexerlessGrammar g = PlSQLGrammar.createGrammar();

	@Test
	public void caseExpression() {
		assertThat(g.rule(PlSQLGrammar.EXPRESSION))
		.matches("CASE grade\n"
         + "WHEN 'A' THEN 'Excellent'\n"
         + "WHEN 'B' THEN 'Very Good'\n"
         + "WHEN 'C' THEN 'Good'\n"
         + "WHEN 'D' THEN 'Fair'\n"
         + "WHEN 'F' THEN 'Poor'\n"
         + "ELSE 'No such grade'\n"
         + "END")
         .matches("CASE \n"
         + "WHEN test.s=blubb.a THEN 'Excellent'\n"
         + "WHEN true THEN 'Very Good'\n"
         + "ELSE 'No such grade'\n"
         + "END")
         .matches("CASE WHEN var IN(rec.field1,rec.field2) then 1 else 0 end");
	}
	
	@Test
	public void castTest() {
		assertThat(g.rule(PlSQLGrammar.EXPRESSION))
		.matches("treat(var1 as type2)")
		.matches("cast(systimestamp as date)");
	}
	
	@Test
	public void identifierName() {
		assertThat(g.rule(PlSQLGrammar.OBJECT_NAME))
		.matches("recordX")
		.matches("recordX.field")
		;
	}
	
	@Test
	public void objectReference() {
		assertThat(g.rule(PlSQLGrammar.OBJECT_REFERENCE))
		.matches("recordX.field")
		.matches("collectionX(1)")
		.matches("collectionX(3).field")
		.matches("collectionX(i+5).field(3).myFiled");
	}
	
	@Test
	public void newExpression() {
		assertThat(g.rule(PlSQLGrammar.EXPRESSION))
		.matches("new myType")
		.matches("new testType()")
		.matches("new testType(var1)");
	}

	
	@Test
	public void debugFlag() {
		assertThat(g.rule(PlSQLGrammar.EXPRESSION))
		.matches("$$myDebugFlag");
	}
	
	@Test
	public void literals() {
		assertThat(g.rule(PlSQLGrammar.EXPRESSION))
		.matches("'String'")
		.matches("1234")
		.matches("link.n_my_field");
	}
	
	@Test
	public void calc() {
		assertThat(g.rule(PlSQLGrammar.EXPRESSION))
		.matches("1+1")
		.matches("1 + 3 - 4")
		.matches("1 + ( 3 * 4 )");
	}
	
	@Test
	public void booleanTest() {
		assertThat(g.rule(PlSQLGrammar.EXPRESSION))
		.matches("NOT booleanVar")
		.matches("test IS not NULL")
		.matches("1 = 2")
		.matches("1 < 2")
		.matches("1 > 2")
		.matches("1 <= 2")
		.matches("1 >= 2")
		.matches("TRUE")
		.matches("FALSE")
		.matches("1 = 1 AND 2 = 2")
		.matches("var IN(rec.field1,rec.field2)")
		.matches("1=1 OR 2=2")
		.matches("ul_my_rec_o.u_field.c_flag = crap_core_const.cc_const")
		.matches("rownum <= 4")
		.matches("link.n_link_to_trg = trans.n_transaction_no")
		.matches("p.n_field = l.n_link \n" +
                 "AND link.n_link_to_trg = trans.n_transaction_no")
		;
	}
	

	
	@Test
	public void functions() {
		assertThat(g.rule(PlSQLGrammar.EXPRESSION))
		.matches("substr(user,1,8)")
		.matches("testfunc(myParam => 'testValue', myParam2 => 3)")
		.matches(" base_log_core.debug (ni_sequence_no   => CAPP_CORE_CONSTANTS.nc_zero_record, test2 => 'blubb')");
	}
}
