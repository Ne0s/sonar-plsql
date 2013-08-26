package de.ne0.sonar.plsql.checks;

import java.util.List;

import com.google.common.collect.ImmutableList;

import de.ne0.sonar.plsql.checks.expressions.ImplicitConversionCheck;

public final class CheckList {

	public static final String REPOSITORY_KEY = "plsql";
	
	public static final String REPOSITORY_NAME = "Sonar";
	
	public static final String SONAR_WAY_PROFILE = "Sonar way";

	
	 private CheckList() {
	  }

	  public static List<Class> getChecks() {
	    return ImmutableList.<Class> of(
	    		TabCharacterCheck.class,
	    		ParsingErrorCheck.class,
	    		VariableNameCheck.class,
	    		UnusedArgumentCheck.class,
	    		UnusedVariableCheck.class,
	    		FunctionComplexityCheck.class,
	    		LineLengthCheck.class,
	    		ProperStatementTerminationCheck.class,
	    		ArgumentHasDirectionCheck.class,
	    		CorrectExecuteBufferCheck.class,
	    		NoCommitRollbackCheck.class,
	    		LiteralsInCodeCheck.class,
	    		ImplicitConversionCheck.class
	    		);
	  }

}
