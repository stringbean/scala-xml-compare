package software.purpledragon.xml.compare

import software.purpledragon.xml.compare.options.DiffOption._
import software.purpledragon.xml.compare.options.DiffOptions

import scala.xml.Node

object XmlCompare {
  private type Check = (Node, Node, DiffOptions) => XmlDiff

  val DefaultOptions: DiffOptions = Set(IgnorePrefix)

  def compare(left: Node, right: Node, options: DiffOptions = DefaultOptions): XmlDiff = {
    val checks: Seq[Check] = Seq(compareNamespace)

    checks.foldLeft[XmlDiff](XmlEqual) { (status, check) =>
      if (!status.isEqual) {
        // already failed
        status
      } else {
        check(left, right, options)
      }
    }
  }

  private def compareNamespace(left: Node, right: Node, options: DiffOptions): XmlDiff = {
    if (left.label != right.label) {
      XmlDiffers("different label")
    } else if (left.namespace != right.namespace) {
      XmlDiffers("different namespace")
    } else if (left.prefix != right.prefix && !options.contains(IgnorePrefix)) {
      XmlDiffers("different prefix")
    } else {
      XmlEqual
    }
  }
}
