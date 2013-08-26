package de.ne0.sonar.plsql.toolkit;

import org.sonar.sslr.toolkit.Toolkit;

public class PlSQLToolkit {
	 private PlSQLToolkit() {
	  }

	  public static void main(String[] args) {
	    Toolkit toolkit = new Toolkit("SSLR :: PlSQL :: Toolkit", new PlSQLConfigurationModel());
	    toolkit.run();
	  }

}
