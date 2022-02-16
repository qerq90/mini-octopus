package api

import core.config.Config
import core.vk_api.VkApi
import core.vk_api.VkApi.UnknownVkError
import model.Model.UserId
import model.config.VkConfig
import sttp.client3.asynchttpclient.zio.AsyncHttpClientZioBackend
import zio._
import zio.config.syntax.ZIOConfigNarrowOps

case class MainConfig(vk: VkConfig)

object Main extends App {

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {

    val main: Layer[Throwable, VkApi.Env] =
      (AsyncHttpClientZioBackend
        .layer() ++ Config.makeConfig[MainConfig].narrow(_.vk))
        .>>>(VkApi.live)

    (for {
      res <- VkApi
        .getUser(List(UserId(51422811)))
        .catchSome { case e: UnknownVkError =>
          ZIO(e.cause.getMessage)
        }
      _ <- ZIO.succeed(println(res))
    } yield ()).exitCode.provideCustomLayer(main).orDie
  }
}
