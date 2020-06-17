[![Build Status](https://img.shields.io/travis/com/stringbean/scala-xml-compare/master.svg)](https://travis-ci.com/stringbean/scala-xml-compare)
[![Codacy Grade](https://img.shields.io/codacy/grade/47939504d2cc49b0a7eb21f6bcc5c24d.svg?label=codacy)](https://www.codacy.com/app/stringbean/scala-xml-compare)
[![Test Coverage](https://img.shields.io/codecov/c/github/stringbean/scala-xml-compare/master.svg)](https://codecov.io/gh/stringbean/scala-xml-compare)
[![Maven Central - Scala 2.11](https://img.shields.io/maven-central/v/software.purpledragon.xml/xml-compare_2.11.svg?label=scala%202.11)](https://search.maven.org/search?q=g:software.purpledragon.xml%20a:xml-compare_2.11)
[![Maven Central - Scala 2.12](https://img.shields.io/maven-central/v/software.purpledragon.xml/xml-compare_2.12.svg?label=scala%202.12)](https://search.maven.org/search?q=g:software.purpledragon.xml%20a:xml-compare_2.12)
[![Maven Central - Scala 2.13](https://img.shields.io/maven-central/v/software.purpledragon.xml/xml-compare_2.13.svg?label=scala%202.13)](https://search.maven.org/search?q=g:software.purpledragon.xml%20a:xml-compare_2.13)
![MiMa Checked Against](https://img.shields.io/badge/MiMa%20Compatiblitity-2.0.0-brightGreen)

# XML Comparison Utils for Scala

`scala-xml-compare` is a small Scala library for comparing XML documents.

## Quickstart

Add the following to your `build.sbt`:

```scala
libraryDependencies += "software.purpledragon.xml" %% "xml-compare" % "<version>"
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
libraryDependencies += "software.purpledragon.xml" %% "xml-scalatest" % "<version>" % Test
```

This then enables the `beXml` matcher:

```scala
val doc = <person><name>John Smith</name></person>
doc should beXml(<person><name>John Smith</name></person>)
```

### specs2

Similarly a companion library for specs2 is provided:

```scala
libraryDependencies += "software.purpledragon.xml" %% "xml-specs2" % "<version>" % Test
```
