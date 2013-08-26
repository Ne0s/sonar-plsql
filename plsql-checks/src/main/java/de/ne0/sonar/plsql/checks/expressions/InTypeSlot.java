package de.ne0.sonar.plsql.checks.expressions;

import java.util.Vector;

import com.sonar.sslr.api.AstNode;

import de.ne0.sonar.plsql.checks.utils.CheckHelper.DataType;

public class InTypeSlot extends TypeSlot {

	private Vector<TypeSlot> arguments = new Vector<TypeSlot>();

	InTypeSlot(AstNode node) {
		//will return Boolean
		super(1, node, DataType.BOOLEAN, false);
	}
		
	@Override
	void fillSlot(TypeSlot mySlot) {
		if (currentSlot==0) {
			//for primary Expression (aka function Name)
			super.fillSlot(mySlot);
		} else {
			//should be an argument
			arguments.add(mySlot);
		}
	}
	
	@Override
	boolean hasIssue() {
		DataType firstType = expectedSlot[0].getType();
		//shortcut if not known
		if (firstType==DataType.UNKNOWN) {
			return false; 
		}
		
		for (TypeSlot argument : arguments) {
			if (argument.getType()!=firstType && argument.getType()!=DataType.UNKNOWN) {
				return true;
			}
		}
		return false;
	}
	
	

}
