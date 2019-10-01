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

package software.purpledragon.xml

import org.scalatest.{FlatSpec, Matchers}

class XmlUtilsSpec extends FlatSpec with Matchers {
  "XmlUtils.extractAttributes" should "return empty values for no attributes" in {
    XmlUtils.extractAttributes(<empty/>) shouldBe (Nil, Map.empty[String, String])
  }

  it should "return attribute names and value map" in {
    val (names, attributes) = XmlUtils.extractAttributes(<test field3="value-3" field1="value-1" field2="value-2"/>)

    names shouldBe Seq("field3", "field1", "field2")
    attributes shouldBe Map(
      "field1" -> "value-1",
      "field2" -> "value-2",
      "field3" -> "value-3"
    )
  }
}
