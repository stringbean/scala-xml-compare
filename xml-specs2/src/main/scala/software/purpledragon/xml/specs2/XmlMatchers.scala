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

package software.purpledragon.xml.specs2

import org.specs2.matcher.Matcher
import org.specs2.matcher.MatchersImplicits._
import software.purpledragon.xml.compare.XmlCompare

import scala.xml.Node

/**
 * Companion object to the [[XmlMatchers]] trait for use as an alternative to mixing it in.
 */
object XmlMatchers extends XmlMatchers

/**
 * Provides [[https://etorreborre.github.io/specs2 Specs2]] matchers for checking XML documents.
 */
trait XmlMatchers {

  /**
   * Asserts that two XML documents are equal.
   *
   * Enables the following syntax:
   * {{{
   *   result must beXml(<example>1</example>)
   * }}}
   */
  def beXml(expected: Node): Matcher[Node] = { actual: Node =>
    val diff = XmlCompare.compare(expected, actual)

    (diff.isEqual, "XML matched", s"XML did not match - ${diff.message}")
  }
}
