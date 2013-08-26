package de.ne0.sonar.plsql.checks;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.sslr.parser.LexerlessGrammar;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.squid.checks.SquidCheck;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

@Rule(key = "NoCommitRollbackCheck", name = "Avoid Commit/Rollback In Code", description = "Avoid  Commit/Rollback In Code unless in automnomous transaction", priority = Priority.MAJOR)
@BelongsToProfile(title = CheckList.SONAR_WAY_PROFILE, priority = Priority.MAJOR)
public class NoCommitRollbackCheck extends SquidCheck<LexerlessGrammar> {

	@Override
	public void init() {
		subscribeTo(PlSQLGrammar.COMMIT_STATEMENT, PlSQLGrammar.ROLLBACK_STATEMENT);
	}

	@Override
	public void visitNode(AstNode astNode) {
				getContext().createLineViolation(this,
						"Avoid  Commit/Rollback In Code unless in automnomous transaction",
						astNode);
		
	}

}
