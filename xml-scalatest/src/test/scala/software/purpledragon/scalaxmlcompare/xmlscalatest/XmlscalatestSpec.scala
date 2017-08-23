package software.purpledragon.scalaxmlcompare.xmlscalatest

import org.scalatest._

class XmlscalatestSpec extends FlatSpec with Matchers {
  val xmlscalatest = new Xmlscalatest

  "greet" should "greet bob" in {
    xmlscalatest.greet("bob") shouldBe "hello, bob"
  }
}
