import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin

object SettingsPlugin extends AutoPlugin {
  override def trigger: PluginTrigger = AllRequirements
  override def requires: Plugins = JvmPlugin

  val javaVersion = "1.8"

  override def projectSettings: Seq[Setting[_]] = Seq(
    organization := (organization in LocalRootProject).value,
    version := (version in LocalRootProject).value,
    scalaVersion := (scalaVersion in LocalRootProject).value,
    crossScalaVersions := (crossScalaVersions in LocalRootProject).value,
    javacOptions ++= Seq("-source", javaVersion,
      "-target", javaVersion,
      "-Xlint"),
    scalacOptions ++= Seq(s"-target:jvm-$javaVersion",
      "-deprecation",
      "-feature",
      "-unchecked"),
    libraryDependencies ++= (libraryDependencies in LocalRootProject).value,
    autoAPIMappings := true
  )
}
