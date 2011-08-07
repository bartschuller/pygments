name := "pygments"

version := "0.1.3"

organization := "org.smop"

scalaVersion := "2.9.0-1"

libraryDependencies ++= Seq(
  "org.scala-tools.testing" %% "specs" % "1.6.8" % "test",
  "org.scala-tools" % "subcut_2.9.0" % "0.8"
)


publishTo := Some("Lunatech Public" at "http://nexus.lunatech.com/content/repositories/lunatech-public/")

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
