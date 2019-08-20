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

import scala.xml.{Atom, Node}

/**
 * Utility for comparing XML documents.
 */
object XmlCompare {
  private type Check = (Node, Node, DiffOptions, Seq[String]) => XmlDiff

  /**
   * Default [[software.purpledragon.xml.compare.options.DiffOption.DiffOption DiffOption]]s to use during XML comparison.
   *
   * Currently these are:
   *  - [[software.purpledragon.xml.compare.options.DiffOption.IgnoreNamespacePrefix IgnoreNamespacePrefix]]
   */
  val DefaultOptions: DiffOptions = Set(IgnoreNamespacePrefix)

  /**
   * Compares two XML documents. This will perform a recursive scan of all the nodes in each document, checking each
   * for node name, namespace and text.
   *
   * @param left the first XML document to compare.
   * @param right the second XML document to compare.
   * @param options configuration options to control the way the comparison is performed.
   * @return results of the XML comparison.
   */
  def compare(left: Node, right: Node, options: DiffOptions = DefaultOptions): XmlDiff = {
    compareNodes(left, right, options, Nil)
  }

  private def compareNodes(left: Node, right: Node, options: DiffOptions, path: Seq[String]): XmlDiff = {
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
        check(left, right, options, path)
      }
    }
  }

  private def compareNamespace(left: Node, right: Node, options: DiffOptions, path: Seq[String]): XmlDiff = {
    if (left.label != right.label) {
      XmlDiffers("different label", left.label, right.label, extendPath(path, left))
    } else if (left.namespace != right.namespace && !options.contains(IgnoreNamespace)) {
      XmlDiffers("different namespace", left.namespace, right.namespace, extendPath(path, left))
    } else if (left.prefix != right.prefix && !options.contains(IgnoreNamespacePrefix) &&
               !options.contains(IgnoreNamespace)) {
      XmlDiffers("different namespace prefix", left.prefix, right.prefix, extendPath(path, left))
    } else {
      XmlEqual
    }
  }

  private def compareText(left: Node, right: Node, options: DiffOptions, path: Seq[String]): XmlDiff = {
    def extractText(node: Node): String = node.child.collect({ case a: Atom[_] => a }).map(_.text.trim).mkString
    val leftText = extractText(left)
    val rightText = extractText(right)

    if (leftText != rightText) {
      XmlDiffers("different text", leftText, rightText, extendPath(path, left))
    } else {
      XmlEqual
    }
  }

  private def compareChildren(left: Node, right: Node, options: DiffOptions, path: Seq[String]): XmlDiff = {
    val leftChildren = left.child.filterNot(c => c.isInstanceOf[Atom[_]])
    val rightChildren = right.child.filterNot(c => c.isInstanceOf[Atom[_]])

    if (leftChildren.size != rightChildren.size) {
      XmlDiffers("different child count", leftChildren.size, rightChildren.size, extendPath(path, left))
    } else {
      leftChildren.zip(rightChildren).foldLeft[XmlDiff](XmlEqual) {
        case (status, (leftChild, rightChild)) =>
          if (!status.isEqual) {
            // already failed
            status
          } else {
            compareNodes(leftChild, rightChild, options, extendPath(path, left))
          }
      }
    }
  }

  private def extendPath(path: Seq[String], node: Node): Seq[String] = {
    path :+ node.nameToString(new StringBuilder()).toString
  }
}
