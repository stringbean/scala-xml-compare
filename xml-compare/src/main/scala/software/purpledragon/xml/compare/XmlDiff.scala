package software.purpledragon.xml.compare

sealed trait XmlDiff {
  def isEqual: Boolean
}

object XmlEqual extends XmlDiff {
  override def isEqual: Boolean = true
}

case class XmlDiffers(reason: String) extends XmlDiff {
  override def isEqual: Boolean = false
}
