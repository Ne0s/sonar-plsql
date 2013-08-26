package de.ne0.sonar.plsql.parser;

import static de.ne0.sonar.plsql.api.PlSQLKeyword.*;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.BEGIN;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.BODY;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.CHAR;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.CREATE;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.DATE;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.DEFAULT;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.DELETE;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.END;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.EXCEPTION;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.FALSE;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.IN;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.INTEGER;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.IS;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.KEY;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.NEW;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.NOT;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.NULL;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.NUMBER;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.OTHERS;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.OUT;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.PACKAGE;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.PRIMARY;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.PROCEDURE;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.REPLACE;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.THEN;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.TIME;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.TRUE;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.UNIQUE;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.VARCHAR;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.VARCHAR2;
import static de.ne0.sonar.plsql.api.PlSQLKeyword.WHEN;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.AND;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.ANDAND;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.AND_EQU;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.BANG;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.COLON;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.COMMA;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.DEC;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.DIV;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.DIV_EQU;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.DOT;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.EQU;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.EQUAL;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.EQUAL2;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.GE;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.GT;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.INC;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.LBRACKET;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.LCURLYBRACE;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.LE;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.LPARENTHESIS;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.LT;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.MINUS;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.MINUS_EQU;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.MOD;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.MOD_EQU;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.NOTEQUAL;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.NOTEQUAL2;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.OR;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.OROR;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.OR_EQU;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.PLUS;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.PLUS_EQU;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.QUERY;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.RBRACKET;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.RCURLYBRACE;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.RPARENTHESIS;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.SEMI;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.SL;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.SL_EQU;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.SR;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.SR2;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.SR_EQU;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.SR_EQU2;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.STAR;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.STAR_EQU;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.*;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.XOR;
import static de.ne0.sonar.plsql.api.PlSQLPunctuator.XOR_EQU;
import static de.ne0.sonar.plsql.api.PlSQLTokenType.EMPTY;
import static de.ne0.sonar.plsql.api.PlSQLTokenType.IDENTIFIER;



import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerlessGrammarBuilder;
import org.sonar.sslr.parser.LexerlessGrammar;

import com.sonar.sslr.api.GenericTokenType;
import com.sonar.sslr.impl.ast.SkipFromAstIfOnlyOneChild;

import de.ne0.sonar.plsql.api.PlSQLKeyword;
import de.ne0.sonar.plsql.api.PlSQLPunctuator;
import de.ne0.sonar.plsql.api.PlSQLTokenType;
import de.ne0.sonar.plsql.lexer.PlSQLLexer;

public enum PlSQLGrammar implements GrammarRuleKey {

	/**
	 * Spacing.
	 */
	SPACING,

	/**
	 * Spacing without line break.
	 */
	SPACING_NO_LB, NEXT_NOT_LB, LINE_TERMINATOR_SEQUENCE,

	/**
	 * End of statement.
	 */

	EOS, //EOS_NO_LB,
	
	
	
	LABEL, 
	DEBUG_FLAG,
	IDENTIFIER_NAME,
	OBJECT_NAME,
	LEFT_JOIN_OBJECT_NAME,
	OBJECT_REFERENCE,
	
	/**
	 * A.1 Lexical
	 */
	LITERAL,
	  NULL_LITERAL,
	  BOOLEAN_LITERAL,
	  STRING_LITERAL,
	KEYWORD, LETTER_OR_DIGIT,

	/**
	 * DataTypes
	 */
	INLINE_CONSTRAINT,
	DATATYPE, CHARACTER_DATATYPE, NUMERIC_DATATYPE,DATE_DATATYPE,LOCAL_TIMEZONE, RAW_DATATYPE,REF_DATATYPE,EXCEPTION_DATATYPE,
	
	/**
	 * A.3 Expressions
	 */
	EXPRESSION,
	IN_EXPRESSION,
	UNARY_EXPRESSION,
	
	MEMBER_EXPRESSION,
	POSTFIX_EXPRESSION,
	ASSIGNMENT_EXPRESSION,
	FUNCTION_EXPRESSION,
	CALL_EXPRESSION,ARGUMENTS,ARGUMENT,
	CASE_EXPRESSION,
	NEW_EXPRESSION,
	MULTIPLICATIVE_EXPRESSION,
	CONCAT_EXPRESSION,
	ADDITIVE_EXPRESSION,
	ASSIGNMENT_OPERATOR,
	
	
	PRIMARY_EXPRESSION,BRACKED_EXPRESSION,
	EXPRESSION_KEYWORDS,
	BOOLEAN_COMP_EXPRESSION,
	BOOLEAN_EXPRESSION,
	CHARACTER_EXPRESSION,
	DATE_EXPRESSION,
	NUMERIC_EXPRESSION,
	SIMPLE_CASE_EXPRESSION,
	SEARCHED_CASE_EXPRESSION,
	
	
	
	/**
	 * A.4 Statements
	 */

	EXCEPTION_HANDLER,EXCEPTION_DEFINITION, 
	TYPE_DEFINITION, RECORD_TYPE, NORMAL_VARIABLE_DECLARATION, CURSOR_DECLARATION, VARIABLE_DECLARATION, STATEMENT, STATEMENT_LIST, COMMIT_STATEMENT, ROLLBACK_STATEMENT, EXIT_STATEMENT, RETURN_STATEMENT, RAISE_STATEMENT,  NULL_STATEMENT,ASSIGNMENT_STATEMENT, FORALL_STATEMENT, LOOP_STATEMENT, CASE_STATEMENT, IF_STATEMENT,CONDITIONAL_IF_STATEMENT, CALL_STATEMENT,BLOCK_STATEMENT,
	UPDATE_COLUMN, 
	INSERT_STATEMENT,DELETE_STATEMENT, UPDATE_STATEMENT, RETURNING_CLAUSE, SELECT_STATEMENT, SELECT_EXPRESSION, INTO_CLAUSE, WHERE_CLAUSE, GROUP_BY_CLAUSE, ORDER_BY_CLAUSE,CONNECT_BY_CLAUSE,  TABLE_REFERENCE,TABLE_CLAUSE,
	GRANT_STATEMENT,OBJECT_PRIVILEGE,
	SELECT_COLUMN, NAMED_COL, ANALYTIC_COL, ANALYTIC_ORDER_BY_CLAUSE, QUERY_PARTITION_CLAUSE, WILDCARD_COL, UNNAMED_COL,BULK_COLLECT,

