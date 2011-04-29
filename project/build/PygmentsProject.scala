import sbt._

class PygmentsProject(info: ProjectInfo) extends DefaultProject(info) {
  // val jbossReleases = "JBoss Releases" at "http://repository.jboss.org/maven2/"
  val specs = "org.scala-tools.testing" %% "specs" % "1.6.7.2" % "test->default"

  override def compileOptions = super.compileOptions ++ Seq(Unchecked)

  override def managedStyle = ManagedStyle.Maven
  val publishTo = "Lunatech Public" at "http://nexus.lunatech.com/content/repositories/lunatech-public/"
  Credentials(Path.userHome / ".ivy2" / ".credentials", log)
}
