package core.game.creatures

import model.{Race, Status}

case class Mob(
    name: String,
    override val lvl: Int,
    override val race: Race,
    override var hp: Int,
    override var attack: Int,
    override var maxHp: Int,
    override var status: Status)
    extends Creature {

  override def toString: String =
    s"Mob $name has $hp hp and $attack attack and is $status"
}