	/**
	 * A.5 functions and Programms
	 */
	PARAMETER_DECLARATION,
	WITH_PARAMETERS,
	FUNCTION_DECLARATION,
	PROCEDURE_DECLARATION,
	FUNCTION_BODY,
	PROCEDURE_BODY,
	OR_REPLACE,

	PACKAGE_DECLARATION, PACKAGE_BODY_DECLARATION, PROGRAM,SOURCE_ELEMENT, 
	//SOURCE_ELEMENTS, 
	

	
	CREATE_SYNONYM, 
	
	/**
	 * TABLES
	 */
	ALTER_TABLE,
	CREATE_INDEX, 
	CREATE_VIEW, 
	CREATE_SEQUENCE, SEQUENCE_PROPERTY, 
	CREATE_TABLE,
	TABLE_COMMENT,
	COLUMN_DEFINITION,
	PHYSICAL_TABLE_PROPERTIES,TABLESPACE_PROPERTY, PARALLEL_PROPERTY,PARTITION_PROPERTY,LIST_PARTITION,
	TABLE_CONSTRAINT_CLAUSE,
	USING_INDEX,
	
	/**
	 * End of file.
	 */
	EOF, EXECUTE_PLSQL_BUFFER;

	private static void punctuators(LexerlessGrammarBuilder b) {
		punctuator(b, LCURLYBRACE, "{");
		punctuator(b, RCURLYBRACE, "}");
		punctuator(b, LPARENTHESIS, "(");
		punctuator(b, RPARENTHESIS, ")");
		punctuator(b, LBRACKET, "[");
		punctuator(b, RBRACKET, "]");
		punctuator(b, DOT, ".", b.nextNot("."));
		punctuator(b, DOTDOT, "..", b.nextNot("."));
		punctuator(b, SEMI, ";");
		punctuator(b, COMMA, ",");
		punctuator(b, LT, "<", b.nextNot(b.firstOf("=",">","<")));
		punctuator(b, GT, ">", b.nextNot("="));
		punctuator(b, LE, "<=");
		punctuator(b, GE, ">=");
		punctuator(b, EQUAL, "==", b.nextNot("="));
		punctuator(b, NOTEQUAL, "!=", b.nextNot("="));
		punctuator(b, NOTEQUAL3, "<>");
		punctuator(b, EQUAL2, "===");
		punctuator(b, NOTEQUAL2, "!==");
		punctuator(b, PLUS, "+", b.nextNot(b.firstOf("+", "=")));
		punctuator(b, MINUS, "-", b.nextNot(b.firstOf("-", "=")));
		punctuator(b, STAR, "*", b.nextNot("="));
		punctuator(b, MOD, "%", b.nextNot("="));
		punctuator(b, DIV, "/", b.nextNot("="));
		punctuator(b, INC, "++");
		punctuator(b, DEC, "--");
		punctuator(b, SL, "<<", b.nextNot(b.firstOf("<", "=")));
		punctuator(b, SR, ">>", b.nextNot(b.firstOf(">", "=")));
		punctuator(b, SR2, ">>>");
		punctuator(b, AND, "&", b.nextNot("&", "="));
		punctuator(b, OR, "|", b.nextNot("="));
		punctuator(b, XOR, "^", b.nextNot("="));
		punctuator(b, BANG, "!", b.nextNot("="));
		punctuator(b, TILDA, "~");
		punctuator(b, ANDAND, "&&");
		punctuator(b, OROR, "||");
		punctuator(b, QUERY, "?");
		punctuator(b, COLON, ":");
		punctuator(b, ASSIGNMENT, ":=");
		punctuator(b, EQU, "=", b.nextNot("="));
		punctuator(b, PLUS_EQU, "+=");
		punctuator(b, MINUS_EQU, "-=");
		punctuator(b, DIV_EQU, "/=");
		punctuator(b, STAR_EQU, "*=");
		punctuator(b, MOD_EQU, "%=");
		punctuator(b, SL_EQU, "<<=");
		punctuator(b, SR_EQU, ">>=");
		punctuator(b, SR_EQU2, ">>>=");
		punctuator(b, AND_EQU, "&=");
		punctuator(b, OR_EQU, "|=");
		punctuator(b, XOR_EQU, "^=");
		punctuator(b, EQU_GT, "=>");
	}

	private static void punctuator(LexerlessGrammarBuilder b,
			GrammarRuleKey ruleKey, String value) {
		for (PlSQLPunctuator tokenType : PlSQLPunctuator.values()) {
			if (value.equals(tokenType.getValue())) {
				b.rule(tokenType).is(SPACING, value);
				return;
			}
		}
		throw new IllegalStateException(value);
	}

	private static void punctuator(LexerlessGrammarBuilder b,
			GrammarRuleKey ruleKey, String value, Object element) {
		for (PlSQLPunctuator tokenType : PlSQLPunctuator.values()) {
			if (value.equals(tokenType.getValue())) {
				b.rule(tokenType).is(SPACING, value, element);
				return;
			}
		}
		throw new IllegalStateException(value);
	}

	private static final Object makeCaseInsensitiveRule(LexerlessGrammarBuilder b, String value) {
		final String valueLow = value.toLowerCase();
		final String valueUp = value.toUpperCase();
		final StringBuilder regexp = new StringBuilder();
		for (int j=0; j<valueLow.length(); j++) {
			regexp.append("["+valueLow.charAt(j)+valueUp.charAt(j)+"]");
		}
		return b.regexp(regexp.toString());
	}
	
