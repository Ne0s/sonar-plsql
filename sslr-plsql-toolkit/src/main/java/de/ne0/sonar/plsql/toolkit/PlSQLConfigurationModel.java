package de.ne0.sonar.plsql.toolkit;

import java.nio.charset.Charset;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.colorizer.CDocTokenizer;
import org.sonar.colorizer.CppDocTokenizer;
import org.sonar.colorizer.JavadocTokenizer;
import org.sonar.colorizer.KeywordsTokenizer;
import org.sonar.colorizer.StringTokenizer;
import org.sonar.colorizer.Tokenizer;
import org.sonar.sslr.toolkit.AbstractConfigurationModel;
import org.sonar.sslr.toolkit.ConfigurationProperty;
import org.sonar.sslr.toolkit.Validators;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.impl.Parser;

import de.ne0.sonar.plsql.PlSQLConfiguration;
import de.ne0.sonar.plsql.api.PlSQLKeyword;
import de.ne0.sonar.plsql.parser.PlSQLParser;

public class PlSQLConfigurationModel  extends AbstractConfigurationModel {

	  private static final Logger LOG = LoggerFactory.getLogger(PlSQLConfigurationModel.class);

	  private static final String CHARSET_PROPERTY_KEY = "sonar.sourceEncoding";

	  @VisibleForTesting
	  ConfigurationProperty charsetProperty = new ConfigurationProperty("Charset", CHARSET_PROPERTY_KEY,
	      getPropertyOrDefaultValue(CHARSET_PROPERTY_KEY, "UTF-8"),
	      Validators.charsetValidator());

	  public List<ConfigurationProperty> getProperties() {
	    return ImmutableList.of(charsetProperty);
	  }

	  @Override
	  public Charset getCharset() {
	    return Charset.forName(charsetProperty.getValue());
	  }

	  @Override
	  public Parser<? extends Grammar> doGetParser() {
	    return PlSQLParser.create(getConfiguration());
	  }

	  @Override
	  public List<Tokenizer> doGetTokenizers() {
	    return ImmutableList.of(
	        new StringTokenizer("<span class=\"s\">", "</span>"),
	        new CDocTokenizer("<span class=\"cd\">", "</span>"),
	        new JavadocTokenizer("<span class=\"cppd\">", "</span>"),
	        new CppDocTokenizer("<span class=\"cppd\">", "</span>"),
	        new KeywordsTokenizer("<span class=\"k\">", "</span>", PlSQLKeyword.keywordValues()));
	  }

	  @VisibleForTesting
	  PlSQLConfiguration getConfiguration() {
	    return new PlSQLConfiguration(getCharset());
	  }

	  @VisibleForTesting
	  static String getPropertyOrDefaultValue(String propertyKey, String defaultValue) {
	    String propertyValue = System.getProperty(propertyKey);

	    if (propertyValue == null) {
	      LOG.info("The property \"" + propertyKey + "\" is not set, using the default value \"" + defaultValue + "\".");
	      return defaultValue;
	    } else {
	      LOG.info("The property \"" + propertyKey + "\" is set, using its value \"" + propertyValue + "\".");
	      return propertyValue;
	    }
	  }

	}