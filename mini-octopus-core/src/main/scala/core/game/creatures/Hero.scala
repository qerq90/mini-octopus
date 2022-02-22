package core.game.creatures

import core.game.model.Model.{CreatureId}
import model.{Race, Status}

case class Hero(
    name: String,
    strength: Int,
    vitality: Int,
    intelligence: Int,
    agility: Int,
    id: CreatureId,
    lvl: Int,
    race: Race,
    var hp: Int,
    var maxHp: Int,
    var attack: Int,
    var status: Status
) extends Creature {

  override def toString = s"$name have $hp hp and $attack atk and is $status"
}
