name := "pygments"

version := "0.1.5"

organization := "org.smop"

scalaVersion := "2.9.0-1"

crossScalaVersions := Seq("2.8.1", "2.9.0-1")

libraryDependencies ++= Seq(
  "org.scala-tools.testing" %% "specs" % "1.6.8" % "test"
)

libraryDependencies <<= (scalaVersion, libraryDependencies) { (sv, deps) =>
    // select the subcut version based on the Scala version
    val versionMap = Map("2.8.1" -> "2.8.1", "2.9.0-1" -> "2.9.0")
    val subcut = "subcut_"+ versionMap.getOrElse(sv, error("Unsupported Scala version " + sv))
    deps :+ ("org.scala-tools" % subcut % "0.8")
}

publishTo := Some("Lunatech Public" at "http://nexus.lunatech.com/content/repositories/lunatech-public/")

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
