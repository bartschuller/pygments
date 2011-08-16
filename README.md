# pygments

Scala library for producing syntax-highlighted text, using the Pygments tool

## Usage

Add the dependency to your project, for [sbt](http://code.google.com/p/simple-build-tool/):

```scala
val lunatechPublic = "Lunatech Public" at "http://nexus.lunatech.com/content/repositories/lunatech-public/"
val pygments = "org.smop" %% "pygments" % "0.1.4"
```

Or for [Play! 1.2](http://www.playframework.org/):

```yaml
# Application dependencies

require:
    - play
    - play -> scala 0.9
    - org.smop -> pygments_2.8.1 0.1.4
repositories:
    - lunatechPublic:
        type: iBiblio
        root: "http://nexus.lunatech.com/content/repositories/lunatech-public/"
        contains:
            - org.smop -> *
```

Install [pygments](http://pygments.org/) (the Python library/tool) and make sure the `pygmentize` command is on your PATH.

```scala
import org.smop.pygments._

// There is just one implementation currently and it forks the
// commandline tool.
val p: Pygments = new ForkPygmentize

// produces a data structure with information about the languages that
// it recognizes, useful for populating a dropdown menu
val lexers = p.allLexers

// Actually produce a String with html.
val html = p.highlight("val i:Integer = 3", Lexer("scala"), Formatter("html"))
```

It's very easy to experiment on the console. `Formatter("console")` comes in handy there.

## Contact

The project is currently hosted at [GitHub](https://github.com/bartschuller/pygments) and the usual github tools can be used to communicate with the author. Other means of contact include Twitter [@BartSchuller](http://twitter.com/BartSchuller) and email [pygments@smop.org](mailto:pygments@smop.org).

## License

The *org.smop pygments* library is published under the [Simplified BSD License](http://www.opensource.org/licenses/bsd-license):

Copyright © 2011, Bart Schuller  
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

  * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
  * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
