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
package de.ne0.sonar.plsql.checks;

import java.util.Map;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.sslr.parser.LexerlessGrammar;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.squid.checks.SquidCheck;

import de.ne0.sonar.plsql.checks.utils.Scope;
import de.ne0.sonar.plsql.checks.utils.Scope.Variable;
import de.ne0.sonar.plsql.parser.PlSQLGrammar;

@Rule(
  key = "UnusedVariable",
  name = "Unused Variable",
  description = "Validate if Variable got used",
  priority = Priority.MAJOR)
@BelongsToProfile(title = CheckList.SONAR_WAY_PROFILE, priority = Priority.MAJOR)
public class UnusedVariableCheck extends SquidCheck<LexerlessGrammar> {



  private Scope currentScope;

  @Override
  public void init() {
    subscribeTo(
    		PlSQLGrammar.FUNCTION_BODY, PlSQLGrammar.PROCEDURE_BODY,
    		PlSQLGrammar.BLOCK_STATEMENT,
    		
        PlSQLGrammar.VARIABLE_DECLARATION,
        PlSQLGrammar.PRIMARY_EXPRESSION,
        PlSQLGrammar.ASSIGNMENT_STATEMENT,
        PlSQLGrammar.INTO_CLAUSE
        );
  }

  @Override
  public void visitFile(AstNode astNode) {
    currentScope = null;
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.is(PlSQLGrammar.FUNCTION_BODY, PlSQLGrammar.PROCEDURE_BODY,
    		PlSQLGrammar.BLOCK_STATEMENT)) {
      // enter new scope
      currentScope = new Scope(currentScope);
//    } else if (astNode.is(EcmaScriptGrammar.FORMAL_PARAMETER_LIST)) {
//      // declare all parameters as variables, which are already used, so that they won't trigger violations
//      for (AstNode identifierNode : astNode.getChildren(EcmaScriptTokenType.IDENTIFIER)) {
//        currentScope.declare(identifierNode, 1);
//      }
    } else if (currentScope != null) {
      if (astNode.is(PlSQLGrammar.VARIABLE_DECLARATION)) {
        currentScope.declare(astNode.getFirstChild(PlSQLGrammar.IDENTIFIER_NAME), 0);
      } else if (astNode.is(PlSQLGrammar.PRIMARY_EXPRESSION)) {
          AstNode identifierNode = astNode.getFirstChild(PlSQLGrammar.OBJECT_NAME);
          if (identifierNode != null) {
            currentScope.use(identifierNode);
          }
        } else if (astNode.is(PlSQLGrammar.ASSIGNMENT_STATEMENT)) {
            AstNode identifierNode = astNode.getFirstChild(PlSQLGrammar.OBJECT_REFERENCE);
            if (identifierNode != null) {
              currentScope.use(identifierNode);
            }
          } else if (astNode.is(PlSQLGrammar.INTO_CLAUSE)) {
        	  for (AstNode identifierNode : astNode.getChildren(PlSQLGrammar.IDENTIFIER_NAME)) {
                currentScope.use(identifierNode);
              } 
          }
    }
  }

  @Override
  public void leaveNode(AstNode astNode) {
    if (currentScope!=null && astNode.is(PlSQLGrammar.FUNCTION_BODY, PlSQLGrammar.PROCEDURE_BODY,
    		PlSQLGrammar.BLOCK_STATEMENT)) {
      // leave scope
      for (Map.Entry<String, Variable> entry : currentScope.getVariables().entrySet()) {
        if (entry.getValue().getUsages() == 0) {
          getContext().createLineViolation(this, "Remove the declaration of the unused '" + entry.getKey() + "' variable.", entry.getValue().getDeclaration());
        }
      }
      currentScope = currentScope.getOuterScope();
    }
  }

  @Override
  public void leaveFile(AstNode astNode) {
    currentScope = null;
  }

}
