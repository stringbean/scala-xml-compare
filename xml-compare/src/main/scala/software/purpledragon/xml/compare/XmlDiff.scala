package software.purpledragon.xml.compare

sealed trait XmlDiff {
  def isEqual: Boolean
}

object XmlEqual extends XmlDiff {
  override def isEqual: Boolean = true

  override def toString: String = "XmlEqual"
}

case class XmlDiffers(reason: String, left: Any, right: Any) extends XmlDiff {
  override def isEqual: Boolean = false
}
