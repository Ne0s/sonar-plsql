package de.ne0.sonar.plsql.api;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.api.TokenType;
import org.sonar.sslr.grammar.GrammarRuleKey;

public enum PlSQLPunctuator implements TokenType, GrammarRuleKey {
  LCURLYBRACE("{"),
  RCURLYBRACE("}"),
  LPARENTHESIS("("),
  RPARENTHESIS(")"),
  LBRACKET("["),
  RBRACKET("]"),
  DOT("."),
  DOTDOT(".."),
  SEMI(";"),
  COMMA(","),
  LT("<"),
  GT(">"),
  LE("<="),
  GE(">="),
  EQUAL("=="),
  NOTEQUAL("!="),
  EQUAL2("==="),
  NOTEQUAL2("!=="),
  NOTEQUAL3("<>"),
  PLUS("+"),
  MINUS("-"),
  STAR("*"),
  MOD("%"),
  DIV("/"),
  INC("++"),
  DEC("--"),
  SL("<<"),
  SR(">>"),
  SR2(">>>"),
  AND("&"),
  OR("|"),
  XOR("^"),
  BANG("!"),
  TILDA("~"),
  ANDAND("&&"),
  OROR("||"),
  QUERY("?"),
  COLON(":"),
  ASSIGNMENT(":="),
  EQU_GT("=>"),
  EQU("="),
  PLUS_EQU("+="),
  MINUS_EQU("-="),
  DIV_EQU("/="),
  STAR_EQU("*="),
  MOD_EQU("%="),
  SL_EQU("<<="),
  SR_EQU(">>="),
  SR_EQU2(">>>="),
  AND_EQU("&="),
  OR_EQU("|="),
  XOR_EQU("^=");

  private final String value;

  private PlSQLPunctuator(String word) {
    this.value = word;
  }

  public String getName() {
    return name();
  }

  public String getValue() {
    return value;
  }

  public boolean hasToBeSkippedFromAst(AstNode node) {
    return false;
  }

}
