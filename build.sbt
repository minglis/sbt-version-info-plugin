import java.util.jar._

sbtPlugin := true

version in ThisBuild := "1.2.1"

organization in ThisBuild := "gov.gds"

name := "sbt-version-info-plugin"

publishArtifact := true

packageOptions in ThisBuild <+= (version, name) map { (v, n) =>
  Package.ManifestAttributes(
    Attributes.Name.IMPLEMENTATION_VERSION -> v,
    Attributes.Name.IMPLEMENTATION_TITLE -> n,
    Attributes.Name.IMPLEMENTATION_VENDOR -> "alphagov.co.uk"
  )
}

publishTo in ThisBuild <<= (version) { version: String =>
    val publishType = if (version.endsWith("SNAPSHOT")) "snapshots" else "releases"
    Some(
        Resolver.file(
            "alphagov github " + publishType,
            file(System.getProperty("user.home") + "/alphagov.github.com/maven/" + publishType)
        )
    )
}


scalacOptions in ThisBuild += "-deprecation"



