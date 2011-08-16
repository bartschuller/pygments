package org.smop.pygments.tests

import org.specs._
import log.ConsoleLog
import org.smop.pygments._
import org.smop.pygments.Implicits._

object PygmentsSpecification extends Specification with ConsoleLog {
  "Pygments" should {
    val p = Pygments()
    "list all lexers" in {
      p.allLexers must contain(LexerDefinition("Tcsh", List("tcsh", "csh"), List("*.tcsh", "*.csh")))
    }
    "match on filename" in {
      p.findLexer("Makefile") must beSome("Makefile")
      p.findLexer("my file.scala") must beSome("Scala")
      p.findLexer("Makefile.in") must beSome("Makefile")
      p.findLexer("garble.hdughiuer") must beNone
    }
    "highlight stuff" in {
      val hl = p.highlight("<html>", Lexer("html"), Formatter("html"))
      hl mustMatch "<div"
      val big = <div>
        {1 to 100 map (k => <p>Hello there
          {k}
          .</p> <ul>
          {1 to 10 map (i => <li>
            {1000 * k + i}
          </li>)}
        </ul>)}
      </div>.toString
      p.highlight(big, Lexer("html"), Formatter("html")) mustMatch "<div"
    }
    "handle UTF-8 properly" in {
      val hl = p.highlight("Ëñçødîng", Lexer("text"), Formatter("html"))
      hl must include("Ëñçødîng")
    }
  }
}
