val javaVersion = "1.8"

import sbtrelease.ReleasePlugin.autoImport._

inThisBuild(
  Seq(
    organization := "software.purpledragon.xml",
    scalaVersion := "2.13.1",
    crossScalaVersions := Seq(scalaVersion.value, "2.11.12", "2.12.10"),
    licenses += ("Apache-2.0", url("https://opensource.org/licenses/Apache-2.0")),
    developers := List(
      Developer("stringbean", "Michael Stringer", "@the_stringbean", url("https://github.com/stringbean"))
    ),
    organizationName := "Michael Stringer",
    organizationHomepage := Some(url("https://purpledragon.software")),
    homepage := Some(url("https://stringbean.github.io/scala-xml-compare")),
    startYear := Some(2017),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/stringbean/scala-xml-compare"),
        "scm:git:git@github.com:stringbean/scala-xml-compare.git")),
    autoAPIMappings := true,
    javacOptions ++= Seq("-source", javaVersion, "-target", javaVersion, "-Xlint"),
    scalacOptions ++= Seq(s"-target:jvm-$javaVersion", "-deprecation", "-feature", "-unchecked"),
    // dependencies common for all sub-projects
    libraryDependencies ++= Seq(
      "org.scala-lang.modules" %% "scala-xml" % "1.2.0"
    ),
  ))

lazy val commonSettings = Seq(
  releasePublishArtifactsAction := PgpKeys.publishLocalSigned.value,
  previewSite := {},
  previewAuto := {},
)

lazy val xmlCompare = Project("xml-compare", file("xml-compare"))
  .settings(commonSettings)

lazy val xmlScalatest = Project("xml-scalatest", file("xml-scalatest"))
  .dependsOn(xmlCompare)
  .settings(commonSettings)

lazy val xmlSpecs2 = Project("xml-specs2", file("xml-specs2"))
  .dependsOn(xmlCompare)
  .settings(commonSettings)

import ReleaseTransformations._
lazy val root = Project("scala-xml-compare", file("."))
  .aggregate(
    xmlCompare,
    xmlScalatest,
    xmlSpecs2
  )
  .settings(
    publishArtifact := false,
    // ghpages
    git.remoteRepo := scmInfo.value.get.connection.replace("scm:git:", ""),
    ghpagesNoJekyll := true,
    // site/paradox
    siteSubdirName in ScalaUnidoc := "api",
    addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), siteSubdirName in ScalaUnidoc),
    paradoxProperties in Compile ++= Map(
      "scaladoc.base_url" -> ".../api"
    ),
    paradoxNavigationDepth := 3,
    scalacOptions in Compile in doc ++= Seq(
      "-doc-root-content",
      baseDirectory.value + "/root-scaladoc.txt"
    ),
    // sbt-release settings
    releaseCrossBuild := true,
    releaseVcsSign := true,
    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runClean,
      runTest,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      publishArtifacts,
      releaseStepTask(ghpagesPushSite),
      setNextVersion,
      commitNextVersion,
      pushChanges
    )
  )
  .enablePlugins(ScalaUnidocPlugin, GhpagesPlugin, ParadoxSitePlugin)
