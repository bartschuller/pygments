package org.smop.pygments

import org.scala_tools.subcut.inject.{NewBindingModule, Injectable, BindingModule}

case class Lexer(name: String, options: Map[String, String] = Map())

case class LexerDefinition(name: String, aliases: Seq[String], filenames: Seq[String])

case class Formatter(name: String, options: Map[String, String] = Map())

case class FormatterDefinition(name: String, aliases: Seq[String], filenames: Seq[String])

trait Pygments {
  def highlight(code: String, lexer: Lexer, formatter: Formatter): String

  def allLexers: Iterable[LexerDefinition]

  def findLexer(fileName: String): Option[String] = {
    allLexers.find(_ match {
      case LexerDefinition(_, _, patterns) => patterns.exists(globMatches(_, fileName))
    }).map(_.name)
  }

  def globMatches(glob: String, fileName: String): Boolean = {
    if (glob.contains("*")) {
      val re = glob.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*")
      fileName.matches(re)
    } else
      glob == fileName
  }
}

object Pygments {
  def apply()(implicit bindingModule: BindingModule): Pygments = (new PygmentsFactory).implementation
}

class PygmentsException(msg: String, cause: Throwable = null) extends RuntimeException(msg, cause)

object Implicits {
  implicit val defaultConfigurationModule = new NewBindingModule({ module => })
}

class PygmentsFactory(implicit val bindingModule: BindingModule) extends Injectable {
  val implementation = injectIfBound[Pygments] { new ForkPygmentize }
}
