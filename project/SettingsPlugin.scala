import com.lucidchart.sbt.scalafmt.ScalafmtCorePlugin.autoImport._
import de.heikoseeberger.sbtheader.HeaderPlugin
import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport._
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
    scalafmtVersion := "1.2.0",
    autoAPIMappings := true,
    headerLicense := Some(HeaderLicense.ALv2("2017", "Michael Stringer")),
    licenses += ("Apache-2.0", url("https://opensource.org/licenses/Apache-2.0")),
    developers := List(
      Developer("stringbean", "Michael Stringer", "@the_stringbean", url("https://github.com/stringbean"))
    ),
    organizationName := "Purple Dragon Software",
    organizationHomepage := Some(url("https://purpledragon.software")),
    homepage := Some(url("https://stringbean.github.io/scala-xml-compare")),
    scmInfo := Some(ScmInfo(url("https://github.com/stringbean/scala-xml-compare"), "https://github.com/stringbean/scala-xml-compare.git"))
  )
}