	private static void keywords(LexerlessGrammarBuilder b) {
		b.rule(LETTER_OR_DIGIT).is(b.regexp("\\p{javaJavaIdentifierPart}"));
		Object[] rest = new Object[PlSQLKeyword.values().length - 2];
		for (int i = 0; i < PlSQLKeyword.values().length; i++) {
			PlSQLKeyword tokenType = PlSQLKeyword.values()[i];
			
			b.rule(tokenType).is(SPACING, makeCaseInsensitiveRule(b, tokenType.getValue()),
					b.nextNot(LETTER_OR_DIGIT));
			if (i > 1) {
				rest[i - 2] = tokenType.getValue();
			}
		}
		b.rule(KEYWORD).is(
				b.firstOf(PlSQLKeyword.keywordValues()[0],
						PlSQLKeyword.keywordValues()[1], rest),
				b.nextNot(LETTER_OR_DIGIT));
	}

	private static void lexical(LexerlessGrammarBuilder b) {
		b.rule(SPACING)
				.is(b.skippedTrivia(b.regexp("[" + PlSQLLexer.LINE_TERMINATOR
						+ PlSQLLexer.WHITESPACE + "]*+")),
						b.zeroOrMore(
								b.commentTrivia(b.regexp(PlSQLLexer.COMMENT)),
								b.skippedTrivia(b.regexp("["
										+ PlSQLLexer.LINE_TERMINATOR
										+ PlSQLLexer.WHITESPACE + "]*+"))))
				.skip();

		b.rule(SPACING_NO_LB)
				.is(b.zeroOrMore(b.firstOf(
						b.skippedTrivia(b.regexp("[" + PlSQLLexer.WHITESPACE
								+ "]++")),
						b.commentTrivia(b.regexp("(?:"
								+ PlSQLLexer.SINGLE_LINE_COMMENT + "|"
								+ PlSQLLexer.MULTI_LINE_COMMENT_NO_LB + ")")))))
				.skip();
		b.rule(NEXT_NOT_LB)
				.is(b.nextNot(b.regexp("(?:" + PlSQLLexer.MULTI_LINE_COMMENT
						+ "|[" + PlSQLLexer.LINE_TERMINATOR + "])"))).skip();

		b.rule(LINE_TERMINATOR_SEQUENCE)
				.is(b.skippedTrivia(b
						.regexp("(?:\\n|\\r\\n|\\r|\\u2028|\\u2029)"))).skip();

		// Empty token is mandatory for the next two rules, because Toolkit is
		// unable to work with AstNode without tokens
		Object emptyToken = b.token(EMPTY, "");

		b.rule(EXECUTE_PLSQL_BUFFER).is(SPACING,
				/*LINE_TERMINATOR_SEQUENCE,*/DIV,SPACING_NO_LB, b.firstOf(LINE_TERMINATOR_SEQUENCE, EOF));
		
		b.rule(EOS).is(
				b.firstOf(SEMI, EXECUTE_PLSQL_BUFFER, EOF 
						//,b.sequence(SPACING, emptyToken, b.endOfInput())
						),
						b.optional(EXECUTE_PLSQL_BUFFER));
//
//		b.rule(EOS_NO_LB).is(
//				b.firstOf(b.sequence(SPACING_NO_LB, NEXT_NOT_LB, SEMI), b
//						.sequence(SPACING_NO_LB, emptyToken,
//								LINE_TERMINATOR_SEQUENCE), b.sequence(
//						SPACING_NO_LB, emptyToken, b.next("}")), b.sequence(
//						SPACING_NO_LB, emptyToken, b.endOfInput())));

		b.rule(EOF).is(b.token(GenericTokenType.EOF, b.endOfInput())).skip();
		b.rule(IDENTIFIER).is(SPACING, b.nextNot(KEYWORD),
				b.regexp(PlSQLLexer.IDENTIFIER));

		
		b.rule(OBJECT_NAME).is(SPACING, //TODO: check if remove keywords is ok b.nextNot(KEYWORD),
				//b.optional(b.regexp(PlSQLLexer.IDENTIFIER), DOT),
				b.regexp(PlSQLLexer.IDENTIFIER+"(?:\\."+PlSQLLexer.IDENTIFIER+")*"));
		
		b.rule(PlSQLTokenType.NUMERIC_LITERAL).is(
		        SPACING,
		        b.regexp(PlSQLLexer.NUMERIC_LITERAL));
		    b.rule(STRING_LITERAL).is(
		        SPACING,
		        b.token(GenericTokenType.LITERAL, b.regexp(PlSQLLexer.LITERAL)));
		    

		punctuators(b);
		keywords(b);

	}

