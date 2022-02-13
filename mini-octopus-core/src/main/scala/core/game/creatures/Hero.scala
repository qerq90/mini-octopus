package core.game.creatures

import model.Model.UserId
import model.Status

case class Hero(
    id: UserId,
    name: String,
    override var lvl: Int,
    override var hp: Int,
    override var maxHp: Int,
    override var attack: Int,
    override var status: Status
) extends Creature {

  override def toString = s"$name have $hp hp and $attack atk and is $status"
}

object Hero {

  def apply(
      userId: UserId,
      name: String,
      lvl: Int,
      hp: Int,
      attack: Int): Hero =
    new Hero(userId, name, lvl, hp, hp, attack, Status.Alive)
}
