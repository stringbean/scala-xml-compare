/*
 * Copyright 2017 Michael Stringer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package software.purpledragon.xml.scalatest

import org.scalatest.matchers.{MatchResult, Matcher}
import software.purpledragon.xml.compare.XmlCompare

import scala.xml.Node

/**
 * Companion object to the [[XmlMatchers]] trait for use as an alternative to mixing it in.
 */
object XmlMatchers extends XmlMatchers

/**
 * Provides [[http://www.scalatest.org Scalatest]] matchers for checking XML documents.
 */
trait XmlMatchers {

  /**
   * Asserts that two XML documents are equal.
   *
   * Enables the following syntax:
   * {{{
   *   result should beXml(<example>1</example>)
   * }}}
   */
  def beXml(expected: Node): Matcher[Node] = new XmlMatcher(expected)

  private class XmlMatcher(expected: Node) extends Matcher[Node] {
    override def apply(actual: Node): MatchResult = {
      val diff = XmlCompare.compare(expected, actual)

      val failureBuilder = new StringBuilder()
      failureBuilder ++= "XML did not match"

      if (diff.failurePath.nonEmpty) {
        failureBuilder ++= " at "
        diff.failurePath.addString(failureBuilder, "[", " / ", "]")
      }

      failureBuilder ++= ": "
      failureBuilder ++= diff.message

      MatchResult(
        diff.isEqual,
        failureBuilder.toString(),
        "XML matched"
      )
    }
  }
}
