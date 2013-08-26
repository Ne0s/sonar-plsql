package de.ne0.sonar.plsql.parser.packages;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class CreatePackageTest {
	LexerlessGrammar g = PlSQLGrammar.createGrammar();

	@Test
	public void realLife() {
		assertThat(g.rule(PlSQLGrammar.PACKAGE_DECLARATION))
//				.matches("create package test as null; end test;")
//				.matches("create package test.test as null; end test;")
//				.matches("create package test is null; end test;")
//				.matches("create or replace package test as null; end test;")
				.notMatches("create package body test as null; end test;");
	}
}
