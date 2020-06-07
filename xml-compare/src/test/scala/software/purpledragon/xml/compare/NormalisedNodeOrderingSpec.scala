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

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.xml.{Comment, Node, PCData, Text}

class NormalisedNodeOrderingSpec extends AnyFlatSpec with Matchers {
  private implicit val ordering: Ordering[Node] = NormalisedNodeOrdering

  "NormalisedNodeOrdering" should "order nodes by type" in {
    val nodes = Seq[Node](
      Comment("commented"),
      Text("textual"),
      PCData("data"),
      <element/>
    )

    nodes.sorted shouldBe Seq(
      <element/>,
      Text("textual"),
      PCData("data"),
      Comment("commented")
    )
  }

  it should "order Elems firstly by label" in {
    val nodes = Seq[Node](
      <second/>,
      <first/>
    )

    nodes.sorted shouldBe Seq(
      <first/>,
      <second/>
    )
  }

  it should "order Elems secondly by attribute count" in {
    val nodes = Seq[Node](
      <element/>,
      <element attribute1="1" attribute2="2"/>,
      <element attribute="1"/>
    )

    nodes.sorted shouldBe Seq(
      <element/>,
      <element attribute="1"/>,
      <element attribute1="1" attribute2="2"/>
    )
  }

  it should "order Elems thirdly by attribute names" in {
    val nodes = Seq[Node](
      <element attribute2="value"/>,
      <element attribute1="value"/>,
      <element attribute3="value"/>
    )

    nodes.sorted shouldBe Seq(
      <element attribute1="value"/>,
      <element attribute2="value"/>,
      <element attribute3="value"/>
    )
  }

  it should "order Elems finally by attribute values" in {
    val nodes = Seq[Node](
      <element attribute="2"/>,
      <element attribute="1"/>,
      <element attribute="3"/>
    )

    nodes.sorted shouldBe Seq(
      <element attribute="1"/>,
      <element attribute="2"/>,
      <element attribute="3"/>
    )
  }

  it should "order Text nodes" in {
    val nodes = Seq[Node](Text("banana"), Text("apple"), Text("cherry"))
    nodes.sorted shouldBe Seq(Text("apple"), Text("banana"), Text("cherry"))
  }

  it should "order PCData nodes" in {
    val nodes = Seq[Node](PCData("banana"), PCData("apple"), PCData("cherry"))
    nodes.sorted shouldBe Seq(PCData("apple"), PCData("banana"), PCData("cherry"))
  }

  it should "order Comment nodes" in {
    val nodes = Seq[Node](Comment("banana"), Comment("apple"), Comment("cherry"))
    nodes.sorted shouldBe Seq(Comment("apple"), Comment("banana"), Comment("cherry"))
  }
}
