# Comparing XML

A simple XML comparison can be performed using
@scaladoc[`XmlCompare.compare`](software.purpledragon.xml.compare.XmlCompare$): 

```scala
// using XML literals:
val result = XmlCompare.compare(<test/>, <test/>) // == XmlEqual

// or from a file:
val success = XmlCompare.compare(<result><success/></result>, XML.loadFile("result.xml"))
```

The way that XML is compared can be customised by supplying a `Set` of
@scaladoc[`DiffOption`s](software.purpledragon.xml.compare.options.DiffOption$):

```scala
// this would result an XmlEqual
XmlCompare.compare(
  <test name="John" age="45"/>,
  <test age="45" name="John"/>)

// this would result an XmlDiffers
XmlCompare.compare(
  <test name="John" age="45"/>,
  <test age="45" name="John"/>,
  Set(DiffOption.StrictAttributeOrdering))
```

@ref:[Comparison Options](#comparison-options) contains details of the supported options.

## Comparison Results

`XmlCompare.compare` returns an @scaladoc[`XmlDiff`](software.purpledragon.xml.compare.XmlDiff) that will either be
@scaladoc[`XmlEqual`](software.purpledragon.xml.compare.XmlEqual) or a detailed 
@scaladoc[`XmlDiffers`](software.purpledragon.xml.compare.XmlDiffers). If a simple
pass/fail check is required then the `isEqual` method can be called on the result.

### XmlEqual

An `XmlEqual` result signifies that no differences in the XML were found.

### XmlDiffers

An `XmlDiffers` result will contain the _first_ difference found in the XML. The `reason` property will have a
human-readable reason for the difference, `left` & `right` will have the differences and `failurePath` will have the
path segments to the difference. 

For example:
 
```scala
XmlDiffers(
  "different attribute ordering",
  Seq("first", "second"),
  Seq("second", "first"),
  Seq("test")
)
```

## Comparison Options

By default the following options are used:

* `IgnoreNamespacePrefix`

### IgnoreNamespacePrefix

If enabled the prefixes associated with namespaces will be ignored. Differing namespaces will still cause a comparison
error.

#### Example

This:
```xml
<t:example xmlns:t="http://example.com">5</t:example>
```

would be considered equal to:
```xml
<f:example xmlns:f="http://example.com">5</f:example>
```

### IgnoreNamespace

If enabled then namespaces will be ignored completely.

#### Example

This:
```xml
<t:example xmlns:t="http://example.com">5</t:example>
```

would be considered equal to:
```xml
<f:example xmlns:f="http://example.org">5</f:example>
```

### StrictAttributeOrdering

This adds an additional comparison on the ordering of element attributes. The presence of attributes will be checked
_before_ the ordering.

#### Example

This:

```xml
<example first="a" second="b" />
```

would not be equal to:

```xml
<example second="b" first="a" />
```

and would result in the following failure:

```scala
XmlDiffers(
  "different attribute ordering",
  Seq("first", "second"),
  Seq("second", "first"),
  Seq("test")
)
```