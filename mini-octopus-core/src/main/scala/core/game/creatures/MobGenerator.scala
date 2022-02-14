package core.game.creatures

import core.util.RichSeq.RichSeq
import model.Race.Human
import model.Rarity.Common
import model.Status.Alive
import model.{Race, Rarity}

import scala.util.Random

object MobGenerator {

  /** Function to create mob by lvl
    * Rarity and race will be randomized
    */
  def createMob(lvl: Int): Mob = {
    val race = Race.values.random.getOrElse(Human)
    createMob(lvl, race)
  }

  /** Function to create mob by lvl and race
    * Rarity and name will be randomized
    */
  def createMob(lvl: Int, race: Race): Mob = {
    val rarity = Rarity.values.random.getOrElse(Common)
    createMob(lvl, race, rarity)
  }

  /** Function to create mob by lvl and rarity
    * Race and name will be randomized
    */
  def createMob(lvl: Int, rarity: Rarity): Mob = {
    val race = Race.values.random.getOrElse(Human)
    createMob(lvl, race, rarity)
  }

  /** Function to create mob by lvl, rarity and race
    * Name will be randomized
    */
  def createMob(lvl: Int, race: Race, rarity: Rarity): Mob = {
    val name = MobNameGenerator.generateName(race, rarity)
    createMob(lvl, name, race, rarity)
  }

  /** Function to create mob */
  def createMob(lvl: Int, name: String, race: Race, rarity: Rarity): Mob = {
    val hp = (32 * 5 * lvl * rarity.factor * race.factor.hpFactor * Random
      .between(0.8, 1.2)).toInt
    val atk = (16 * 5 * lvl * rarity.factor * race.factor.attackFactor * Random
      .between(0.8, 1.2)).toInt
    val maxHp = hp

    Mob(name, rarity, lvl, race, hp, maxHp, atk, Alive)
  }
}
