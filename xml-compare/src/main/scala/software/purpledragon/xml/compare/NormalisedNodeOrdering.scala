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

import software.purpledragon.xml.XmlUtils.extractAttributes

import scala.xml._

/**
 * Arbitrary ordering for XML nodes used to normalise XML when we are ignoring child ordering.
 */
private[compare] object NormalisedNodeOrdering extends Ordering[Node] {
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
