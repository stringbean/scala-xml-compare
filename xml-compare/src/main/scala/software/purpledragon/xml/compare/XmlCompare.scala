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
import software.purpledragon.xml.compare.options.{DiffOption, DiffOptions}

import scala.xml._

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
      compareAttributes,
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

  private def compareAttributes(left: Node, right: Node, options: DiffOptions, path: Seq[String]): XmlDiff = {
    val (leftKeys, leftMap) = extractAttributes(left)
    val (rightKeys, rightMap) = extractAttributes(right)

    if (leftKeys.sorted != rightKeys.sorted) {
      XmlDiffers("different attribute names", leftKeys.sorted, rightKeys.sorted, extendPath(path, left))
    } else if (options.contains(StrictAttributeOrdering) && leftKeys != rightKeys) {
      XmlDiffers("different attribute ordering", leftKeys, rightKeys, extendPath(path, left))
    } else {
      leftKeys.sorted collectFirst {
        case name if leftMap(name) != rightMap(name) =>
          XmlDiffers(s"different value for attribute '$name'", leftMap(name), rightMap(name), extendPath(path, left))
      } getOrElse XmlEqual
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
    val leftChildren = normalise(left.child, options)
    val rightChildren = normalise(right.child, options)

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

  private def extractAttributes(node: Node): (Seq[String], Map[String, String]) = {
    node.attributes.foldLeft(Seq.empty[String], Map.empty[String, String]) {
      case ((keys, attribs), attrib) =>
        (keys :+ attrib.key, attribs + (attrib.key -> attrib.value.text))
    }
  }

  private implicit object NodeOrdering extends Ordering[Node] {
    private def typeToOrdering(node: Node): Int = {
      node match {
        case _: Elem => 1
        case _: Text => 2
        case _: PCData => 3
        case _: Comment => 4
      }
    }

    override def compare(x: Node, y: Node): Int = {
      (x, y) match {
        case (xe: Elem, ye: Elem) =>
          val labelOrder = xe.label compareTo ye.label

          if (labelOrder != 0) {
            labelOrder
          } else {
            val (xAttributeNames, xAttributes) = extractAttributes(xe)
            val (yAttributeNames, yAttributes) = extractAttributes(ye)

            // order by attribute count
            val attributeSizeOrder = xAttributeNames.size compareTo yAttributeNames.size

            if (attributeSizeOrder != 0) {
              attributeSizeOrder
            } else {
              // compare attribute names
              val attributeNamesOrder = xAttributeNames.sorted zip yAttributeNames.sorted map {
                case (x, y) => x compareTo y
              }

              // take first difference
              attributeNamesOrder.find(_ != 0) match {
                case Some(v) =>
                  v
                case None =>
                  // if not compare values
                  val attributeValuesOrder = xAttributeNames map { name =>
                    xAttributes(name) compareTo yAttributes(name)
                  }

                  attributeValuesOrder.find(_ != 0).getOrElse(0)
              }
            }
          }

        case (xe: Text, ye: Text) =>
          xe.text compareTo ye.text

        case (xe: PCData, ye: PCData) =>
          xe.data compareTo ye.data

        case (xe: Comment, ye: Comment) =>
          xe.commentText compareTo ye.commentText

        case _ =>
          // different types - order by type
          typeToOrdering(x) compareTo typeToOrdering(y)
      }
    }
  }

  private def normalise(nodes: Seq[Node], options: DiffOptions): Seq[Node] = {
    val sort = options.contains(DiffOption.IgnoreChildOrder)
    val filtered = nodes.filterNot(n => n.isInstanceOf[Atom[_]])

    if (sort) {
      filtered.sorted
    } else {
      filtered
    }
  }
}
