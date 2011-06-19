name := "pygments"

version := "0.1.2"

organization := "org.smop"

scalaVersion := "2.9.0-1"

libraryDependencies += "org.scala-tools.testing" %% "specs" % "1.6.8" % "test"

publishTo := Some("Lunatech Public" at "http://nexus.lunatech.com/content/repositories/lunatech-public/")

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