	/**
	 * A.4 Statement
	 */
	private static void statements(LexerlessGrammarBuilder b) {
		b.rule(RECORD_TYPE).is(RECORD, LPARENTHESIS, IDENTIFIER_NAME, DATATYPE, b.zeroOrMore(COMMA, IDENTIFIER_NAME, DATATYPE), RPARENTHESIS);
		b.rule(EXCEPTION_DEFINITION).is(PRAGMA, EXCEPTION_INIT, LPARENTHESIS, IDENTIFIER_NAME, COMMA, UNARY_EXPRESSION, RPARENTHESIS, SEMI);
		b.rule(TYPE_DEFINITION).is(b.firstOf(TYPE,SUBTYPE), IDENTIFIER_NAME, IS, b.firstOf(RECORD_TYPE, b.sequence(wordCaseIndepend(b, "ref"), CURSOR), DATATYPE), SEMI);
		b.rule(VARIABLE_DECLARATION).is(b.nextNot(BEGIN), b.nextNot(END), IDENTIFIER_NAME, b.optional(CONSTANT), DATATYPE, b.optional(b.optional(NOT, NULL), b.firstOf(ASSIGNMENT, DEFAULT), EXPRESSION),SEMI);
		b.rule(CURSOR_DECLARATION).is(CURSOR, IDENTIFIER_NAME, IS, SELECT_EXPRESSION, SEMI);
		b.rule(NORMAL_VARIABLE_DECLARATION).is(b.firstOf(CURSOR_DECLARATION, VARIABLE_DECLARATION)).skip();
		
		b.rule(DELETE_STATEMENT).is(DELETE, FROM, OBJECT_NAME, WHERE_CLAUSE, EOS);
		
		b.rule(UPDATE_COLUMN).is(OBJECT_NAME, EQU, EXPRESSION);
		b.rule(RETURNING_CLAUSE).is(b.firstOf(RETURN, wordCaseIndepend(b, "RETURNING")), SELECT_COLUMN, b.zeroOrMore(COMMA, SELECT_COLUMN), INTO_CLAUSE);
		b.rule(UPDATE_STATEMENT).is(UPDATE, TABLE_CLAUSE, SET, UPDATE_COLUMN, b.zeroOrMore(COMMA, UPDATE_COLUMN), b.optional(WHERE_CLAUSE), 
				b.optional(RETURNING_CLAUSE), EOS);
		
		b.rule(INSERT_STATEMENT).is(INSERT, INTO, IDENTIFIER_NAME, b.optional(LPARENTHESIS, IDENTIFIER_NAME, b.zeroOrMore(COMMA, IDENTIFIER_NAME), RPARENTHESIS),
				VALUES, b.firstOf(b.sequence(LPARENTHESIS, EXPRESSION, b.zeroOrMore(COMMA, EXPRESSION), RPARENTHESIS), OBJECT_NAME), 
				b.optional(RETURNING_CLAUSE),
				SEMI
				);
		
		b.rule(BULK_COLLECT).is(wordCaseIndepend(b, "bulk"), wordCaseIndepend(b, "collect"));
		
		//Column types
		b.rule(WILDCARD_COL).is(b.firstOf(STAR, b.sequence(OBJECT_NAME, word(b, ".*"))));
		b.rule(QUERY_PARTITION_CLAUSE).is(PARTITION, BY, LPARENTHESIS, EXPRESSION, b.zeroOrMore(COMMA, EXPRESSION), RPARENTHESIS);
		b.rule(ANALYTIC_ORDER_BY_CLAUSE).is(ORDER, b.optional(wordCaseIndepend(b, "SIBLINGS")),BY, EXPRESSION, b.firstOf(ASC, DESC));
		b.rule(ANALYTIC_COL).is(IDENTIFIER_NAME /*Anayltic-FunctionName */, LPARENTHESIS, b.optional(IDENTIFIER_NAME, b.zeroOrMore(COMMA, IDENTIFIER_NAME)),RPARENTHESIS, 
				wordCaseIndepend(b, "over"),LPARENTHESIS, b.optional(QUERY_PARTITION_CLAUSE), b.optional(ANALYTIC_ORDER_BY_CLAUSE),RPARENTHESIS);
		b.rule(NAMED_COL).is(b.firstOf(b.sequence(b.firstOf(ANALYTIC_COL, EXPRESSION), AS, IDENTIFIER_NAME), 
				b.sequence(b.firstOf(ANALYTIC_COL, EXPRESSION), b.nextNot(INTO),b.nextNot(FROM),b.nextNot(BULK_COLLECT), IDENTIFIER_NAME)));
		b.rule(UNNAMED_COL).is(b.nextNot(INTO),b.nextNot(FROM),b.nextNot(BULK_COLLECT), EXPRESSION);

		b.rule(SELECT_COLUMN).is(b.firstOf(WILDCARD_COL, NAMED_COL, UNNAMED_COL));
		
		b.rule(SELECT_EXPRESSION).is(SELECT, /*hint?*/ b.optional(b.firstOf(ALL, DISTINCT, UNIQUE)),
				//select list
				SELECT_COLUMN, b.zeroOrMore(COMMA, SELECT_COLUMN),
				//b.firstOf(STAR,b.sequence(OBJECT_NAME, word(b, ".*")), b.sequence(EXPRESSION, b.optional(AS, IDENTIFIER_NAME), b.zeroOrMore(COMMA, EXPRESSION, b.optional(AS, IDENTIFIER_NAME)))),
				b.optional(INTO_CLAUSE),
				FROM, TABLE_CLAUSE,b.zeroOrMore(COMMA, TABLE_CLAUSE),
				b.optional(WHERE_CLAUSE),
				b.optional(GROUP_BY_CLAUSE),
				b.optional(ORDER_BY_CLAUSE),
				b.optional(CONNECT_BY_CLAUSE),
				b.optional(b.firstOf(MINUS, b.sequence(UNION, ALL)), SELECT_EXPRESSION));
		b.rule(CONNECT_BY_CLAUSE).is(START, WITH, EXPRESSION, CONNECT, BY,PRIOR, EXPRESSION);
		b.rule(SELECT_STATEMENT).is(SELECT_EXPRESSION, EOS);
		b.rule(INTO_CLAUSE).is(b.optional(BULK_COLLECT), INTO, OBJECT_NAME, b.zeroOrMore(COMMA, OBJECT_NAME));
		b.rule(TABLE_CLAUSE).is(b.firstOf(b.sequence(LPARENTHESIS,SELECT_EXPRESSION, RPARENTHESIS), TABLE_REFERENCE), b.optional(b.nextNot(WHERE),b.nextNot(SET), b.firstOf(b.sequence(AS, IDENTIFIER_NAME), IDENTIFIER_NAME)));
		b.rule(TABLE_REFERENCE).is(IDENTIFIER_NAME);
		b.rule(WHERE_CLAUSE).is(WHERE, EXPRESSION);
		b.rule(GROUP_BY_CLAUSE).is(GROUP, BY, EXPRESSION, b.zeroOrMore(COMMA, EXPRESSION));
		b.rule(ORDER_BY_CLAUSE).is(ORDER, BY, EXPRESSION, b.optional(b.firstOf(ASC, DESC)), b.zeroOrMore(COMMA, EXPRESSION, b.optional(b.firstOf(ASC, DESC))));
		
		b.rule(CASE_STATEMENT).is(b.optional(LABEL), CASE, b.oneOrMore(WHEN, BOOLEAN_EXPRESSION, THEN, b.oneOrMore(STATEMENT)), b.optional(ELSE, b.oneOrMore(STATEMENT)), END, CASE, b.optional(IDENTIFIER_NAME), SEMI);
		b.rule(EXIT_STATEMENT).is(EXIT, b.optional(WHEN, EXPRESSION), SEMI);
		b.rule(RETURN_STATEMENT).is(RETURN, EXPRESSION, SEMI);
		b.rule(COMMIT_STATEMENT).is(COMMIT, SEMI);
		b.rule(ROLLBACK_STATEMENT).is(ROLLBACK, SEMI);
		b.rule(RAISE_STATEMENT).is(RAISE, b.optional(IDENTIFIER_NAME), SEMI);
		b.rule(IF_STATEMENT).is(IF, EXPRESSION, THEN, b.oneOrMore(STATEMENT), b.zeroOrMore(ELSIF, /*BOOLEAN_*/EXPRESSION, THEN, b.oneOrMore(STATEMENT)), b.optional(ELSE, b.oneOrMore(STATEMENT)), END, IF, SEMI);
		b.rule(CONDITIONAL_IF_STATEMENT).is(CONDITIONAL_IF, EXPRESSION, CONDITIONAL_THEN, b.oneOrMore(STATEMENT), b.zeroOrMore(CONDITIONAL_ELSIF, /*BOOLEAN_*/EXPRESSION, CONDITIONAL_THEN, b.oneOrMore(STATEMENT)), b.optional(CONDITIONAL_ELSE, b.oneOrMore(STATEMENT)), CONDITIONAL_END);
		b.rule(FORALL_STATEMENT).is(wordCaseIndepend(b, "FORALL"),IDENTIFIER_NAME, IN, EXPRESSION, DOTDOT, EXPRESSION, STATEMENT);
		b.rule(LOOP_STATEMENT).is(b.optional(LABEL),
				b.optional(b.firstOf(
						//FOR Statement
						b.sequence(FOR, IDENTIFIER_NAME, IN, b.firstOf(b.sequence(LPARENTHESIS, SELECT_EXPRESSION, RPARENTHESIS) ,b.sequence(b.optional(REVERSE), EXPRESSION, DOTDOT, EXPRESSION))), 
						//While Statement
						b.sequence(WHILE, EXPRESSION))),
				LOOP, b.oneOrMore(STATEMENT), END, LOOP, b.optional(IDENTIFIER_NAME), SEMI);
		
		
		b.rule(EXCEPTION_HANDLER).is(WHEN, b.firstOf(OTHERS, IDENTIFIER_NAME), THEN, b.oneOrMore(STATEMENT));
		b.rule(ASSIGNMENT_STATEMENT).is(OBJECT_REFERENCE, ASSIGNMENT, EXPRESSION, SEMI);
		b.rule(BLOCK_STATEMENT).is(b.zeroOrMore(b.firstOf(NORMAL_VARIABLE_DECLARATION, TYPE_DEFINITION)), b.zeroOrMore(b.firstOf(FUNCTION_BODY, PROCEDURE_BODY)), BEGIN, b.zeroOrMore(STATEMENT), b.optional(EXCEPTION, b.oneOrMore(EXCEPTION_HANDLER)), END, b.optional(IDENTIFIER_NAME), SEMI);
		b.rule(CALL_STATEMENT).is(EXPRESSION, SEMI);
		b.rule(NULL_STATEMENT).is(NULL, SEMI);
		
		b.rule(OBJECT_PRIVILEGE).is(b.firstOf(ALTER, DELETE, EXECUTE, //DEBUG, FLASHBACK
				INDEX, INSERT, SELECT, UPDATE
				));
		b.rule(GRANT_STATEMENT).is(GRANT, OBJECT_PRIVILEGE, b.zeroOrMore(COMMA, OBJECT_PRIVILEGE), ON, OBJECT_REFERENCE, TO, 
				b.firstOf(b.sequence(SPACING, b.regexp(PlSQLLexer.IDENTIFIER+"(?:[\\#])")), IDENTIFIER_NAME), // for roles 
				EOS);
		
		
		b.rule(STATEMENT).is(b.nextNot(END), b.firstOf(GRANT_STATEMENT, COMMIT_STATEMENT, ROLLBACK_STATEMENT, EXIT_STATEMENT, RETURN_STATEMENT, RAISE_STATEMENT, IF_STATEMENT, CONDITIONAL_IF_STATEMENT, CASE_STATEMENT, SELECT_STATEMENT, INSERT_STATEMENT, UPDATE_STATEMENT, DELETE_STATEMENT, FORALL_STATEMENT, LOOP_STATEMENT, ASSIGNMENT_STATEMENT, NULL_STATEMENT, CALL_STATEMENT, BLOCK_STATEMENT));
	}

