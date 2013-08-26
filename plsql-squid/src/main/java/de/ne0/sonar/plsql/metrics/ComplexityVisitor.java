/*
 * Sonar JavaScript Plugin
 * Copyright (C) 2011 SonarSource and Eriks Nukis
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package de.ne0.sonar.plsql.metrics;

import org.sonar.sslr.parser.LexerlessGrammar;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.squid.SquidAstVisitor;

import de.ne0.sonar.plsql.api.PlSQLMetric;
import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class ComplexityVisitor extends SquidAstVisitor<LexerlessGrammar> {

  @Override
  public void init() {
    subscribeTo(
        PlSQLGrammar.FUNCTION_DECLARATION,
        PlSQLGrammar.FUNCTION_EXPRESSION,
        // Branching nodes
        PlSQLGrammar.IF_STATEMENT,
        PlSQLGrammar.LOOP_STATEMENT,
        PlSQLGrammar.CASE_STATEMENT,
        PlSQLGrammar.CASE_EXPRESSION,
        PlSQLGrammar.RETURN_STATEMENT,
        PlSQLGrammar.EXCEPTION_HANDLER
        // Expressions
//        PlSQLGrammar.QUERY,
//        PlSQLGrammar.ANDAND,
//        PlSQLGrammar.OROR
        
    		);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.is(PlSQLGrammar.RETURN_STATEMENT) && isLastReturnStatement(astNode)) {
      return;
    }
    getContext().peekSourceCode().add(PlSQLMetric.COMPLEXITY, 1);
  }

  private boolean isLastReturnStatement(AstNode astNode) {
    AstNode parent = astNode.getParent().getParent();
    return parent.is(PlSQLGrammar.SOURCE_ELEMENT);
  }

}
