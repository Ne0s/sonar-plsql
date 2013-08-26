package de.ne0.sonar.plsql.checks.utils;

import static de.ne0.sonar.plsql.api.PlSQLPunctuator.LPARENTHESIS;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.RPARENTHESIS;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.STAR;

import com.sonar.sslr.api.AstNode;

import de.ne0.sonar.plsql.api.PlSQLKeyword;
import de.ne0.sonar.plsql.api.PlSQLTokenType;
import de.ne0.sonar.plsql.checks.utils.CheckHelper.DataType;
import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class CheckHelper {

	public enum DataType  {UNKNOWN, CHAR, NUMBER, OBJECT_TYPE, RECORD, BOOLEAN, STRING};
	
	private static final String TYPE_POSTFIX = "_o";
	private static final String COLLECTION_POSTFIX = "_col";

	public static String getPrefixForDataType(final AstNode dataTypeNode) {
		if (!dataTypeNode.is(PlSQLGrammar.DATATYPE)) {
			throw new RuntimeException("Unallowed Node-Type ("+dataTypeNode.getType()+") to derive DataType prefix");
		}
		
		if (dataTypeNode.getFirstChild(PlSQLGrammar.NUMERIC_DATATYPE)!=null) {
			return "n";
		}
		
		if (dataTypeNode.getFirstChild(PlSQLGrammar.CHARACTER_DATATYPE)!=null) {
			if (dataTypeNode.getFirstChild(PlSQLGrammar.CHARACTER_DATATYPE).hasDirectChildren(PlSQLKeyword.CHAR)) {
				return "c";
			}
			return "s";
		}
		
		if (dataTypeNode.getFirstChild(PlSQLGrammar.REF_DATATYPE)!=null) {
			final String objectName = dataTypeNode.getFirstChild(PlSQLGrammar.REF_DATATYPE).getFirstChild(PlSQLGrammar.OBJECT_NAME).getTokenValue().toLowerCase();
			if (objectName.endsWith(TYPE_POSTFIX) || objectName.endsWith(COLLECTION_POSTFIX)) {
				return "u";
			} else if (dataTypeNode.getFirstChild(PlSQLGrammar.REF_DATATYPE).getFirstChild(PlSQLKeyword.ROWTYPE)!=null) {
				return "r";
			} else {
				//below will work, as if no '.' found indexOf will return -1 => substr to start with 0 
				String fieldName = objectName.substring(objectName.lastIndexOf('.')+1);
				//so we just return first character if 2nd or 3rd is an '_' assuming naming conventions met
				if (fieldName.length()>2 && (fieldName.charAt(1)=='_' || fieldName.charAt(2)=='_')) {
					return ""+fieldName.charAt(0);
				}
			}
		}
		
		return "?";
	}

	public static DataType getDataType(AstNode astNode) {
		if (astNode.is(PlSQLGrammar.PRIMARY_EXPRESSION)) {
			return getDataTypeForPrimaryExpression(astNode);
		} else if (astNode.is(PlSQLGrammar.IDENTIFIER_NAME)) {
			return getTypeFromName(astNode.getTokenValue());
		}
		
		return DataType.UNKNOWN;
	}

	private static DataType getDataTypeForPrimaryExpression(AstNode astNode) {
//		b.rule(PRIMARY_EXPRESSION).is(b.firstOf(
//				LEFT_JOIN_OBJECT_NAME,
//		        OBJECT_NAME, -- done
//		        LITERAL, --done
//		        DEBUG_FLAG,
//		        EXPRESSION_KEYWORDS,
//		        STAR, //for stuff like count(*)
//		        
//		     //   ARRAY_LITERAL,
//		     //   OBJECT_LITERAL,
//		        b.sequence(LPARENTHESIS, EXPRESSION, RPARENTHESIS)));
		
		if (astNode.hasDirectChildren(PlSQLGrammar.OBJECT_NAME)) {
			return getTypeFromName(astNode.getFirstChild(PlSQLGrammar.OBJECT_NAME).getTokenValue());
		} else if (astNode.hasDirectChildren(PlSQLGrammar.LITERAL)) {
			AstNode literalNode = astNode.getFirstChild(PlSQLGrammar.LITERAL);
			if (literalNode.hasDirectChildren(PlSQLGrammar.STRING_LITERAL)) {
				return DataType.STRING;
			} else if (literalNode.hasDirectChildren(PlSQLTokenType.NUMERIC_LITERAL)) {
				return DataType.NUMBER;
			} else if (literalNode.hasDirectChildren(PlSQLGrammar.BOOLEAN_LITERAL)) {
				return DataType.BOOLEAN;
			} else {
				return DataType.UNKNOWN;
			}
		}
		
		return DataType.UNKNOWN;
	}

	private static DataType getTypeFromName(String objectName) {
		//below will work, as if no '.' found indexOf will return -1 => substr to start with 0 
		String fieldName = objectName.substring(objectName.lastIndexOf('.')+1);
		//so we just return first character if 2nd or 3rd is an '_' assuming naming conventions met
		if (fieldName.length()>2 && (fieldName.charAt(1)=='_' || fieldName.charAt(2)=='_')) {
			switch (fieldName.charAt(0)) {
			case 'n' : return DataType.NUMBER;
			case 's' : return DataType.STRING;
			case 'u' : return DataType.OBJECT_TYPE;
			case 'b' : return DataType.BOOLEAN;
			case 'r' : return DataType.RECORD;
			default : return DataType.UNKNOWN;
			}
		}
		return DataType.UNKNOWN;
	}
	
}
