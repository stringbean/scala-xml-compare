package software.purpledragon.xml.compare

import org.scalatest.{FlatSpec, Matchers}
import software.purpledragon.xml.compare.options.DiffOption._

class XmlCompareSpec extends FlatSpec with Matchers {

  "compare with defaults" should "match same empty element" in {
    XmlCompare.compare(<test/>, <test/>) shouldBe XmlEqual
  }

  it should "match empty elements ignoring collapsed" in {
    XmlCompare.compare(<test/>, <test></test>) shouldBe XmlEqual
  }

  it should "not-match different label" in {
    XmlCompare.compare(<test/>, <foo/>) shouldBe XmlDiffers("different label", "test", "foo")
  }

  it should "match same namespaces" in {
    XmlCompare.compare(<t:test xmlns:t="http://example.com"/>, <t:test xmlns:t="http://example.com"/>) shouldBe XmlEqual
  }

  it should "match different namespace prefix" in {
    XmlCompare.compare(<t:test xmlns:t="http://example.com"/>, <e:test xmlns:e="http://example.com"/>) shouldBe XmlEqual
  }

  it should "not-match different namespace url" in {
    XmlCompare.compare(<t:test xmlns:t="http://example.com"/>, <t:test xmlns:t="http://foo.com"/>) shouldBe
      XmlDiffers("different namespace", "http://example.com", "http://foo.com")
  }

  it should "match with same single child" in {
    XmlCompare.compare(<test><test2/></test>, <test><test2/></test>) shouldBe XmlEqual
  }

  it should "not-match with different single child" in {
    XmlCompare.compare(<test><test2/></test>, <test><foo/></test>) shouldBe XmlDiffers(
      "different label",
      "test2",
      "foo")
  }

  it should "match with same text contents" in {
    XmlCompare.compare(<test>contents</test>, <test>contents</test>) shouldBe XmlEqual
  }

  it should "not-match different text contents" in {
    XmlCompare.compare(<test>contents</test>, <test/>) shouldBe XmlDiffers("different text", "contents", "")
  }

  it should "match with text content differing only by whitespace" in {
    XmlCompare.compare(<test>contents  </test>, <test>contents</test>) shouldBe XmlEqual
  }

  it should "match with child & text content in different order" in {
    XmlCompare.compare(<test><test2/>contents</test>, <test>contents<test2/></test>) shouldBe XmlEqual
  }

  "compare without IgnoreNamespacePrefix" should "not-match different namespace prefix" in {
    XmlCompare.compare(
        <t:test xmlns:t="http://example.com"/>,
        <e:test xmlns:e="http://example.com"/>,
        Set.empty) shouldBe XmlDiffers("different namespace prefix", "t", "e")
  }

  it should "match same namespaces" in {
    XmlCompare.compare(
        <t:test xmlns:t="http://example.com"/>,
        <t:test xmlns:t="http://example.com"/>,
        Set.empty) shouldBe XmlEqual
  }

  "compare with IgnoreNamespace" should "match different namespace prefix" in {
    XmlCompare.compare(<t:test xmlns:t="http://example.com"/>, <e:test xmlns:e="http://example.com"/>, Set(IgnoreNamespace)) shouldBe XmlEqual
  }

  it should "match different namespace" in {
    XmlCompare.compare(<t:test xmlns:t="http://example.com"/>, <t:test xmlns:e="http://example.org"/>, Set(IgnoreNamespace)) shouldBe XmlEqual
  }
}
