package de.ne0.sonar.plsql.api;

import org.sonar.sslr.grammar.GrammarRuleKey;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.TokenType;

public enum PlSQLTokenType implements TokenType, GrammarRuleKey {
	
	  IDENTIFIER,
	  OBJECT_IDENTIFIER,
	  NUMERIC_LITERAL,
	  EMPTY;

	  public String getName() {
	    return name();
	  }

	  public String getValue() {
	    return name();
	  }

	  public boolean hasToBeSkippedFromAst(AstNode node) {
	    return false;
	  }

	}