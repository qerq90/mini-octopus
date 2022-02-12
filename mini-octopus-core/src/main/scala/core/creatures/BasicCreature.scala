package core.creatures

import model.Status

trait Creature {
  var hp: Int
  var maxHp: Int
  var attack: Int
  var status: Status

  def getDamaged(damage: Int): Unit = hp -= damage

  def getHealed(heal: Int): Unit = heal + hp match {
    case healedHp if healedHp > maxHp => hp = maxHp
    case healedHp => hp = healedHp
  }

  def getActualStatus = hp match {
    case hp if hp <= 0 => status = Status.Dead; status
    case _ => status
  }
}
