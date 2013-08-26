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

@Rule(key = "FunctionHasReturn", 
name = "Function Without Return", 
description = "Function does not have a return in all cases", 
priority = Priority.CRITICAL)
@BelongsToProfile(title = CheckList.SONAR_WAY_PROFILE, priority = Priority.CRITICAL)
public class FunctionHasReturnCheck extends SquidCheck<LexerlessGrammar> {

	
	private Scope currentScope;

	@Override
	public void init() {
		subscribeTo(PlSQLGrammar.FUNCTION_BODY);
	}

	@Override
	public void visitNode(AstNode functionNode) {
		if (!blockHasAlwaysReturn(functionNode.getFirstChild(PlSQLGrammar.BLOCK_STATEMENT))) {
			getContext().createLineViolation(this, "Function does not always return a value", functionNode);
		}
	}

	
	private boolean blockHasAlwaysReturn(AstNode blockNode) {
		for (AstNode statement : blockNode.getChildren(PlSQLGrammar.STATEMENT)) {
			//TODO: fix
		}
		return true;
	}
}
