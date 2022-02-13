package core.game.creatures

import model.Status

trait Creature {
  var lvl: Int
  var hp: Int
  var maxHp: Int
  var attack: Int
  var status: Status

  def getDamaged(damage: Int): Unit = hp - damage match {
    case damagedHp if damagedHp <= 0 => hp = 0
    case damagedHp => hp = damagedHp
  }

  def getHealed(heal: Int): Unit = heal + hp match {
    case healedHp if healedHp > maxHp => hp = maxHp
    case healedHp => hp = healedHp
  }

  def getActualStatus = hp match {
    case hp if hp <= 0 => status = Status.Dead; status
    case _ => status
  }
}

object Creature {

  case class DummyBasicCreature(
      override val lvl: Int,
      override var attack: Int,
      override var hp: Int,
      override var maxHp: Int,
      override var status: Status)
      extends Creature
}
