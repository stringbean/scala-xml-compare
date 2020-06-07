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

object DiffOptions {

  /**
   * Default [[DiffOption DiffOption]]s to use during XML comparison.
   *
   * Currently these are:
   *  - [[DiffOption.IgnoreNamespacePrefix IgnoreNamespacePrefix]]
   */
  val default: DiffOptions = DiffOptions(DiffOption.IgnoreNamespacePrefix)

  /**
   * Empty set of [[DiffOption DiffOption]]s.
   */
  val empty: DiffOptions = DiffOptions()

  /**
   * Creates a `DiffOptions` with the supplied list of [[DiffOption DiffOption]]s.
   */
  def apply(options: DiffOption.DiffOption*): DiffOptions = DiffOptions(options.toSet)
}

/**
 * Collection of [[DiffOption DiffOption]]s to control XML comparison.
 *
 * @param options set of enabled options.
 */
case class DiffOptions(options: Set[DiffOption.DiffOption]) {
  /** Creates a new `DiffOptions` with the supplied option added. */
  def &(option: DiffOption.DiffOption): DiffOptions = copy(options = options + option)
  /** Creates a new `DiffOptions` with the supplied option removed. */
  def &!(option: DiffOption.DiffOption): DiffOptions = copy(options = options - option)

  /** Creates a new `DiffOptions` with the supplied option added. */
  def +(option: DiffOption.DiffOption): DiffOptions = copy(options = options + option)
  /** Creates a new `DiffOptions` with the supplied option removed. */
  def -(option: DiffOption.DiffOption): DiffOptions = copy(options = options - option)

  /** Tests whether the supplied option is enabled. */
  def isEnabled(option: DiffOption.DiffOption): Boolean = options.contains(option)
  /** Tests whether the supplied option is disabled. */
  def isDisabled(option: DiffOption.DiffOption): Boolean = !options.contains(option)
}
