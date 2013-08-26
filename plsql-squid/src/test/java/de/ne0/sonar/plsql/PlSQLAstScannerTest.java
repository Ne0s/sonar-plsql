package de.ne0.sonar.plsql;

import static org.fest.assertions.Assertions.assertThat;

import java.io.File;

import org.junit.Test;
import org.sonar.squid.api.SourceProject;
import org.sonar.squid.indexer.QueryByType;
import org.sonar.sslr.parser.LexerlessGrammar;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.sonar.sslr.squid.AstScanner;

import de.ne0.sonar.plsql.api.PlSQLMetric;

public class PlSQLAstScannerTest {

	 @Test
	  public void files() {
	    AstScanner<LexerlessGrammar> scanner = PlSQLAstScanner.create(new PlSQLConfiguration(Charsets.UTF_8));
	    scanner.scanFiles(ImmutableList.of(new File("src/test/resources/metrics/capp_evli_bcos_pkg.pkb"), new File("src/test/resources/metrics/capp_cfg_comms_t.sql"), new File("src/test/resources/metrics/capp_matching_pkg.pkb")));
	    SourceProject project = (SourceProject) scanner.getIndex().search(new QueryByType(SourceProject.class)).iterator().next();
	    assertThat(project.getInt(PlSQLMetric.FILES)).isEqualTo(3);
	  }
}
