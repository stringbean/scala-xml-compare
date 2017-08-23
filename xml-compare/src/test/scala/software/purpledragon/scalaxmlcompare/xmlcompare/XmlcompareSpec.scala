package software.purpledragon.scalaxmlcompare.xmlcompare

import org.scalatest._

class XmlcompareSpec extends FlatSpec with Matchers {
  val xmlcompare = new Xmlcompare

  "greet" should "greet bob" in {
    xmlcompare.greet("bob") shouldBe "hello, bob"
  }
}
