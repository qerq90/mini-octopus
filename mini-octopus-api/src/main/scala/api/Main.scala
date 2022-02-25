package api

import core.config.Config
import core.vk_api.VkApi
import core.vk_api.VkApi.UnknownVkError
import model.Model.UserId
import model.config.VkConfig
import sttp.client3.asynchttpclient.zio.AsyncHttpClientZioBackend
import zio._
import zio.config.syntax.ZIOConfigNarrowOps
import zio.magic._

case class MainConfig(vk: VkConfig)

object Main extends App {

  type Env = VkApi.Env

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {

    (for {
      res <- VkApi
        .getUser(List(UserId(51422811)))
        .catchSome { case e: UnknownVkError =>
          ZIO(e.cause.getMessage)
        }
      _ <- ZIO.succeed(println(res))
    } yield ()).exitCode.provideCustomLayer(makeEnv).orDie
  }

  def makeEnv: ZLayer[Any, Throwable, Env] = ZLayer.wire[Env](
    AsyncHttpClientZioBackend.layer(),
    Config.makeConfig[MainConfig].narrow(_.vk),
    VkApi.live
  )
}
