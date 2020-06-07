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

import org.specs2.matcher.Expectations
import org.specs2.mutable.Specification
import software.purpledragon.xml.compare.options.DiffOptions

class XmlMatchersSpec extends Specification with XmlMatchers with Expectations {

  "beXml" should {
    "match identical XML" in {
      val matcher = beXml(<test>text</test>)

      val matchResult = matcher(createExpectable(<test>text</test>))
      matchResult.isSuccess === true
    }

    "match XML with different whitespace" in {
      val matcher = beXml(<test>text</test>)

      val matchResult = matcher(createExpectable(<test>
          text
        </test>))
      matchResult.isSuccess === true
    }

    "match XML with different namespace" in {
      val matcher = beXml(<ns1:test>text</ns1:test>)

      val matchResult = matcher(createExpectable(<ns2:test>text</ns2:test>))
      matchResult.isSuccess === true
    }

    "not match different XML" in {
      val matcher = beXml(<test>text</test>)

      val matchResult = matcher(createExpectable(<test>different</test>))
      matchResult.isSuccess === false
      matchResult.message === "XML did not match at [test]: different text: [text] != [different]"
    }

    "not match different nested XML" in {
      val matcher = beXml(<test><nested>text</nested></test>)

      val matchResult = matcher(createExpectable(<test><nested>different</nested></test>))
      matchResult.isSuccess === false
      matchResult.message === "XML did not match at [test / nested]: different text: [text] != [different]"
    }

    "not match different namespaced XML" in {
      val matcher = beXml(<t:test>text</t:test>)

      val matchResult = matcher(createExpectable(<f:test>different</f:test>))
      matchResult.isSuccess === false
      matchResult.message === "XML did not match at [t:test]: different text: [text] != [different]"
    }
  }

  "beXml(xml)(options)" should {
    "not match XML with different namespace" in {
      val matcher = beXml(<ns1:test>text</ns1:test>)(DiffOptions.empty)

      val matchResult = matcher(createExpectable(<ns2:test>text</ns2:test>))
      matchResult.isSuccess === false
      matchResult.message === "XML did not match at [ns1:test]: different namespace prefix: [ns1] != [ns2]"
    }
  }

  "beXml(xml) with implicit options" should {
    "not match XML with different namespace" in {
      implicit val options: DiffOptions = DiffOptions.empty
      val matcher = beXml(<ns1:test>text</ns1:test>)

      val matchResult = matcher(createExpectable(<ns2:test>text</ns2:test>))
      matchResult.isSuccess === false
      matchResult.message === "XML did not match at [ns1:test]: different namespace prefix: [ns1] != [ns2]"
    }
  }
}
