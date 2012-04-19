package uk.gov.gds.versioninfo

import java.net.InetAddress
import java.util.Date
import sbt._
import Keys._
import java.lang.System

object VersionInfo extends Plugin {

  val branch = SettingKey[String]("version-branch")
  val buildNumber = SettingKey[String]("version-build-number")
  val vcsNumber = SettingKey[String]("version-vcs-number")


  override val settings = Seq(
    branch := System.getProperty("GIT_BRANCH", "DEV"),
    buildNumber := System.getProperty("BUILD_NUMBER", "DEV"),
    vcsNumber := System.getProperty("GIT_COMMIT", "DEV"),
    sourceGenerators in Compile <+= buildFile
  )

  private def buildFile = (baseDirectory, streams, sourceManaged, branch, buildNumber, vcsNumber).map { (base, s, sourceDir, branchName, buildNum, vcsNum) => {

      val template = """
      {
          Build="%s",
          Branch="%s",
          Built-By="%s",
          Built-On="%s",
          Date="%s",
          Revision="%s"
      }
      """ format (
            buildNum, 
            branchName,
            System.getProperty("user.name", "<unknown>") , 
            InetAddress.getLocalHost.getHostName,
            new Date().toString,
            vcsNum
        )

      val conf = base  / "conf" / "version.conf"
      s.log.debug("Writing to " + confFile + "  \n " + template)

      if(IO.exists(base / "conf"))
        IO.write(confFile, template)

      Seq(confFile)
    }

  }
}