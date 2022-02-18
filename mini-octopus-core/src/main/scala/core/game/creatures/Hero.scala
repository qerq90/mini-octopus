package core.game.creatures

import model.Model.UserId
import model.{Race, Status}

case class Hero(
    id: UserId,
    name: String,
    strength: Int,
    vitality: Int,
    intelligence: Int,
    agility: Int,
    lvl: Int,
    race: Race,
    var hp: Int,
    var maxHp: Int,
    var attack: Int,
    var status: Status
) extends Creature {

  override def toString = s"$name have $hp hp and $attack atk and is $status"
}
