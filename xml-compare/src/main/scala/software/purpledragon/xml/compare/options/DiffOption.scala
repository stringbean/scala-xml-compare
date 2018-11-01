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

/**
 * Configuration options for XML comparisons.
 */
object DiffOption extends Enumeration {
  type DiffOption = Value

  /**
   * Ignores XML namespace prefixes on elements.
   *
   * Enabling this makes this:
   * {{{
   *   <t:example xmlns:t="http://example.com">5</t:example>
   * }}}
   * equal to:
   * {{{
   *   <f:example xmlns:f="http://example.com">5</f:example>
   * }}}
   */
  val IgnoreNamespacePrefix: DiffOption.Value = Value

  /**
   * Ignores XML namespaces completely.
   *
   * Enabling this makes this:
   * {{{
   *   <t:example xmlns:t="http://example.com">5</t:example>
   * }}}
   * equal to:
   * {{{
   *   <f:example xmlns:f="http://example.org">5</f:example>
   * }}}
   */
  val IgnoreNamespace: DiffOption.Value = Value
}
