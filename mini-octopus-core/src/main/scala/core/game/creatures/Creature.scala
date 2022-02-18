package core.game.creatures

import core.game.ability._
import model.{Race, Rarity, Status}

trait Creature extends Hp {
  val lvl: Int
  val race: Race

  var hp: Int
  var maxHp: Int
  var attack: Int
  var status: Status

  def getActualStatus: Status = hp match {
    case hp if hp <= 0 => status = Status.Dead; status
    case _ => status
  }
}
