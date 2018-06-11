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

import software.purpledragon.xml.compare.options.DiffOption._
import software.purpledragon.xml.compare.options.DiffOptions

import scala.xml.{Node, Text}

object XmlCompare {
  private type Check = (Node, Node, DiffOptions) => XmlDiff

  val DefaultOptions: DiffOptions = Set(IgnoreNamespacePrefix)

  def compare(left: Node, right: Node, options: DiffOptions = DefaultOptions): XmlDiff = {
    val checks: Seq[Check] = Seq(
      compareNamespace,
      compareText,
      compareChildren
    )

    checks.foldLeft[XmlDiff](XmlEqual) { (status, check) =>
      if (!status.isEqual) {
        // already failed
        status
      } else {
        check(left, right, options)
      }
    }
  }

  private def compareNamespace(left: Node, right: Node, options: DiffOptions): XmlDiff = {
    if (left.label != right.label) {
      XmlDiffers("different label", left.label, right.label)
    } else if (left.namespace != right.namespace && !options.contains(IgnoreNamespace)) {
      XmlDiffers("different namespace", left.namespace, right.namespace)
    } else if (left.prefix != right.prefix && !options.contains(IgnoreNamespacePrefix) &&
               !options.contains(IgnoreNamespace)) {
      XmlDiffers("different namespace prefix", left.prefix, right.prefix)
    } else {
      XmlEqual
    }
  }

  private def compareText(left: Node, right: Node, options: DiffOptions): XmlDiff = {
    val leftText = left.child.collect({ case t: Text => t }).map(_.text.trim).mkString
    val rightText = right.child.collect({ case t: Text => t }).map(_.text.trim).mkString

    if (leftText != rightText) {
      XmlDiffers("different text", leftText, rightText)
    } else {
      XmlEqual
    }
  }

  private def compareChildren(left: Node, right: Node, options: DiffOptions): XmlDiff = {
    val leftChildren = left.child.filterNot(_.isInstanceOf[Text])
    val rightChildren = right.child.filterNot(_.isInstanceOf[Text])

    if (leftChildren.size != rightChildren.size) {
      XmlDiffers("child count", leftChildren.size, rightChildren.size)
    } else {
      leftChildren.zip(rightChildren).foldLeft[XmlDiff](XmlEqual) {
        case (status, (leftChild, rightChild)) =>
          if (!status.isEqual) {
            // already failed
            status
          } else {
            compare(leftChild, rightChild, options)
          }
      }
    }
  }
}
