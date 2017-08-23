// code style
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.9.0")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")
addSbtPlugin("com.lucidchart" % "sbt-scalafmt" % "1.10")
addSbtPlugin("de.heikoseeberger" % "sbt-header" % "3.0.1")

// dependency management
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.1")

// artifact publishing
addSbtPlugin("org.foundweekends" % "sbt-bintray" % "0.5.1")
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.0")

// documentation
addSbtPlugin("com.eed3si9n" % "sbt-unidoc" % "0.4.1")