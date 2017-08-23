import PgpKeys.{publishLocalSigned, publishSigned}

organization := "software.purpledragon.xml"
version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.8"

scalaVersion := "2.12.3"
crossScalaVersions := Seq("2.11.11", "2.12.3")

// dependencies common for all sub-projects
libraryDependencies ++= Seq(
  "org.scala-lang.modules"  %% "scala-xml"  % "1.0.6",
  "org.scalatest"           %% "scalatest"  % "3.0.4" % "test"
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
    publishSigned := {},
    publishLocal := {},
    publishLocalSigned := {},
    test := {},
    testOnly := {}
  )
  .enablePlugins(ScalaUnidocPlugin)

useGpg := true
usePgpKeyHex("B19D7A14F6F8B3BFA9FF655A5216B5A5F723A92D")