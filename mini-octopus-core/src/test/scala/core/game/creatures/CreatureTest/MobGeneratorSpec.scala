package core.game.creatures.CreatureTest

import core.game.creatures.MobGenerator
import model.Race.Human
import model.Rarity.Common
import model.Status.Alive
import org.scalatest.FunSuite

class MobGeneratorSpec extends FunSuite {
  test("MobGenerator is generating valid mob by params") {
    val mob = MobGenerator.createMob(100, "test", Human, Common)
    assert(
      mob.lvl == 100
        && mob.status == Alive
        && mob.race == Human
        && mob.name == "test"
        && mob.rarity == Common)
  }
}
