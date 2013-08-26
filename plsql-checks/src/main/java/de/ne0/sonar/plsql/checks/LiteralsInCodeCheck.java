package de.ne0.sonar.plsql.checks;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.sslr.parser.LexerlessGrammar;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.squid.checks.SquidCheck;

import de.ne0.sonar.plsql.api.PlSQLKeyword;
import de.ne0.sonar.plsql.api.PlSQLTokenType;
import de.ne0.sonar.plsql.parser.PlSQLGrammar;

@Rule(key = "LiteralInCode", name = "Literal In Code", description = "Avoid using Literals in Code. Move to constants", priority = Priority.MINOR)
@BelongsToProfile(title = CheckList.SONAR_WAY_PROFILE, priority = Priority.MINOR)
public class LiteralsInCodeCheck extends SquidCheck<LexerlessGrammar> {

	@Override
	public void init() {
		subscribeTo(PlSQLGrammar.STRING_LITERAL, PlSQLTokenType.NUMERIC_LITERAL);
	}

	@Override
	public void visitNode(AstNode astNode) {
		if (!(astNode.getParent().is(PlSQLGrammar.VARIABLE_DECLARATION) && astNode
				.getParent().hasDirectChildren(PlSQLKeyword.CONSTANT))) {
			// check for allowed values
			final String value = astNode.getTokenValue();
			if (!(value.equals("0") || value.equals("1"))) {
				getContext().createLineViolation(this,
						"Move Magic Value of {0} to constant definition",
						astNode, astNode.getTokenValue());
			}
		}
	}

}
