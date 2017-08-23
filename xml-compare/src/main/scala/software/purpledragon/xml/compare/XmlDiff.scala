package software.purpledragon.xml.compare

sealed trait XmlDiff {
  def isEqual: Boolean
  def message: String
}

object XmlEqual extends XmlDiff {
  override def isEqual: Boolean = true
  override def toString: String = "XmlEqual"
  override def message: String = ""
}

case class XmlDiffers(reason: String, left: Any, right: Any) extends XmlDiff {
  override def isEqual: Boolean = false

  override def message: String = s"$reason - $left != $right"
}
