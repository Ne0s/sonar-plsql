package de.ne0.sonar.plsql.checks;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.sslr.parser.LexerlessGrammar;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.squid.checks.SquidCheck;

import de.ne0.sonar.plsql.api.PlSQLKeyword;
import de.ne0.sonar.plsql.parser.PlSQLGrammar;

@Rule(key = "MissingArgumentDirection", name = "Argument w/o direction", description = "Validate if Arguments has direction specified", priority = Priority.MINOR)
@BelongsToProfile(title = CheckList.SONAR_WAY_PROFILE, priority = Priority.MINOR)
public class ArgumentHasDirectionCheck extends SquidCheck<LexerlessGrammar> {

	@Override
	public void init() {
		subscribeTo(PlSQLGrammar.PARAMETER_DECLARATION);
	}

	@Override
	public void visitNode(AstNode astNode) {
		if (!astNode.hasDirectChildren(PlSQLKeyword.IN, PlSQLKeyword.OUT)) {
			getContext().createLineViolation(this,
					"Parameter does not have direction. Please specify IN",
					astNode);
		}
	}

}
