package org.smop.pygments.tests

import org.specs._
import log.ConsoleLog
import org.smop.pygments._


object PygmentsSpecification extends Specification with ConsoleLog {
  "Pygments" should {
    val p = new ForkPygmentize
    "list all lexers" in {
      p.allLexers must contain(LexerDefinition("Tcsh", List("tcsh", "csh"), List("*.tcsh", "*.csh")))
    }
    "highlight stuff" in {
      val hl = p.highlight("<html>", Lexer("html"), Formatter("html"))
      hl mustMatch "<div"
    }
  }
}
