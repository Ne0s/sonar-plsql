package de.ne0.sonar.plsql.plugin;

import org.sonar.api.profiles.AnnotationProfileParser;
import org.sonar.api.profiles.ProfileDefinition;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.utils.ValidationMessages;

import de.ne0.sonar.plsql.checks.CheckList;
import de.ne0.sonar.plsql.plugin.core.PlSQL;

public class PlSQLProfile  extends ProfileDefinition {

	  private final AnnotationProfileParser annotationProfileParser;

	  public PlSQLProfile(AnnotationProfileParser annotationProfileParser) {
	    this.annotationProfileParser = annotationProfileParser;
	  }

	  @Override
	  public RulesProfile createProfile(ValidationMessages validation) {
	    return annotationProfileParser.parse(CheckList.REPOSITORY_KEY, CheckList.SONAR_WAY_PROFILE, PlSQL.KEY, CheckList.getChecks(), validation);
	  }

	}