	private static void datatypes(LexerlessGrammarBuilder b) {
		//Character Datatypes
		b.rule(CHARACTER_DATATYPE).is(b.firstOf(VARCHAR2, VARCHAR, CHAR), b.optional(LPARENTHESIS, PlSQLTokenType.NUMERIC_LITERAL,b.optional(b.firstOf(wordCaseIndepend(b, "byte"), wordCaseIndepend(b, "char"))), RPARENTHESIS));
		
		//Numeric Datatypes
		b.rule(NUMERIC_DATATYPE).is(b.firstOf(NUMBER, INTEGER, BINARY_INTEGER), b.optional(LPARENTHESIS, PlSQLTokenType.NUMERIC_LITERAL,b.optional(COMMA, PlSQLTokenType.NUMERIC_LITERAL), RPARENTHESIS));
		
		//Date Datatypes
		b.rule(LOCAL_TIMEZONE).is(WITH, wordCaseIndepend(b, "LOCAL"), TIME, wordCaseIndepend(b, "ZONE"));
		b.rule(DATE_DATATYPE).is(b.firstOf(DATE, TIME, wordCaseIndepend(b, "timestamp")), b.optional(LPARENTHESIS, PlSQLTokenType.NUMERIC_LITERAL, RPARENTHESIS), b.optional(LOCAL_TIMEZONE));
		
		
		//LOB Datatypes
		
		//RAW Types
		b.rule(RAW_DATATYPE).is(wordCaseIndepend(b, "raw"), b.optional(LPARENTHESIS, PlSQLTokenType.NUMERIC_LITERAL, RPARENTHESIS));
		
		//Ref
		b.rule(REF_DATATYPE).is(OBJECT_NAME, b.optional(MOD, b.firstOf(TYPE, ROWTYPE)));
		b.rule(EXCEPTION_DATATYPE).is(EXCEPTION);
		
		b.rule(DATATYPE).is(b.firstOf(b.sequence(TABLE, OF, DATATYPE, b.optional(INDEX,BY,DATATYPE)), CHARACTER_DATATYPE, NUMERIC_DATATYPE, DATE_DATATYPE, RAW_DATATYPE, EXCEPTION_DATATYPE, REF_DATATYPE));
	}
	
