package de.ne0.sonar.plsql.checks;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.sslr.parser.LexerlessGrammar;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.squid.checks.SquidCheck;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

@Rule(key = "SpacingInExecuteBuffer", name = "Incorrect Spacing while ExecuteBuffer", description = "Spacing after ExecuteBuffer is an issue while deploying", priority = Priority.CRITICAL)
@BelongsToProfile(title = CheckList.SONAR_WAY_PROFILE, priority = Priority.CRITICAL)
public class CorrectExecuteBufferCheck extends SquidCheck<LexerlessGrammar> {

	
	@Override
	public void init() {
		subscribeTo(PlSQLGrammar.EXECUTE_PLSQL_BUFFER);
	}

	
	@Override
	public void visitNode(AstNode astNode) {
		if (astNode.hasDirectChildren(PlSQLGrammar.SPACING_NO_LB)) {
			getContext().createLineViolation(
					this,
					"Whitespaces after SLASH produce issues while deploying",
					astNode.getFirstChild(PlSQLGrammar.SPACING_NO_LB));
		}
	}

	
}
