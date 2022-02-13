package core.game.creatures.CreatureTest

import core.game.creatures.Creature.DummyBasicCreature
import model.Race.Human
import model.{Race, Status}
import model.Status._
import org.scalatest.FunSuite

class CreatureSpec extends FunSuite {

  private def getDummyCreature(
      lvl: Int = 1,
      atk: Int = 20,
      hp: Int = 100,
      maxHp: Int = 100,
      status: Status = Alive,
      race: Race = Human
  ): DummyBasicCreature =
    DummyBasicCreature(lvl, atk, hp, maxHp, status, race)

  test("Creature actually takes dmg from while getting damaged") {
    val creature = getDummyCreature()
    creature.getDamaged(20)
    assert(creature.hp == 80)
  }

  test("Hp of creature can't go under 0 after getting damaged") {
    val creature = getDummyCreature()
    creature.getDamaged(creature.maxHp + 1)
    assert(creature.hp == 0)
  }

  test("Creature actually gets healead") {
    val creature = getDummyCreature(hp = 50)
    creature.getHealed(20)
    assert(creature.hp == 70)
  }

  test("Hp of creature can't go over max hp after getting healed") {
    val creature = getDummyCreature()
    creature.getHealed(1)
    assert(creature.hp == creature.maxHp)
  }

  test("Creature should die, if its hp got to 0") {
    val creature = getDummyCreature()
    assert(creature.getActualStatus == Alive)
    creature.getDamaged(Int.MaxValue)
    assert(creature.getActualStatus == Dead)
  }

}
