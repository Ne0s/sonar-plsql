package de.ne0.sonar.plsql.checks.expressions;

import java.util.Vector;

import com.sonar.sslr.api.AstNode;

public class CaseTypeSlot extends TypeSlot {

	private Vector<TypeSlot> arguments = new Vector<TypeSlot>();

	CaseTypeSlot(AstNode node) {
		super(0, node);
	}
		
	@Override
	void fillSlot(TypeSlot mySlot) {
			//should be an argument
			arguments.add(mySlot);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "CASE ->> " + super.toString();
	}

}
