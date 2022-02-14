package core.util

import scala.util.Random

object RichSeq {

  implicit class RichSeq[T](seq: Seq[T]) {

    def random: Option[T] = seq.length match {
      case n if n > 0 => Some(seq(Random.nextInt(n)))
      case _ => None
    }
  }
}
