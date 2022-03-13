package dao

import core.game.creatures.Hero
import doobie.implicits._
import zio.interop.catz._
import doobie.util.transactor.Transactor
import zio.Task

final case class MainDaoPg(xa: Transactor[Task]) extends MainDao.MainDao {

  override def saveUserHero(hero: Hero): Task[Unit] = {
    sql"""INSERT INTO heros
          (name, strength, vitality, intelligence, agility, id, lvl, race, hp, maxHp, attack, status)
         VALUES (
         ${hero.name},
         ${hero.strength},
         ${hero.vitality},
         ${hero.intelligence},
         ${hero.agility},
         ${hero.id.value.toString},
         ${hero.lvl},
         ${hero.race.toString},
         ${hero.hp},
         ${hero.maxHp},
         ${hero.attack},
         ${hero.status.toString}
         )""".update.run.transact(xa).unit
  }
}
