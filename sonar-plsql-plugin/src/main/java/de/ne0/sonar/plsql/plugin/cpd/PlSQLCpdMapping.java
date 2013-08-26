package de.ne0.sonar.plsql.plugin.cpd;

import java.nio.charset.Charset;

import net.sourceforge.pmd.cpd.Tokenizer;

import org.sonar.api.batch.AbstractCpdMapping;
import org.sonar.api.resources.Language;
import org.sonar.api.resources.ProjectFileSystem;

import de.ne0.sonar.plsql.plugin.core.PlSQL;

public class PlSQLCpdMapping  extends AbstractCpdMapping {
	 private final PlSQL language;
	  private final Charset charset;

	  public PlSQLCpdMapping(PlSQL language, ProjectFileSystem fs) {
	    this.language = language;
	    this.charset = fs.getSourceCharset();
	  }

	  public Tokenizer getTokenizer() {
	    return new PlSQLTokenizer(charset);
	  }

	  public Language getLanguage() {
	    return language;
	  }
}
