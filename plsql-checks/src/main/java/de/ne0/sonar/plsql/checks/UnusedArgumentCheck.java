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

@Rule(key = "UnusedArgument", name = "Unused Argument", description = "Validate if Arguments are in use", priority = Priority.MAJOR)
@BelongsToProfile(title = CheckList.SONAR_WAY_PROFILE, priority = Priority.MAJOR)
public class UnusedArgumentCheck extends SquidCheck<LexerlessGrammar> {

	
	private Scope currentScope;

	@Override
	public void init() {
		subscribeTo(PlSQLGrammar.FUNCTION_BODY, PlSQLGrammar.PROCEDURE_BODY,
				PlSQLGrammar.PARAMETER_DECLARATION,
				PlSQLGrammar.ASSIGNMENT_STATEMENT,
				PlSQLGrammar.PRIMARY_EXPRESSION, PlSQLGrammar.INTO_CLAUSE);
	}

	@Override
	public void visitFile(AstNode astNode) {
		currentScope = null;
	}

	@Override
	public void visitNode(AstNode astNode) {
		if (astNode.is(PlSQLGrammar.FUNCTION_BODY)
				|| astNode.is(PlSQLGrammar.PROCEDURE_BODY)) {
			// enter new scope
			currentScope = new Scope(currentScope);
		} else if (currentScope != null) {
			if (astNode.is(PlSQLGrammar.PARAMETER_DECLARATION)) {
				currentScope.declare(astNode
						.getFirstChild(PlSQLGrammar.IDENTIFIER_NAME));
			} else if (astNode.is(PlSQLGrammar.PRIMARY_EXPRESSION)) {
				AstNode identifierNode = astNode
						.getFirstChild(PlSQLGrammar.OBJECT_NAME);
				if (identifierNode != null) {
					currentScope.use(identifierNode);
				}
			} else if (astNode.is(PlSQLGrammar.ASSIGNMENT_STATEMENT)) {
				AstNode identifierNode = astNode
						.getFirstChild(PlSQLGrammar.OBJECT_REFERENCE);
				if (identifierNode != null) {
					currentScope.use(identifierNode);
				}
			} else if (astNode.is(PlSQLGrammar.INTO_CLAUSE)) {
				for (AstNode identifierNode : astNode
						.getChildren(PlSQLGrammar.OBJECT_NAME)) {
					currentScope.use(identifierNode);
				}
			}
		}
	}

	@Override
	public void leaveNode(AstNode astNode) {
		if (currentScope != null
				&& astNode.is(PlSQLGrammar.FUNCTION_BODY,
						PlSQLGrammar.PROCEDURE_BODY)) {
			// leave scope
			for (Map.Entry<String, Variable> entry : currentScope.getVariables()
					.entrySet()) {
				if (entry.getValue().getUsages() == 0) {
					getContext().createLineViolation(
							this,
							"Remove the declaration of the unused '"
									+ entry.getKey() + "' argument.",
							entry.getValue().getDeclaration());
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
