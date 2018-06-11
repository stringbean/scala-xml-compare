import com.lucidchart.sbt.scalafmt.ScalafmtCorePlugin.autoImport._
import sbt.Keys._
import sbt._
import sbt.plugins.JvmPlugin

object SettingsPlugin extends AutoPlugin {
  override def trigger: PluginTrigger = AllRequirements
  override def requires: Plugins = JvmPlugin

  private val javaVersion = "1.8"
  private val scalatestStable = "3.0.5"
  private val scalatestSnapshot = "3.0.6-SNAP4"

  object autoImport {
    val scalatestVersion = settingKey[String]("Version of scalatest to use")
  }

  import autoImport._

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
    scalafmtVersion := "1.5.1",
    autoAPIMappings := true,
    startYear := Some(2017),
    licenses += ("Apache-2.0", url("https://opensource.org/licenses/Apache-2.0")),
    developers := List(
      Developer("stringbean", "Michael Stringer", "@the_stringbean", url("https://github.com/stringbean"))
    ),
    organizationName := "Michael Stringer",
    organizationHomepage := Some(url("https://purpledragon.software")),
    homepage := Some(url("https://stringbean.github.io/scala-xml-compare")),
    scmInfo := Some(ScmInfo(url("https://github.com/stringbean/scala-xml-compare"), "https://github.com/stringbean/scala-xml-compare.git")),
    scalatestVersion := {
      scalaVersion.value match {
        case "2.13.0-M5" => scalatestSnapshot
        case _ => scalatestStable
      }
    }
  )
}
