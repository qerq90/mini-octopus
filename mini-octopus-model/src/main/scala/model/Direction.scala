package model

import enumeratum._

sealed trait Direction extends EnumEntry

object Direction extends Enum[Direction] {

  val values = findValues

  case object North extends Direction
  case object South extends Direction
  case object West extends Direction
  case object East extends Direction

}
