package software.purpledragon.xml.specs2

import org.specs2.matcher.Matcher
import org.specs2.matcher.MatchersImplicits._
import software.purpledragon.xml.compare.XmlCompare

import scala.xml.Node

trait XmlMatchers {
  def beXml(expected: Node): Matcher[Node] = { actual: Node =>
    val diff = XmlCompare.compare(expected, actual)

    (diff.isEqual, "XML matched", s"XML did not match - ${diff.message}")
  }
}
