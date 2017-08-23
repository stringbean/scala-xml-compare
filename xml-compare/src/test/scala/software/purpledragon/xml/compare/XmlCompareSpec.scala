package software.purpledragon.xml.compare

import org.scalatest.{FlatSpec, Matchers}

class XmlCompareSpec extends FlatSpec with Matchers {

  "comparing empty" should "match same element name" in {
    XmlCompare.compare(<test/>, <test/>) shouldBe XmlEqual
  }

  it should "blah" in {
    XmlCompare.compare(<test/>, <test></test>) shouldBe XmlEqual
  }

  it should "not-match" in {
    XmlCompare.compare(<test/>, <foo/>) shouldBe XmlDiffers("different label")
  }

  it should "match same namespaces" in {
    XmlCompare.compare(<t:test xmlns:t="http://example.com"/>, <t:test xmlns:t="http://example.com"/>) shouldBe XmlEqual
  }

  it should "match different namespace prefix" in {
    XmlCompare.compare(
        <t:test xmlns:t="http://example.com"/>,
        <e:test xmlns:e="http://example.com"/>) shouldBe XmlEqual
  }

  it should "not-match different namespace prefix (with IgnorePrefix disabled)" in {
    XmlCompare.compare(
        <t:test xmlns:t="http://example.com"/>,
        <e:test xmlns:e="http://example.com"/>, Set.empty) shouldBe XmlDiffers("different prefix")
  }

  it should "not-match different namespace url" in {
    XmlCompare.compare(
        <t:test xmlns:t="http://example.com"/>,
        <t:test xmlns:t="http://foo.com"/>) shouldBe XmlDiffers("different namespace")
  }
}
