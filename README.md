[![Build Status](https://img.shields.io/travis/stringbean/scala-xml-compare/master.svg)](https://travis-ci.org/stringbean/scala-xml-compare)
[![Codacy Grade](https://img.shields.io/codacy/grade/47939504d2cc49b0a7eb21f6bcc5c24d.svg?label=codacy)](https://www.codacy.com/app/stringbean/scala-xml-compare)
[![Test Coverage](https://img.shields.io/codecov/c/github/stringbean/scala-xml-compare/master.svg)](https://codecov.io/gh/stringbean/scala-xml-compare)
[![Maven Central - Scala 2.11](https://img.shields.io/maven-central/v/software.purpledragon.xml/xml-compare_2.11.svg?label=scala%202.11)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22software.purpledragon%22%20a%3A%22xml-compare_2.11%22)
[![Maven Central - Scala 2.12](https://img.shields.io/maven-central/v/software.purpledragon.xml/xml-compare_2.12.svg?label=scala%202.12)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22software.purpledragon%22%20a%3A%22xml-compare_2.12%22)
[![Maven Central - Scala 2.13-M5](https://img.shields.io/maven-central/v/software.purpledragon.xml/xml-compare_2.13.0-M5.svg?label=scala%202.13-M5)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22software.purpledragon%22%20a%3A%22xml-compare_2.13.0-M5%22)

# XML Comparison Utils for Scala

`scala-xml-compare` is a small Scala library for comparing XML documents.

## Quickstart

Add the following to your `build.sbt`:

```scala
libraryDependencies += "purpledragon.software" %% "scala-xml" % "<version>"
```

You can then compare XML using:

```scala
val doc1 = <person><name>John Smith</name></person>
val doc2 = <person><name>Peter Smith</name></person>

val result = XmlCompare.compare(doc1, doc2)
// result1 = XmlDiffers("different text", "John Smith", "Peter Smith")
```

### Scalatest

A companion library is provided for testing XML in Scalatest:

```scala
libraryDependencies += "purpledragon.software" %% "scala-scalatest" % "<version>" % Test
```

This then enables the `beXml` matcher:

```scala
val doc = <person><name>John Smith</name></person>
doc should beXml(<person><name>John Smith</name></person>)
```

### specs2

Similarly a companion library for specs2 is provided:

```scala
libraryDependencies += "purpledragon.software" %% "scala-specs2" % "<version>" % Test
```