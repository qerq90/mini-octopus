package api

import api.routes.Router
import core.config.Config
import core.util.doobie.Transactor
import core.vk_api.VkApi
import model.config.{PostgresConfig, ServerConfig, VkConfig}
import sttp.client3.asynchttpclient.zio.AsyncHttpClientZioBackend
import zhttp.http.Http
import zio._
import zio.config.syntax.ZIOConfigNarrowOps
import zio.magic._
import zhttp.service.server.ServerChannelFactory
import zhttp.service.{EventLoopGroup, Server, ServerChannelFactory}

case class MainConfig(
    vk: VkConfig,
    server: ServerConfig,
    postgres: PostgresConfig)

object Main extends App {

  type Env = VkApi.Env
    with ServerChannelFactory
    with EventLoopGroup
    with Has[ServerConfig]

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    makeServer
      .flatMap(_.make.useForever)
      .provideCustomLayer(makeEnv)
      .exitCode

  val nThreads = 10

  val makeServer: ZIO[Has[ServerConfig], Nothing, Server[Any, Nothing]] =
    ZIO.access(serverConfig =>
      Server.port(serverConfig.get.port) ++
        Server.app(
          Router.routes
            .catchAll(_ => Http.text("Error occured"))
        ))

  val config: ZLayer[Any, Throwable, Has[MainConfig]] =
    Config.makeConfig[MainConfig]

  def makeEnv: ZLayer[Any, Throwable, Env] =
    ZLayer.wire[Env](
      AsyncHttpClientZioBackend.layer(),
      config.narrow(_.server),
      config.narrow(_.vk),
      config.narrow(_.postgres),
      Transactor.makeLayer,
      VkApi.live,
      ServerChannelFactory.auto,
      EventLoopGroup.auto(nThreads)
    )
}
