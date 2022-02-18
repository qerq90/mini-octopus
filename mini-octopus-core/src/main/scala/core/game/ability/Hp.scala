package core.game.ability

trait Hp {
  var hp: Int
  var maxHp: Int

  def getDamaged(damage: Int): Unit = hp - damage match {
    case damagedHp if damagedHp <= 0 => hp = 0
    case damagedHp => hp = damagedHp
  }

  def getHealed(heal: Int): Unit = heal + hp match {
    case healedHp if healedHp > maxHp => hp = maxHp
    case healedHp => hp = healedHp
  }
}
