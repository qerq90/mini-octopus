package dao

import core.game.creatures.Hero
import zio.Task

trait mainDao {
  def saveUserHero(hero: Hero): Task[Unit]
}
