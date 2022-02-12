package model

import enumeratum._

sealed trait Status extends EnumEntry

object Status extends Enum[Status] {

  val values = findValues

  case object Alive extends Status
  case object Dead extends Status

}
