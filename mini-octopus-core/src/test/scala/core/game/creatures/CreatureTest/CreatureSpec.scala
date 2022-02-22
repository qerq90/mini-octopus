package core.game.creatures.CreatureTest

import core.game.creatures.Creature
import core.game.model.Model
import core.game.model.Model.CreatureId
import model.Race.Human
import model.{Race, Status}
import model.Status._
import org.scalatest.FunSuite

class CreatureSpec extends FunSuite {

  private def getCreature(
      lvlCreature: Int = 1,
      atkCreature: Int = 20,
      hpCreature: Int = 100,
      maxHpCreature: Int = 100,
      statusCreature: Status = Alive,
      raceCreature: Race = Human
  ): Creature = new Creature {
    override val id: Model.CreatureId = CreatureId(1)
    override val lvl: Int = lvlCreature
    override val race: Race = raceCreature
    override var attack: Int = atkCreature
    override var hp: Int = hpCreature
    override var maxHp: Int = maxHpCreature
    override var status: Status = statusCreature
  }

  test("Creature actually takes dmg from while getting damaged") {
    val creature = getCreature()
    creature.getDamaged(20)
    assert(creature.hp == 80)
  }

  test("Hp of creature can't go under 0 after getting damaged") {
    val creature = getCreature()
    creature.getDamaged(creature.maxHp + 1)
    assert(creature.hp == 0)
  }

  test("Creature actually gets healead") {
    val creature = getCreature(hpCreature = 50)
    creature.getHealed(20)
    assert(creature.hp == 70)
  }

  test("Hp of creature can't go over max hp after getting healed") {
    val creature = getCreature()
    creature.getHealed(1)
    assert(creature.hp == creature.maxHp)
  }

  test("Creature should die, if its hp got to 0") {
    val creature = getCreature()
    assert(creature.getActualStatus == Alive)
    creature.getDamaged(Int.MaxValue)
    assert(creature.getActualStatus == Dead)
  }

}
