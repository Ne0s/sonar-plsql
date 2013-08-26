package de.ne0.sonar.plsql.checks.utils;

import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.sonar.sslr.api.AstNode;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class Scope {
	
	 public static class Variable {
		    final AstNode declaration;
		    int usages;

		    public Variable(AstNode declaration, int usages) {
		      this.declaration = declaration;
		      this.usages = usages;
		    }
		    
		    public int getUsages() {
				return usages;
			}
		    
		    public AstNode getDeclaration() {
				return declaration;
			}
		  }
	 
	private final Scope outerScope;
	private final Map<String, Variable> variables;

	public Scope(Scope outerScope) {
		this.outerScope = outerScope;
		this.variables = Maps.newHashMap();
	}

	public void declare(AstNode astNode) {
		declare(astNode, 0);
	}
	
	public void declare(AstNode astNode, int use) {
		Preconditions.checkState(astNode.is(PlSQLGrammar.IDENTIFIER_NAME));
		String identifier = astNode.getTokenValue().toLowerCase();
		variables.put(identifier, new Variable(astNode, use));
	}

	public void use(AstNode astNode) {
		// Preconditions.checkState(astNode.is(EcmaScriptTokenType.IDENTIFIER));
		String identifier = astNode.getTokenValue();
		
		//strip down to first value
		final int hasReference = identifier.indexOf('.');
		if (hasReference!=-1) {
			identifier = identifier.substring(0, hasReference);
		}
		
		Scope scope = this;
		while (scope != null) {
			Variable arg = scope.variables.get(identifier.toLowerCase());
			if (arg != null) {
				arg.usages++;
				return;
			}
			scope = scope.outerScope;
		}
		//in case not found, create new entry
		 variables.put(identifier, new Variable(astNode, 1));
	}
	
	public Map<String, Variable> getVariables() {
		return variables;
	}
	
	public Scope getOuterScope() {
		return outerScope;
	}
}
