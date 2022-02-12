package core.creatures

import model.Model.UserId
import model.Status

case class Hero(
    id: UserId,
    name: String,
    override var hp: Int,
    override var maxHp: Int,
    override var attack: Int,
    override var status: Status
) extends Creature {
  override def toString = s"$name have $hp hp and $attack atk and is $status"
}

object Hero {

  def apply(hp: Int, attack: Int, name: String): Hero =
    //!TODO add normal id generation
    Hero(UserId(1), name, hp, hp, attack, Status.Alive)
}
