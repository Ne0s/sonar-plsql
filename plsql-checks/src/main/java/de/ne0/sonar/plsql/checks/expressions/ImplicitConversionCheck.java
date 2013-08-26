package de.ne0.sonar.plsql.checks.expressions;

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.sslr.parser.LexerlessGrammar;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.squid.checks.SquidCheck;

import de.ne0.sonar.plsql.checks.CheckList;
import de.ne0.sonar.plsql.checks.utils.CheckHelper;
import de.ne0.sonar.plsql.checks.utils.CheckHelper.DataType;
import de.ne0.sonar.plsql.parser.PlSQLGrammar;

@Rule(key = "ImplicitConversionCheck", name = "Implicit Conversion of Variables", description = "there is an implicid conversion of datatype possible", priority = Priority.CRITICAL)
@BelongsToProfile(title = CheckList.SONAR_WAY_PROFILE, priority = Priority.CRITICAL)
public class ImplicitConversionCheck extends SquidCheck<LexerlessGrammar> {
	
	
		
	
	
	private TypeSlot currentSlot;

	private void addSlots(TypeSlot slots) {
		if (currentSlot != null) {
			currentSlot.fillSlot(slots);
		}
		currentSlot = slots;
	}
	
	@Override
	public void init() {
		subscribeTo(PlSQLGrammar.ASSIGNMENT_STATEMENT, 
				PlSQLGrammar.PRIMARY_EXPRESSION, PlSQLGrammar.CONCAT_EXPRESSION,
				PlSQLGrammar.ADDITIVE_EXPRESSION, PlSQLGrammar.MULTIPLICATIVE_EXPRESSION, PlSQLGrammar.BOOLEAN_EXPRESSION
				,PlSQLGrammar.BOOLEAN_COMP_EXPRESSION
				,PlSQLGrammar.CALL_EXPRESSION
				,PlSQLGrammar.NEW_EXPRESSION
				,PlSQLGrammar.ARGUMENT
				,PlSQLGrammar.UPDATE_COLUMN
				, PlSQLGrammar.CASE_EXPRESSION
				,PlSQLGrammar.IN_EXPRESSION
				,PlSQLGrammar.VARIABLE_DECLARATION
				);
	}

	@Override
	public void visitFile(AstNode astNode) {
		currentSlot = null;
	}

	@Override
	public void visitNode(AstNode astNode) {
//		System.out.println("VISIT: "+ " Node: "+astNode+currentSlot );

		if (astNode.is(PlSQLGrammar.ASSIGNMENT_STATEMENT, PlSQLGrammar.VARIABLE_DECLARATION)) {
			addSlots(new TypeSlot(2, astNode));
		} else if (astNode.is(PlSQLGrammar.ARGUMENT) && astNode.hasDirectChildren(PlSQLGrammar.IDENTIFIER_NAME)) {
			addSlots(new TypeSlot(2, astNode));
			//let's fill first one with Identifier
			final AstNode idName = astNode.getFirstChild(PlSQLGrammar.IDENTIFIER_NAME);
			currentSlot.fillSlot(new FixedTypeSlot(CheckHelper.getDataType(idName), idName));
		} else if (astNode.is(PlSQLGrammar.UPDATE_COLUMN) && astNode.hasDirectChildren(PlSQLGrammar.OBJECT_NAME)) {
			addSlots(new TypeSlot(2, astNode));
			//let's fill first one with Identifier
			final AstNode idName = astNode.getFirstChild(PlSQLGrammar.OBJECT_NAME);
			currentSlot.fillSlot(new FixedTypeSlot(CheckHelper.getDataType(idName), idName));
		} else if (astNode.is(PlSQLGrammar.CONCAT_EXPRESSION)) {
			addSlots(new TypeSlot((astNode.getNumberOfChildren()+1)/2, astNode, DataType.STRING));
		} else if (astNode.is(PlSQLGrammar.BOOLEAN_EXPRESSION)) {
			addSlots(new TypeSlot((astNode.getNumberOfChildren()+1)/2, astNode, DataType.BOOLEAN));
		} else if (astNode.is(PlSQLGrammar.BOOLEAN_COMP_EXPRESSION)) {
			addSlots(new TypeSlot((astNode.getNumberOfChildren()+1)/2, astNode, DataType.BOOLEAN, false));
		}  else if (astNode.is(PlSQLGrammar.ADDITIVE_EXPRESSION, PlSQLGrammar.MULTIPLICATIVE_EXPRESSION)) {
			addSlots(new TypeSlot((astNode.getNumberOfChildren()+1)/2, astNode, DataType.NUMBER));
		}  else if (astNode.is(PlSQLGrammar.CALL_EXPRESSION)) {
			addSlots(new CallTypeSlot(astNode));
		} else if (astNode.is(PlSQLGrammar.CASE_EXPRESSION)) {
			addSlots(new CaseTypeSlot(astNode));
		} else if (astNode.is(PlSQLGrammar.IN_EXPRESSION)) {
			addSlots(new InTypeSlot(astNode));
		}
		else if (astNode.is(PlSQLGrammar.NEW_EXPRESSION)) {
			addSlots(new TypeSlot(1, astNode, DataType.OBJECT_TYPE, false));
		}
		
		
		else if (astNode.is(PlSQLGrammar.PRIMARY_EXPRESSION)) {
			addSlots(new FixedTypeSlot(CheckHelper.getDataType(astNode), astNode));
		}
	}

	//private DataType getTypeForPrimaryExpression(AstNode astNode) {
		
//	}

	@Override
	public void leaveNode(AstNode astNode) {
//		System.out.println("LEAVE: "+" Node: "+astNode+currentSlot );

		//check if we leave current Node
		if (currentSlot != null && currentSlot.node==astNode) {
//			System.out.println("LEAVE: "+currentSlot);
			if (currentSlot.hasIssue()) {
					getContext().createLineViolation(
							this,
							"Implicid conversion from {0} to {1} likely!",
							currentSlot.node, currentSlot.getFirstType(), currentSlot.getSecondType());
				}
			currentSlot = currentSlot.getOuterSlot();
		}
	}

	@Override
	public void leaveFile(AstNode astNode) {
		currentSlot = null;
	}

}