	private static void expressions(LexerlessGrammarBuilder b) {

		b.rule(LEFT_JOIN_OBJECT_NAME).is(OBJECT_NAME,LPARENTHESIS,PLUS,RPARENTHESIS);
		b.rule(EXPRESSION_KEYWORDS).is(b.firstOf(ROWNUM, NULL));
		b.rule(PRIMARY_EXPRESSION).is(b.firstOf(
				LEFT_JOIN_OBJECT_NAME,
		        OBJECT_NAME,
		        LITERAL,
		        DEBUG_FLAG,
		        EXPRESSION_KEYWORDS,
		        STAR //for stuff like count(*)
		        
		     //   ARRAY_LITERAL,
		     //   OBJECT_LITERAL,
		        ));
		b.rule(BRACKED_EXPRESSION).is(b.firstOf(PRIMARY_EXPRESSION, b.sequence(LPARENTHESIS, EXPRESSION, RPARENTHESIS))).skipIfOneChild();
		b.rule(CASE_EXPRESSION).is(CASE, b.optional(b.nextNot(WHEN), IDENTIFIER_NAME), b.oneOrMore(WHEN, EXPRESSION, THEN, EXPRESSION), b.optional(ELSE, EXPRESSION), END);
		b.rule(NEW_EXPRESSION).is(NEW, b.firstOf(CALL_EXPRESSION, IDENTIFIER_NAME));
		    b.rule(MEMBER_EXPRESSION).is(
		        b.firstOf(
		        		NEW_EXPRESSION,
		        		CASE_EXPRESSION,
		        		BRACKED_EXPRESSION
		          
		           ),
		        b.zeroOrMore(b.firstOf(
		            b.sequence(LBRACKET, EXPRESSION, RBRACKET),
		            b.sequence(DOT, IDENTIFIER_NAME)))).skipIfOneChild();
		    b.rule(CALL_EXPRESSION).is(b.firstOf(
		    	b.sequence(b.firstOf(wordCaseIndepend(b, "cast"), wordCaseIndepend(b, "treat")), LPARENTHESIS, EXPRESSION, AS, DATATYPE, RPARENTHESIS)	
		        ,b.sequence(MEMBER_EXPRESSION, ARGUMENTS)//,
//		        b.zeroOrMore(b.firstOf(
//		            ARGUMENTS,
//		            b.sequence(LBRACKET, EXPRESSION, RBRACKET),
//		            b.sequence(DOT, IDENTIFIER_NAME)))
		         )   );
		   // b.rule(ARGUMENTS).is(LPARENTHESIS, b.optional(ASSIGNMENT_EXPRESSION, b.zeroOrMore(COMMA, ASSIGNMENT_EXPRESSION)), RPARENTHESIS);
		    b.rule(ARGUMENTS).is(LPARENTHESIS, b.optional(ARGUMENT, b.zeroOrMore(COMMA, ARGUMENT)), RPARENTHESIS);
		    b.rule(ARGUMENT).is(b.optional(IDENTIFIER_NAME, EQU_GT), EXPRESSION);
		    b.rule(OBJECT_REFERENCE).is(b.firstOf(
		    		CALL_EXPRESSION, MEMBER_EXPRESSION 
		        ), b.zeroOrMore(DOT, b.firstOf(CALL_EXPRESSION, MEMBER_EXPRESSION))).skipIfOneChild();
		    b.rule(POSTFIX_EXPRESSION).is(OBJECT_REFERENCE, b.optional(/* no line terminator here */SPACING_NO_LB, NEXT_NOT_LB, b.firstOf(INC, DEC, b.sequence(IS, b.optional(NOT), NULL), b.sequence(IS, b.optional(NOT), OF, b.optional(TYPE), LPARENTHESIS, OBJECT_REFERENCE,  b.zeroOrMore(COMMA, OBJECT_REFERENCE), RPARENTHESIS))));

		    b.rule(IN_EXPRESSION).is(POSTFIX_EXPRESSION, b.optional(b.sequence(b.optional(NOT), IN , LPARENTHESIS, EXPRESSION, b.zeroOrMore(COMMA, BOOLEAN_EXPRESSION), RPARENTHESIS))).skipIfOneChild();

		    b.rule(UNARY_EXPRESSION).is(b.firstOf(
		        
		        b.sequence(DELETE, UNARY_EXPRESSION),
		        b.sequence(NOT, UNARY_EXPRESSION),
/*		        b.sequence(VOID, UNARY_EXPRESSION),
		        b.sequence(TYPEOF, UNARY_EXPRESSION),
*/		        b.sequence(INC, UNARY_EXPRESSION),
		        b.sequence(DEC, UNARY_EXPRESSION),
		        b.sequence(PLUS, UNARY_EXPRESSION),
		        b.sequence(MINUS, UNARY_EXPRESSION),
		        b.sequence(TILDA, UNARY_EXPRESSION),
//		        b.sequence(BANG, UNARY_EXPRESSION),
		        IN_EXPRESSION
		    		)).skipIfOneChild();
		
		 b.rule(MULTIPLICATIVE_EXPRESSION).is(UNARY_EXPRESSION, b.zeroOrMore(b.firstOf(STAR, DIV, MOD), UNARY_EXPRESSION)).skipIfOneChild();
		 b.rule(ADDITIVE_EXPRESSION).is(MULTIPLICATIVE_EXPRESSION, b.zeroOrMore(b.firstOf(PLUS, MINUS), MULTIPLICATIVE_EXPRESSION)).skipIfOneChild();
		 b.rule(CONCAT_EXPRESSION).is(ADDITIVE_EXPRESSION, b.zeroOrMore(OROR, ADDITIVE_EXPRESSION)).skipIfOneChild();

		
		 b.rule(BOOLEAN_COMP_EXPRESSION).is(CONCAT_EXPRESSION, b.zeroOrMore(b.firstOf(EQU, NOTEQUAL, NOTEQUAL3, LT, LE, GT,GE, LIKE), CONCAT_EXPRESSION)).skipIfOneChild();   
		 b.rule(BOOLEAN_EXPRESSION).is(BOOLEAN_COMP_EXPRESSION, b.zeroOrMore(b.firstOf(PlSQLKeyword.AND, PlSQLKeyword.OR), BOOLEAN_COMP_EXPRESSION)).skipIfOneChild();
		 // TODO: check
		 b.rule(ASSIGNMENT_EXPRESSION).is(b.firstOf(OBJECT_REFERENCE, 
			        b.sequence(OBJECT_REFERENCE, ASSIGNMENT_OPERATOR, ASSIGNMENT_EXPRESSION)
			   /*  word(b, "//TODO:   CONDITIONAL_EXPRESSION") */
			        
				 )).skipIfOneChild();
			    
		 b.rule(ASSIGNMENT_OPERATOR).is(b.firstOf(
			        EQU,
			        STAR_EQU,
			        DIV_EQU,
			        MOD_EQU,
			        PLUS_EQU,
			        MINUS_EQU,
			        SL_EQU,
			        SR_EQU,
			        SR_EQU2,
			        AND_EQU,
			        XOR_EQU,
			        OR_EQU)); 
		 
			b.rule(EXPRESSION).is(b.firstOf(SELECT_EXPRESSION, BOOLEAN_EXPRESSION)
				
				/*BOOLEAN_EXPRESSION, 
				DATE_EXPRESSION,
				NUMERIC_EXPRESSION,
				SIMPLE_CASE_EXPRESSION,
				SEARCHED_CASE_EXPRESSION*/
				).skipIfOneChild();
	}
		
