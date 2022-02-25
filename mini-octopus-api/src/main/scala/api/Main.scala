package api

import core.config.Config
import core.vk_api.VkApi
import model.config.{ServerConfig, VkConfig}
import sttp.client3.asynchttpclient.zio.AsyncHttpClientZioBackend
import zio._
import zio.config.syntax.ZIOConfigNarrowOps
import zio.magic._
import zhttp.http._
import zhttp.service.server.ServerChannelFactory
import zhttp.service.{EventLoopGroup, Server, ServerChannelFactory}

case class MainConfig(vk: VkConfig, server: ServerConfig)

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
    ZIO.accessM(serverConfig =>
      UIO(
        Server.port(serverConfig.get.port) ++
          Server.app(Http.ok)))

  val config: ZLayer[Any, Throwable, Has[MainConfig]] =
    Config.makeConfig[MainConfig]

  def makeEnv: ZLayer[Any, Throwable, Env] =
    ZLayer.wire[Env](
      AsyncHttpClientZioBackend.layer(),
      config.narrow(_.server),
      config.narrow(_.vk),
      VkApi.live,
      ServerChannelFactory.auto,
      EventLoopGroup.auto(nThreads)
    )
}
