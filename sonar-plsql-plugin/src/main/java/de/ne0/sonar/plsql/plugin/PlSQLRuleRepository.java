package de.ne0.sonar.plsql.plugin;

import java.util.List;

import org.sonar.api.rules.AnnotationRuleParser;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleRepository;

import de.ne0.sonar.plsql.checks.CheckList;
import de.ne0.sonar.plsql.plugin.core.PlSQL;

public class PlSQLRuleRepository  extends RuleRepository {

	  private final AnnotationRuleParser annotationRuleParser;

	  public PlSQLRuleRepository(AnnotationRuleParser annotationRuleParser) {
	    super(CheckList.REPOSITORY_KEY, PlSQL.KEY);
	    setName(CheckList.REPOSITORY_NAME);
	    this.annotationRuleParser = annotationRuleParser;
	  }

	  @Override
	  public List<Rule> createRules() {
	    return annotationRuleParser.parse(CheckList.REPOSITORY_KEY, CheckList.getChecks());
	  }

	}
