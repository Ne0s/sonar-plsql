package de.ne0.sonar.plsql.checks.expressions;

import com.sonar.sslr.api.AstNode;

import de.ne0.sonar.plsql.checks.utils.CheckHelper.DataType;

public class FixedTypeSlot extends TypeSlot {

	private DataType type;

	FixedTypeSlot(DataType type,AstNode node) {
		super(0, node);
		this.type = type;
	}
	
	@Override
	DataType getType() {
		return type;
	}
	
	@Override
	String getFirstType() {
		return type.toString();
	}

}
