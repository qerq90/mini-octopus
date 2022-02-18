package core.game.creatures

import model.{Race, Rarity, Status}

case class Mob(
    name: String,
    rarity: Rarity,
    lvl: Int,
    race: Race,
    var hp: Int,
    var maxHp: Int,
    var attack: Int,
    var status: Status)
    extends Creature {

  override def toString: String =
    s"Mob $name has $hp hp and $attack attack and is $status"
}
