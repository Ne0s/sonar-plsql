package de.ne0.sonar.plsql.checks.expressions;

import java.util.Vector;

import com.sonar.sslr.api.AstNode;

import de.ne0.sonar.plsql.checks.utils.CheckHelper.DataType;

public class TypeSlot {
	final int slotCount;
	final AstNode node;
	final TypeSlot[] expectedSlot;
	int currentSlot;
	final Vector<TypeSlot> additionalSlot = new Vector<TypeSlot>();
	TypeSlot outerSlot;
	private final DataType requiredType;
	private final boolean forceRequired;
	
	public void setOuterSlot(TypeSlot outerSlot) {
		this.outerSlot = outerSlot;
	}
	
	public TypeSlot getOuterSlot() {
		return outerSlot;
	}
	
	
	
	TypeSlot(int slotCount, AstNode node) {
		this(slotCount, node, DataType.UNKNOWN);
	}
	
	TypeSlot(int slotCount, AstNode node, DataType requiredType) {
		this(slotCount, node, requiredType, true);
	}
	
	TypeSlot(int slotCount, AstNode node, DataType requiredType, boolean forceRequired) {
		this.requiredType = requiredType;
		this.slotCount = slotCount;
		this.expectedSlot = new TypeSlot[slotCount];
		currentSlot = 0;
		this.node = node;
		this.forceRequired = forceRequired;
		
//		System.out.println(this);
	}
	
	void fillSlot(TypeSlot mySlot) {
//		System.out.println("\t"+toString()+"\nfillSlot "+currentSlot+"(of "+expectedSlot.length+") with "+mySlot);
		if (currentSlot<expectedSlot.length) {
			expectedSlot[currentSlot] = mySlot;
			currentSlot++;
		} else {
			//additional Slot
			additionalSlot.add(mySlot);
		}
		mySlot.setOuterSlot(this);
		
//		System.out.println(this);
	}
	
	boolean hasIssue() {
//		System.out.println(toString()+"\nIssue?  => required: "+requiredType);
//		for (int i=0; i<slotCount; i++) {
//			System.out.println("\tSlot "+i+": "+(slots[i]==null?"null":slots[i].getType()));
//		}
		DataType type = DataType.UNKNOWN;
		if (forceRequired) {
			type = requiredType;
			
		} else if (expectedSlot.length>0 && expectedSlot[0]!=null) {
			type = expectedSlot[0].getType();
		}
		
		for (int i=0; i<slotCount; i++) {
			DataType t1 = (expectedSlot[i]==null?DataType.UNKNOWN:expectedSlot[i].getType());
			if (type!=DataType.UNKNOWN && t1!=DataType.UNKNOWN && type!=t1) {
				return true;
			}
			
			if (type==DataType.UNKNOWN && t1!=DataType.UNKNOWN) {
				type = t1;
			}
		}
		return false;
	}

	String getFirstType() {
		return expectedSlot[0].getType().toString();
	}
	
	String getSecondType() {
		if (slotCount<2) return "<UNKNOWN>";
		
		return expectedSlot[1].getType().toString();
	}
	
	
	DataType getType() {
		//if we have a required type... than we assume that outcome is of that type
		if (requiredType!=DataType.UNKNOWN) {
			return requiredType;
		}
		
		if (expectedSlot.length==0 || expectedSlot[0]==null) {
			return DataType.UNKNOWN;
		}
		DataType type = expectedSlot[0].getType();
		//shortcut
		if (type==DataType.UNKNOWN) {
			return type;
		}
		
		for (int i=1; i<slotCount; i++) {
			if (expectedSlot[i]==null) {
				return DataType.UNKNOWN;
			}
			DataType t2 = expectedSlot[i].getType();
			if (t2!=type) {
				type = DataType.UNKNOWN;
			}
		}
		
		return type;
	}
	
	public String toString() {
		StringBuffer b = new StringBuffer("Slot["+node.getType()+"/"+ node.getTokenValue()+"] ");
		b.append("Type: ");
		b.append(this.getType());
		b.append(" Slots:");
		for (int i=0; i<expectedSlot.length; i++) {
			b.append(i);
			if (i==currentSlot) {
				b.append("*");
			}
			b.append(". ["+expectedSlot[i]+"]");
		}
		return b.toString();
	};

}
