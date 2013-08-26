/*
 * Sonar JavaScript Plugin
 * Copyright (C) 2011 SonarSource and Eriks Nukis
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package de.ne0.sonar.plsql.parser.lexical;

import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Test;
import org.sonar.sslr.parser.LexerlessGrammar;

import de.ne0.sonar.plsql.parser.PlSQLGrammar;

public class SpacingTest {

  LexerlessGrammar g = PlSQLGrammar.createGrammar();

 
  
  @Test
  public void ok() {
    assertThat(g.rule(PlSQLGrammar.SPACING))
        // must allow empty matches, otherwise "optional(SPACING)" will be used everywhere in grammar,
        // which leads to dramatic degradation of performance
        .matches("")

        .as("Whitespace")
        .matches(" ")
        .matches("\n")
        .matches("\r")
        .matches("\r\n")

        .as("SingleLineComment")
        .matches("// comment")
        .matches("// comment \n")

        .as("MultiLineComment")
        .matches("/* comment */")
        .matches("/* comment \n */");
  }

}
