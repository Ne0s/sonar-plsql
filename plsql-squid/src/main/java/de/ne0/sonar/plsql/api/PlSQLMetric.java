package de.ne0.sonar.plsql.api;

import org.sonar.squid.measures.CalculatedMetricFormula;
import org.sonar.squid.measures.MetricDef;

public enum PlSQLMetric implements MetricDef {

	FILES, LINES, LINES_OF_CODE, COMMENT_LINES, COMPLEXITY, STATEMENTS, FUNCTIONS;

	public String getName() {
		return name();
	}

	public boolean isCalculatedMetric() {
		return false;
	}

	public boolean aggregateIfThereIsAlreadyAValue() {
		return true;
	}

	public boolean isThereAggregationFormula() {
		return true;
	}

	public CalculatedMetricFormula getCalculatedMetricFormula() {
		return null;
	}
}
