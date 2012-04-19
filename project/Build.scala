import sbt._

object VersionInfoBuild extends Build {
  lazy val root = Project("VersionInfo", file("."))
}