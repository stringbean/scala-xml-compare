import PgpKeys.{publishLocalSigned, publishSigned}
import com.typesafe.sbt.SbtGit.GitKeys._

organization := "software.purpledragon.xml"
version := "0.0.4-SNAPSHOT"

scalaVersion := "2.12.7"
crossScalaVersions := Seq(scalaVersion.value, "2.11.12", "2.13.0-M5")

// dependencies common for all sub-projects
libraryDependencies ++= Seq(
  "org.scala-lang.modules"  %% "scala-xml"  % "1.1.1"
)

lazy val xmlCompare = Project("xml-compare", file("xml-compare"))
  .settings(
    previewSite := {},
    previewAuto := {}
  )

lazy val xmlScalatest = Project("xml-scalatest", file("xml-scalatest"))
  .dependsOn(xmlCompare)
  .settings(
    previewSite := {},
    previewAuto := {}
  )

lazy val xmlSpecs2 = Project("xml-specs2", file("xml-specs2"))
  .dependsOn(xmlCompare)
  .settings(
    previewSite := {},
    previewAuto := {}
  )

lazy val root = project
  .in(file("."))
  .aggregate(
    xmlCompare,
    xmlScalatest,
    xmlSpecs2
  )
  .settings(
    publish := {},
    publishSigned := {},
    publishLocal := {},
    publishLocalSigned := {},
    test := {},
    testOnly := {},
    bintrayUnpublish := {},
    bintraySyncMavenCentral := {},
    siteSubdirName in ScalaUnidoc := "api",
    addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), siteSubdirName in ScalaUnidoc),
    gitRemoteRepo := "git@github.com:stringbean/scala-xml-compare.git",
    ghpagesNoJekyll := true,
    paradoxTheme := Some(builtinParadoxTheme("generic")),
    sourceDirectory in Paradox := baseDirectory.value / "docs",
    sourceDirectory in Paradox in paradoxTheme := baseDirectory.value / "docs" / "_template",
    git.remoteRepo := "git@github.com:stringbean/scala-xml-compare.git",
    paradoxProperties in Paradox ++= Map(
      "scaladoc.software.purpledragon.xml.base_url" -> ".../api"
    )
  )
  .enablePlugins(ScalaUnidocPlugin, GhpagesPlugin, ParadoxSitePlugin)
