package model

import enumeratum._

sealed trait Race extends EnumEntry

object Race extends Enum[Race] {

  val values = findValues

  case object Human extends Race {
    override def toString: String = "Человек"
  }

  case object Elf extends Race {
    override def toString: String = "Человек"
  }

  case object Murloc extends Race {
    override def toString: String = "Мурлок"
  }

  case object Orc extends Race {
    override def toString: String = "Орк"
  }

  case object Goblin extends Race {
    override def toString: String = "Гоблин"
  }

  case object Demon extends Race {
    override def toString: String = "Демон"
  }

  case object Gnome extends Race {
    override def toString: String = "Гном"
  }

  case object Khajiit extends Race {
    override def toString: String = "Каджит"
  }

}
