package org.smop.pygments

case class Lexer(name: String, options: Map[String, String] = Map())
case class LexerDefinition(name: String, aliases: Seq[String], filenames: Seq[String])
case class Formatter(name: String, options: Map[String, String] = Map())
case class FormatterDefinition(name: String, aliases: Seq[String], filenames: Seq[String])

trait Pygments {
  def highlight(code: String, lexer: Lexer, formatter: Formatter): String
  def allLexers: Iterable[LexerDefinition]
}

class PygmentsException(msg: String, cause: Throwable = null) extends RuntimeException(msg, cause)
