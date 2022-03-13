package dao

import core.game.creatures.Hero
import doobie.hikari.HikariTransactor
import zio.{Has, Task, ZIO, ZLayer}

object mainDao {
  type Env = Has[mainDao]

  trait mainDao {
    def saveUserHero(hero: Hero): Task[Unit]
  }

  def saveUserHero(hero: Hero): ZIO[Env, Throwable, Unit] =
    ZIO.accessM(_.get.saveUserHero(hero))

  val live: ZLayer[Has[HikariTransactor[Task]], Nothing, Env] =
    ZLayer.fromService[HikariTransactor[Task], mainDao](mainDaoPg)
}
