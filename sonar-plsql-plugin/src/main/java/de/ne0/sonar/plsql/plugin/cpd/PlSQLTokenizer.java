package de.ne0.sonar.plsql.plugin.cpd;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import net.sourceforge.pmd.cpd.SourceCode;
import net.sourceforge.pmd.cpd.TokenEntry;
import net.sourceforge.pmd.cpd.Tokenizer;
import net.sourceforge.pmd.cpd.Tokens;

import com.sonar.sslr.api.GenericTokenType;
import com.sonar.sslr.api.Token;
import com.sonar.sslr.impl.Lexer;

import de.ne0.sonar.plsql.PlSQLConfiguration;
import de.ne0.sonar.plsql.lexer.PlSQLLexer;

public class PlSQLTokenizer implements Tokenizer {
	private final Charset charset;

	public PlSQLTokenizer(Charset charset) {
		this.charset = charset;
	}

	public final void tokenize(SourceCode source, Tokens cpdTokens) {
		Lexer lexer = PlSQLLexer.create(new PlSQLConfiguration(charset));
		String fileName = source.getFileName();
		List<Token> tokens = lexer.lex(new File(fileName));
		for (Token token : tokens) {
			TokenEntry cpdToken = new TokenEntry(getTokenImage(token),
					fileName, token.getLine());
			cpdTokens.add(cpdToken);
		}
		cpdTokens.add(TokenEntry.getEOF());
	}

	private String getTokenImage(Token token) {
		if (token.getType() == GenericTokenType.LITERAL) {
			return GenericTokenType.LITERAL.getValue();
		}
		return token.getValue();
	}
}
