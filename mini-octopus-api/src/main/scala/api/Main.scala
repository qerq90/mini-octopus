package api

import core.config.Config
import zio._

case class Port(port: Int)

object Main extends App {
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {
    // Example of config usage
    val port = Config.makeConfig[Port]
    (for {
      port <- ZIO.service[Port]
      _ <- ZIO.succeed(println(port))
    } yield ()).exitCode.provideCustomLayer(port)
  }
}
