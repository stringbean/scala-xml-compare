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

package software.purpledragon.xml.compare

/**
 * Results of a comparison between XML documents. Can be either [[XmlEqual]] or [[XmlDiffers]] (with details of the
 * differences).
 */
sealed trait XmlDiff {

  /** `true` if the XML documents are the same. */
  def isEqual: Boolean

  /** Descriptive message containing the differences (if any). */
  def message: String
}

/**
 * Result of a successful comparison between XML documents.
 */
object XmlEqual extends XmlDiff {
  override val isEqual = true
  override def toString: String = "XmlEqual"
  override val message = ""
}

/**
 * Result of an unsuccessful comparison between XML documents.
 *
 * @param reason descriptive reason for the comparison failing.
 * @param left the left-hand value in the failed comparison.
 * @param right the right-hand value in the failed comparison.
 */
case class XmlDiffers(reason: String, left: Any, right: Any) extends XmlDiff {
  override val isEqual = false
  override val message = s"$reason - $left != $right"
}
