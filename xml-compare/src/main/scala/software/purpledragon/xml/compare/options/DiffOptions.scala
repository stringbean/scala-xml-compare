package software.purpledragon.xml.compare.options

object DiffOptions {

  /**
   * Default [[DiffOption DiffOption]]s to use during XML comparison.
   *
   * Currently these are:
   *  - [[DiffOption.IgnoreNamespacePrefix IgnoreNamespacePrefix]]
   */
  val default: DiffOptions = DiffOptions(DiffOption.IgnoreNamespacePrefix)
  val empty: DiffOptions = DiffOptions()

  def apply(options: DiffOption.DiffOption*): DiffOptions = DiffOptions(options.toSet)
}

case class DiffOptions(options: Set[DiffOption.DiffOption]) {
  def &(option: DiffOption.DiffOption): DiffOptions = copy(options = options + option)
  def &!(option: DiffOption.DiffOption): DiffOptions = copy(options = options - option)

  def +(option: DiffOption.DiffOption): DiffOptions = copy(options = options + option)
  def -(option: DiffOption.DiffOption): DiffOptions = copy(options = options - option)

  def isEnabled(option: DiffOption.DiffOption): Boolean = options.contains(option)
  def isDisabled(option: DiffOption.DiffOption): Boolean = !options.contains(option)
}
