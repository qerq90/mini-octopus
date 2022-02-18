package core.game.creatures

import model.Model.UserId
import model.Race
import model.Status.Alive

object HeroGenerator {

  def createNewHero(id: UserId, name: String, race: Race): Hero = {
    Hero(
      id = id,
      name = name,
      strength = 4,
      vitality = 4,
      intelligence = 4,
      agility = 4,
      lvl = 1,
      race = race,
      hp = 0,
      maxHp = 0,
      attack = 0,
      status = Alive)
  }
}
