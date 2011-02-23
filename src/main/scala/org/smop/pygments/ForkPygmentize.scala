package org.smop.pygments

import io.Codec
import util.parsing.combinator._
import java.io.{BufferedReader, Reader, InputStreamReader}

class ForkPygmentize extends PygmentizeParsers with Pygments {
  val style = "tango"
  def highlight(code: String, lexer: Lexer, formatter: Formatter): String = {
    val (res: BufferedReader, proc: Process) = runCommand("pygmentize", "-l", lexer.name, "-f", formatter.name)
    val os = proc.getOutputStream
    // weird that this doesn't block when code is large.
    os.write(code.getBytes(Codec.UTF8))
    os.close
    val sb = new StringBuilder
    var s: String = res.readLine
    while(s != null) {
      sb.append(s)
      s = res.readLine
    }
    proc.waitFor()
    sb.toString
  }
  def allLexers: Iterable[LexerDefinition] = {
    val (source, proc) = runCommand("pygmentize", "-L", "lexers")
    parseAll(allLexersParser, source) match {
      case Success(res, in) => {proc.waitFor(); res}
      case Failure(msg, in) => {proc.destroy(); throw new PygmentsException(msg)}
      case Error(msg, in) => {proc.destroy(); throw new PygmentsException(msg)}
    }
  }
  def runCommand(args: String*): Product2[BufferedReader, Process] = {
    val pb = new ProcessBuilder(args: _*)
    val proc = pb.start()
    (new BufferedReader(new InputStreamReader(proc.getInputStream, Codec.UTF8)), proc)
  }
}

class PygmentizeParsers extends JavaTokenParsers {
  override val whiteSpace = "[ \t]+".r
  val nl = "\r?\n".r
  def allLexersParser: Parser[List[LexerDefinition]] = ("Pygments version .*".r~nl~nl~"Lexers:"~nl~"~~~~~~~"~nl)~>rep(lexerDef)
  def lexerDef: Parser[LexerDefinition] = "*"~>repsep(alias, ",")~(":"~nl~>name)~(filenames<~nl) ^^ {
    case as~n~fns => LexerDefinition(n, as, fns)
  }
  def alias: Parser[String] = """\w[-+.#\w]*""".r
  def name: Parser[String] = """[^(\r\n]+(?<! )""".r
  def filename: Parser[String] = """[^)\s,]+""".r
  def filenames: Parser[List[String]] = opt("(filenames "~>repsep(filename, ", ")<~")") ^^ {
    case Some(l) => l
    case None => List()
  }
}
