package de.ne0.sonar.plsql.checks;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.sslr.parser.LexerlessGrammar;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.squid.checks.SquidCheck;

import de.ne0.sonar.plsql.api.PlSQLKeyword;
import de.ne0.sonar.plsql.checks.utils.CheckHelper;
import de.ne0.sonar.plsql.parser.PlSQLGrammar;

@Rule(key = "ValdiateVariableNames", name = "Validate Variable Names", description = "Validate if prefix for Variable names is compliant with naming conventions", priority = Priority.MAJOR)
@BelongsToProfile(title = CheckList.SONAR_WAY_PROFILE, priority = Priority.MAJOR)
public class VariableNameCheck extends SquidCheck<LexerlessGrammar> {

	@Override
	public void init() {
		subscribeTo(PlSQLGrammar.PARAMETER_DECLARATION,
				PlSQLGrammar.VARIABLE_DECLARATION, PlSQLGrammar.CURSOR_DECLARATION, PlSQLGrammar.FUNCTION_BODY,
				PlSQLGrammar.FUNCTION_DECLARATION);
	}

	@Override
	public void visitNode(AstNode astNode) {
		// right now we have always first identifier being VarName (or function
		// name)
		if (!astNode.hasDirectChildren(PlSQLGrammar.IDENTIFIER_NAME)) {
			return;
		}
		
		final String name = astNode.getFirstChild(PlSQLGrammar.IDENTIFIER_NAME)
				.getTokenValue();

		String typePrefix = CheckHelper.getPrefixForDataType(astNode
				.getFirstChild(PlSQLGrammar.DATATYPE));

		if (astNode.is(PlSQLGrammar.PARAMETER_DECLARATION)) {

			// we have a IN keyword
			if (astNode.getFirstChild(PlSQLKeyword.OUT) != null) {
				if (astNode.getFirstChild(PlSQLKeyword.IN) != null) {
					typePrefix = typePrefix + "x";
				} else {
					typePrefix = typePrefix + "o";
				}
			} else {
				// IN is default - hence no extra check
				typePrefix = typePrefix + "i";
			}
		} else if (astNode.is(PlSQLGrammar.VARIABLE_DECLARATION)) {
			if (astNode.getFirstChild(PlSQLKeyword.CONSTANT) != null) {
				typePrefix = typePrefix + "c";
			} else {
				typePrefix = typePrefix + "l";
			}
		}

		typePrefix = typePrefix + "_";

		if (!name.toLowerCase().startsWith(typePrefix)) {
			getContext().createLineViolation(this,
					"Invalid Prefix for {0} as name should start with {1}",
					astNode, name, typePrefix);
		}
	}

}
