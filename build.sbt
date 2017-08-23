import PgpKeys.{publishLocalSigned, publishSigned}
import com.typesafe.sbt.SbtGit.GitKeys._

organization := "software.purpledragon.xml"
version := "0.0.2"

scalaVersion := "2.12.3"
crossScalaVersions := Seq("2.11.11", "2.12.3")

// dependencies common for all sub-projects
libraryDependencies ++= Seq(
  "org.scala-lang.modules"  %% "scala-xml"  % "1.0.6"
)

lazy val xmlCompare = Project("xml-compare", file("xml-compare"))

lazy val xmlScalatest = Project("xml-scalatest", file("xml-scalatest"))
  .dependsOn(xmlCompare)

lazy val xmlSpecs2 = Project("xml-specs2", file("xml-specs2"))
  .dependsOn(xmlCompare)

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
    ghpagesNoJekyll := true
  )
  .enablePlugins(ScalaUnidocPlugin, GhpagesPlugin)

useGpg := true
usePgpKeyHex("B19D7A14F6F8B3BFA9FF655A5216B5A5F723A92D")