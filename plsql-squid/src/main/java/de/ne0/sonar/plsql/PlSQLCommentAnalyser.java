package de.ne0.sonar.plsql;

import com.sonar.sslr.api.CommentAnalyser;

public class PlSQLCommentAnalyser extends CommentAnalyser {

	@Override
	public boolean isBlank(String line) {
		for (int i = 0; i < line.length(); i++) {
			if (Character.isLetterOrDigit(line.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String getContents(String comment) {
		if (comment.startsWith("--")) {
			return comment.substring(2);
		} else if (comment.startsWith("/*")) {
			if (comment.endsWith("*/")) {
				return comment.substring(2, comment.length() - 2);
			}
			return comment.substring(2);
		} else {
			throw new IllegalArgumentException();
		}
	}

}
