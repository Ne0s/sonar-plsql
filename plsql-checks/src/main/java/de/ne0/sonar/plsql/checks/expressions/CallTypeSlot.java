package de.ne0.sonar.plsql.checks.expressions;

import java.util.Vector;

import com.sonar.sslr.api.AstNode;

public class CallTypeSlot extends TypeSlot {

	private Vector<TypeSlot> arguments = new Vector<TypeSlot>();

	CallTypeSlot(AstNode node) {
		super(1, node);
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

}
