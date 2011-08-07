package org.smop.pygments

import io.Codec
import util.parsing.combinator._
import java.io.{BufferedReader, Reader, InputStreamReader}

class ForkPygmentize extends PygmentizeParsers with Pygments {
  val style = "tango"
  def highlight(code: String, lexer: Lexer, formatter: Formatter): String = {
    val options: Seq[String] = Seq("pygmentize", "-l", lexer.name, "-f", formatter.name)
    val args = options ++ formatter.options.flatMap(t => Seq("-P", t._1 + "=" + t._2))
    val (res: BufferedReader, proc: Process) = runCommand(args:_*)
    val os = proc.getOutputStream
    // weird that this doesn't block when code is large.
    os.write(code.getBytes(Codec.UTF8))
    os.close
    val sb = new StringBuilder
    var s: String = res.readLine
    while(s != null) {
      sb.append(s)
      sb.append("\n")
      s = res.readLine
    }
    proc.waitFor()
    sb.toString
  }
  lazy val allLexers: Iterable[LexerDefinition] = {
    val (source, proc) = runCommand("pygmentize", "-L", "lexers")
    parseAll(allLexersParser, source) match {
      case Success(res, in) => {proc.waitFor(); res}
      case Failure(msg, in) => {proc.destroy(); throw new PygmentsException("Parse failure: "+msg+" at "+in.pos)}
      case Error(msg, in) => {proc.destroy(); throw new PygmentsException("Parse error: "+msg+" at "+in.pos)}
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
  def alias: Parser[String] = """\w[^,:]*""".r
  def name: Parser[String] = """[^(\r\n]+(?<! )""".r
  def filename: Parser[String] = """[^)\s,]+""".r
  def filenames: Parser[List[String]] = opt("(filenames "~>repsep(filename, ", ")<~")") ^^ {
    case Some(l) => l
    case None => List()
  }
}
