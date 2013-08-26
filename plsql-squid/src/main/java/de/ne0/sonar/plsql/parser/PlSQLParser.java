package de.ne0.sonar.plsql.parser;

import org.sonar.sslr.parser.LexerlessGrammar;
import org.sonar.sslr.parser.ParserAdapter;

import com.sonar.sslr.impl.Parser;
import com.sonar.sslr.impl.events.ParsingEventListener;

import de.ne0.sonar.plsql.PlSQLConfiguration;

public class PlSQLParser {

	private PlSQLParser() {
		
	}
	
	 public static Parser<LexerlessGrammar> create(PlSQLConfiguration conf, ParsingEventListener... parsingEventListeners) {
		    return new ParserAdapter<LexerlessGrammar>(conf.getCharset(), PlSQLGrammar.createGrammar());
		  }
}
