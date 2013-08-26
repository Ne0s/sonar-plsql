package de.ne0.sonar.plsql.parser.packages;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class CreatePackageBodyTest {
	 LexerlessGrammar g = PlSQLGrammar.createGrammar();

	  @Test
	  public void ok() {
	    assertThat(g.rule(PlSQLGrammar.PACKAGE_BODY_DECLARATION))
	        .notMatches("create package test as null; end test;")
	        .matches("create package body test as function t1 return number is begin return 5; end t1; end test;\r\n/\r\n")
//	        .matches("create package body test as null; end test;")
//	        .matches("create PacKage body test as null; end test;")
//	         .matches("create or replace package body test_PKG is null; end test_PKG;")
//	        .matches("CREATE OR REPLACE PACKAGE BODY test_PKG IS null; END test_PKG;")
	        ;
	        
	  }
}
