package de.ne0.sonar.plsql.checks;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.sslr.parser.LexerlessGrammar;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.squid.checks.SquidCheck;

import de.ne0.sonar.plsql.api.PlSQLPunctuator;
import de.ne0.sonar.plsql.parser.PlSQLGrammar;

@Rule(key = "StatementTerminatedProperly", name = "Statement not ended properly", description = "Validate if Statements are terminated properly", priority = Priority.CRITICAL)
@BelongsToProfile(title = CheckList.SONAR_WAY_PROFILE, priority = Priority.CRITICAL)
public class ProperStatementTerminationCheck extends SquidCheck<LexerlessGrammar> {

	@Override
	public void init() {
		subscribeTo(PlSQLGrammar.EOS);
	}

	
	@Override
	public void visitNode(AstNode astNode) {
		//astNode.getTokenValue()
		if (!astNode.hasDirectChildren(PlSQLPunctuator.SEMI, PlSQLGrammar.EXECUTE_PLSQL_BUFFER)) {
			//only EOF left => raise alert
			getContext().createLineViolation(
					this,
					"Statement not terminated correctly as EOF reached",astNode.getParent()
					);
		}
	}


}
