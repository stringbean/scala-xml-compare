# scala-xml-compare

scala-xml-compare is a small Scala library for comparing XML documents.

## Getting Started

@@@vars
```scala
libraryDependencies += "purpledragon.software.xml" %% "xml-compare" % "$project.version$"
```
@@@

Companion libraries are also provided for comparing XML from [Scalatest](http://scalatest.org/) and
[Specs2](https://etorreborre.github.io/specs2/):

@@@vars
```scala
// scalatest
libraryDependencies += "purpledragon.software.xml" %% "xml-scalatest" % "$project.version$" % Test

// specs2
libraryDependencies += "purpledragon.software.xml" %% "xml-specs2" % "$project.version$" % Test
```
@@@

@@@ index

* [Comparing XML](comparing-xml.md)
* [Scalatest](scalatest.md)
* [Specs2](specs2.md)
* [Scaladocs](@scaladoc[XmlDiff](software.purpledragon.xml.compare.XmlDiff))
@@@
