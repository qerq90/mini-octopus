package core.util.doobie

import cats.effect.Blocker
import doobie.hikari.HikariTransactor
import model.config.PostgresConfig
import zio.blocking._
import zio._
import zio.interop.catz._

object Transactor {

  private def makeHikariTransactor: ZManaged[Blocking with Has[PostgresConfig], Throwable, HikariTransactor[Task]] =
    for {
      rt <- ZIO.runtime[Any].toManaged_
      be <- zio.blocking.blockingExecutor.toManaged_
      config <- ZIO.service[PostgresConfig].toManaged_
      xa <- HikariTransactor
        .newHikariTransactor[Task](
          config.driver,
          config.url,
          config.user,
          config.password,
          rt.platform.executor.asEC,
          Blocker.liftExecutionContext(be.asEC)
        )
        .toManagedZIO
    } yield xa

  def makeLayer: ZLayer[Blocking with Has[PostgresConfig], Nothing, Has[HikariTransactor[Task]]] =
    ZLayer.fromManaged(makeHikariTransactor).orDie

}