	private static void tablesViewsAndSequence(LexerlessGrammarBuilder b) {
		b.rule(SEQUENCE_PROPERTY).is(b.firstOf(NOORDER, ORDER, NOCACHE, b.sequence(CACHE, PlSQLTokenType.NUMERIC_LITERAL), CYCLE, NOCYCLE, NOMINVALUE, b.sequence(MINVALUE, PlSQLTokenType.NUMERIC_LITERAL), NOMAXVALUE, b.sequence(MAXVALUE, PlSQLTokenType.NUMERIC_LITERAL), b.sequence(START, WITH, PlSQLTokenType.NUMERIC_LITERAL), b.sequence(wordCaseIndepend(b, "INCREMENT"), BY, PlSQLTokenType.NUMERIC_LITERAL)));
		b.rule(CREATE_SEQUENCE).is(CREATE, SEQUENCE, OBJECT_NAME, b.zeroOrMore(SEQUENCE_PROPERTY), EOS);
		
		b.rule(CREATE_VIEW).is(CREATE, b.optional(OR_REPLACE), b.optional(FORCE), VIEW, OBJECT_NAME,
				b.optional(LPARENTHESIS, IDENTIFIER_NAME, b.zeroOrMore(COMMA, IDENTIFIER_NAME), RPARENTHESIS),
				AS, SELECT_STATEMENT);
		
		b.rule(INLINE_CONSTRAINT).is(b.firstOf(
				b.sequence(b.optional(PlSQLKeyword.NOT), NULL),
				UNIQUE, b.sequence(PRIMARY, KEY)
				
				));
		
		b.rule(COLUMN_DEFINITION).is(IDENTIFIER, DATATYPE, b.optional(PlSQLKeyword.DEFAULT, EXPRESSION), b.optional(INLINE_CONSTRAINT));

		
		b.rule(TABLESPACE_PROPERTY).is(TABLESPACE, IDENTIFIER_NAME);
		b.rule(PARALLEL_PROPERTY).is(PARALLEL, LITERAL);
		b.rule(LIST_PARTITION).is(PARTITION, IDENTIFIER_NAME, VALUES, LPARENTHESIS, b.firstOf(DEFAULT, EXPRESSION), b.zeroOrMore(COMMA,b.firstOf(DEFAULT, LITERAL)),RPARENTHESIS);
		b.rule(PARTITION_PROPERTY).is(PARTITION, BY, wordCaseIndepend(b, "list"), LPARENTHESIS, IDENTIFIER_NAME, RPARENTHESIS, LPARENTHESIS, LIST_PARTITION, b.zeroOrMore(COMMA, LIST_PARTITION), RPARENTHESIS);
		b.rule(PHYSICAL_TABLE_PROPERTIES).is(b.firstOf(TABLESPACE_PROPERTY, PARALLEL_PROPERTY, PARTITION_PROPERTY));
		
		b.rule(CREATE_TABLE).is(CREATE, b.optional(b.optional(wordCaseIndepend(b, "GLOBAL")), TEMPORARY), TABLE, OBJECT_NAME,
				b.optional(LPARENTHESIS,COLUMN_DEFINITION, b.zeroOrMore(COMMA, COLUMN_DEFINITION), RPARENTHESIS), b.zeroOrMore(PHYSICAL_TABLE_PROPERTIES), EOS);
		b.rule(USING_INDEX).is(USING, INDEX);
		b.rule(TABLE_CONSTRAINT_CLAUSE).is(b.optional(CONSTRAINT, IDENTIFIER_NAME), PRIMARY, KEY, LPARENTHESIS, IDENTIFIER_NAME, b.zeroOrMore(COMMA, IDENTIFIER_NAME), RPARENTHESIS, b.optional(USING_INDEX), b.optional(PHYSICAL_TABLE_PROPERTIES));
		b.rule(TABLE_COMMENT).is(wordCaseIndepend(b, "comment"), ON, b.firstOf(TABLE, wordCaseIndepend(b, "column")), OBJECT_NAME, IS, STRING_LITERAL, EOS);
		b.rule(ALTER_TABLE).is(ALTER, TABLE, OBJECT_NAME, ADD,TABLE_CONSTRAINT_CLAUSE, EOS);
		
		b.rule(CREATE_INDEX).is(CREATE, b.optional(UNIQUE), INDEX, OBJECT_NAME, ON, OBJECT_NAME, LPARENTHESIS, EXPRESSION, b.optional(b.firstOf(ASC, DESC)), b.zeroOrMore(COMMA, EXPRESSION, b.optional(b.firstOf(ASC, DESC))), RPARENTHESIS, b.optional(PHYSICAL_TABLE_PROPERTIES), EOS);
	}
	/**
	 * A.5 Functions and Programs
	 */
	private static void functionsAndPrograms(LexerlessGrammarBuilder b) {
		b.rule(PARAMETER_DECLARATION).is(IDENTIFIER_NAME, b.optional(IN), b.optional(OUT), b.optional(wordCaseIndepend(b, "NOCOPY")), DATATYPE, b.optional(b.firstOf(DEFAULT, ASSIGNMENT), EXPRESSION));
		b.rule(WITH_PARAMETERS).is(LPARENTHESIS, b.optional(PARAMETER_DECLARATION, b.zeroOrMore(COMMA, PARAMETER_DECLARATION)),RPARENTHESIS);
		b.rule(PROCEDURE_DECLARATION).is(PROCEDURE, IDENTIFIER_NAME, b.optional(WITH_PARAMETERS), SEMI);
		
		b.rule(FUNCTION_DECLARATION).is(FUNCTION, IDENTIFIER_NAME, b.optional(WITH_PARAMETERS), RETURN, DATATYPE,SEMI);
		
		b.rule(PROCEDURE_BODY).is(PROCEDURE, IDENTIFIER_NAME, b.optional(WITH_PARAMETERS), b.firstOf(IS, AS), 
				BLOCK_STATEMENT);
		
		b.rule(FUNCTION_BODY).is(FUNCTION, IDENTIFIER_NAME, b.optional(WITH_PARAMETERS), RETURN, DATATYPE, b.firstOf(IS, AS), 
				BLOCK_STATEMENT);
		
		
		b.rule(OR_REPLACE).is(PlSQLKeyword.OR, REPLACE);
		
		b.rule(PACKAGE_DECLARATION).is(CREATE, b.optional(OR_REPLACE), PACKAGE,
				OBJECT_NAME, b.firstOf(AS, IS), b.zeroOrMore(b.firstOf(TYPE_DEFINITION, NORMAL_VARIABLE_DECLARATION, EXCEPTION_DEFINITION, FUNCTION_DECLARATION, PROCEDURE_DECLARATION)), 
				END, b.optional(IDENTIFIER_NAME), EOS);
		
		b.rule(PACKAGE_BODY_DECLARATION).is(CREATE, b.optional(OR_REPLACE),
				PACKAGE, BODY, OBJECT_NAME, b.firstOf(AS, IS),
				b.zeroOrMore(NORMAL_VARIABLE_DECLARATION), b.zeroOrMore(b.firstOf(FUNCTION_BODY, PROCEDURE_BODY)), 
				//BEGIN, b.zeroOrMore(STATEMENT), b.optional(EXCEPTION, b.oneOrMore(EXCEPTION_HANDLER)),
				END, b.optional(IDENTIFIER_NAME), EOS);

		b.rule(CREATE_SYNONYM).is(CREATE, b.optional(OR_REPLACE), b.optional(PUBLIC),  wordCaseIndepend(b, "synonym"), IDENTIFIER_NAME, FOR, OBJECT_REFERENCE, EOS);
		
		b.rule(PROGRAM).is(SOURCE_ELEMENT, b.zeroOrMore(SOURCE_ELEMENT), SPACING, b.optional(EOS), EOF);
		//b.rule(SOURCE_ELEMENTS).is(b.oneOrMore(SOURCE_ELEMENT));
		b.rule(SOURCE_ELEMENT).is(
				b.firstOf(PACKAGE_DECLARATION, CREATE_SYNONYM, GRANT_STATEMENT,  
						PACKAGE_BODY_DECLARATION, CREATE_TABLE, ALTER_TABLE, TABLE_COMMENT,  CREATE_INDEX, CREATE_VIEW, CREATE_SEQUENCE));

	}

