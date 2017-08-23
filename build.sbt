organization := "software.purpledragon.xml"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.8"

scalaVersion := "2.12.3"
crossScalaVersions := Seq("2.11.11", "2.12.3")

// dependencies common for all sub-projects
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.4" % "test"
)

lazy val xmlCompare = project
  .in(file("xml-compare"))

lazy val xmlScalatest = project
  .in(file("xml-scalatest"))

lazy val root = project
  .in(file("."))
  .aggregate(
    xmlCompare,
    xmlScalatest
  )
  .settings(
    publish := {},
    publishLocal := {},
    test := {},
    testOnly := {}
  )
  .enablePlugins(ScalaUnidocPlugin)
