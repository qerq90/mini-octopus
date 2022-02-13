package core.game.creatures.CreatureTest

import core.game.creatures.Creature.DummyBasicCreature
import model.Status.{Alive, Dead}
import org.scalatest.FunSuite

class CreatureSpec extends FunSuite {
  test("Creature actually takes dmg from while getting damaged") {
    val creature = DummyBasicCreature(1, 20, 100, 100, Alive)
    creature.getDamaged(20)
    assert(creature.hp == 80)
  }

  test("Hp of creature can't go under 0 after getting damaged") {
    val creature = DummyBasicCreature(1, 20, 100, 100, Alive)
    creature.getDamaged(creature.maxHp + 1)
    assert(creature.hp == 0)
  }

  test("Creature actually gets healead") {
    val creature = DummyBasicCreature(1, 20, 50, 100, Alive)
    creature.getHealed(20)
    assert(creature.hp == 70)
  }

  test("Hp of creature can't go over max hp after getting healed") {
    val creature = DummyBasicCreature(1, 20, 100, 100, Alive)
    creature.getHealed(1)
    assert(creature.hp == creature.maxHp)
  }

  test("Creature should die, if its hp got to 0") {
    val creature = DummyBasicCreature(1, 20, 100, 100, Alive)
    assert(creature.getActualStatus == Alive)
    creature.getDamaged(Int.MaxValue)
    assert(creature.getActualStatus == Dead)
  }

}