	private static Object word(LexerlessGrammarBuilder b, String value) {
		return b.sequence(SPACING, b.token(GenericTokenType.IDENTIFIER, value));
	}
	
	private static Object wordCaseIndepend(LexerlessGrammarBuilder b, String value) {
		return b.sequence(SPACING, makeCaseInsensitiveRule(b, value));
	}

	public static LexerlessGrammar createGrammar() {
		return createGrammarBuilder().build();
	}

	public static LexerlessGrammarBuilder createGrammarBuilder() {
		LexerlessGrammarBuilder b = LexerlessGrammarBuilder.create();
		b.rule(LABEL).is(SL, IDENTIFIER_NAME, SR);
		b.rule(IDENTIFIER_NAME).is(
		        SPACING,
		        b.regexp(PlSQLLexer.IDENTIFIER));
		
		
		
		b.rule(DEBUG_FLAG).is(
		        SPACING,
		        b.regexp("\\$\\$"+PlSQLLexer.IDENTIFIER));
	
		
		 b.rule(LITERAL).is(b.firstOf(
			        NULL_LITERAL,
			        BOOLEAN_LITERAL,
			        PlSQLTokenType.NUMERIC_LITERAL,
			        STRING_LITERAL
			        //REGULAR_EXPRESSION_LITERAL
			        ));
			    b.rule(NULL_LITERAL).is(NULL);
			    b.rule(BOOLEAN_LITERAL).is(b.firstOf(
			        TRUE,
			        FALSE));
		
		lexical(b);
		datatypes(b);
		expressions(b);
		statements(b);
		tablesViewsAndSequence(b);
		functionsAndPrograms(b);

		b.setRootRule(PROGRAM);
		return b;
	}

	private final String internalName;

	private PlSQLGrammar() {
		String name = name();
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (i < name.length()) {
			if (name.charAt(i) == '_' && i + 1 < name.length()) {
				i++;
				sb.append(name.charAt(i));
			} else {
				sb.append(Character.toLowerCase(name.charAt(i)));
			}
			i++;
		}
		this.internalName = sb.toString();
	}

	@Override
	public String toString() {
		// This allows to keep compatibility with old XPath expressions
		return internalName;
	}
}
