package core.game.creatures

import model.Status

case class Mob(
    name: String,
    override var lvl: Int,
    override var hp: Int,
    override var attack: Int,
    override var maxHp: Int,
    override var status: Status)
    extends Creature {

  override def toString: String =
    s"Mob $name has $hp hp and $attack attack and is $status"
}
