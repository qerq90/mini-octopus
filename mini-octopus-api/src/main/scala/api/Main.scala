package api

import core.config.Config
import core.vk_api.VkApi
import model.Model.UserId
import model.config.VkConfig
import sttp.client3.asynchttpclient.zio.AsyncHttpClientZioBackend
import zio._
import zio.config.syntax.ZIOConfigNarrowOps

case class Main(vk: VkConfig)

object Main extends App {
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = {

    val main: Layer[Throwable, VkApi.Env] =
      (AsyncHttpClientZioBackend.layer() ++ Config.makeConfig[Main].narrow(_.vk))
        .>>>(VkApi.live)

    (for {
      res <- VkApi.sendMessage("Privet xyilo", UserId(51422811))
      _ <- ZIO.succeed(println(res))
    } yield ()).exitCode.provideCustomLayer(main).orDie
  }
}
