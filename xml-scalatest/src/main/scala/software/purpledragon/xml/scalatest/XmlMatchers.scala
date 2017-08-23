package software.purpledragon.xml.scalatest

import org.scalatest.matchers.Matcher

import scala.xml.Node

trait XmlMatchers {
  def beXml(node: Node): Matcher[Node] = ???

  def beExactXml(node: Node): Matcher[Node] = ???
}
