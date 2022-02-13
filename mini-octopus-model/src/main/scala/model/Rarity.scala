package model

import enumeratum._

sealed trait Rarity extends EnumEntry

object Rarity extends Enum[Rarity] {

  val values = findValues

  case object Common extends Rarity {
    override def toString: String = "Обычный"
  }

  case object Uncommon extends Rarity {
    override def toString: String = "Необычный"
  }

  case object Rare extends Rarity {
    override def toString: String = "Редкий"
  }

  case object Mythical extends Rarity {
    override def toString: String = "Мифический"
  }

  case object Legendary extends Rarity {
    override def toString: String = "Легендарный"
  }
}
