import sbt._

class PygmentsProject(info: ProjectInfo) extends DefaultProject(info) {
  // val jbossReleases = "JBoss Releases" at "http://repository.jboss.org/maven2/"
  val specs = "org.scala-tools.testing" %% "specs" % "1.6.7.2" % "test->default"

  override def compileOptions = super.compileOptions ++ Seq(Unchecked)
}
