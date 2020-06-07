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

package software.purpledragon.xml.compare.options

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DiffOptionsSpec extends AnyFlatSpec with Matchers {

  "&" should "add an option" in {
    val options = DiffOptions(DiffOption.IgnoreNamespace) & DiffOption.IgnoreChildOrder
    options.options shouldBe Set(DiffOption.IgnoreNamespace, DiffOption.IgnoreChildOrder)
  }

  it should "not add a duplicate option" in {
    val options = DiffOptions(DiffOption.IgnoreNamespace) & DiffOption.IgnoreNamespace
    options.options shouldBe Set(DiffOption.IgnoreNamespace)
  }

  "&!" should "remove an option" in {
    val options = DiffOptions(DiffOption.IgnoreNamespace, DiffOption.IgnoreChildOrder) &! DiffOption.IgnoreChildOrder
    options.options shouldBe Set(DiffOption.IgnoreNamespace)
  }

  it should "do nothing if option not present" in {
    val options = DiffOptions(DiffOption.IgnoreNamespace) &! DiffOption.IgnoreChildOrder
    options.options shouldBe Set(DiffOption.IgnoreNamespace)
  }

  "+" should "add an option" in {
    val options = DiffOptions(DiffOption.IgnoreNamespace) + DiffOption.IgnoreChildOrder
    options.options shouldBe Set(DiffOption.IgnoreNamespace, DiffOption.IgnoreChildOrder)
  }

  it should "not add a duplicate option" in {
    val options = DiffOptions(DiffOption.IgnoreNamespace) + DiffOption.IgnoreNamespace
    options.options shouldBe Set(DiffOption.IgnoreNamespace)
  }

  "-" should "remove an option" in {
    val options = DiffOptions(DiffOption.IgnoreNamespace, DiffOption.IgnoreChildOrder) - DiffOption.IgnoreChildOrder
    options.options shouldBe Set(DiffOption.IgnoreNamespace)
  }

  it should "do nothing if option not present" in {
    val options = DiffOptions(DiffOption.IgnoreNamespace) - DiffOption.IgnoreChildOrder
    options.options shouldBe Set(DiffOption.IgnoreNamespace)
  }

  "isEnabled" should "return true if an option is enabled" in {
    val options = DiffOptions(DiffOption.IgnoreNamespace)
    options.isEnabled(DiffOption.IgnoreNamespace) shouldBe true
  }

  it should "return false if an option is disabled" in {
    val options = DiffOptions.empty
    options.isEnabled(DiffOption.IgnoreNamespace) shouldBe false
  }

  "isDisabled" should "return true if an option is disabled" in {
    val options = DiffOptions(DiffOption.IgnoreNamespace)
    options.isDisabled(DiffOption.IgnoreNamespace) shouldBe false
  }

  it should "return false if an option is enabled" in {
    val options = DiffOptions.empty
    options.isDisabled(DiffOption.IgnoreNamespace) shouldBe true
  }
}
