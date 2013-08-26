package de.ne0.sonar.plsql;

import java.nio.charset.Charset;

import org.sonar.squid.api.SquidConfiguration;

public class PlSQLConfiguration extends SquidConfiguration {

	  private boolean ignoreHeaderComments;

	  public PlSQLConfiguration(Charset charset) {
	    super(charset);
	  }

	  public void setIgnoreHeaderComments(boolean ignoreHeaderComments) {
	    this.ignoreHeaderComments = ignoreHeaderComments;
	  }

	  public boolean getIgnoreHeaderComments() {
	    return ignoreHeaderComments;
	  }


}
