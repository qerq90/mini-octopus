package core.vk_api

import model.Model.UserId
import model.config.VkConfig
import sttp.client3._
import sttp.client3.asynchttpclient.zio.SttpClient
import scala.util.Random
import zio.{Has, IO, ZIO, ZLayer}

object VkApi {
  type Env = Has[VkApi]

  trait VkApi {
    val apiUrl = uri"https://api.vk.com/method/"
    val version = Map("v" -> "5.131")

    def sendMessage(message: String, userId: UserId): IO[Throwable, String]
  }

  final case class VkApiLive(sttp: SttpClient.Service, config: VkConfig) extends VkApi {
    private val token = config.token

    override def sendMessage(message: String, userId: UserId): IO[Throwable, String] = {
      val url = apiUrl + "messages.send"
      val reqBody = Map(
        "message" -> message,
        "user_id" -> userId.value.toString,
        "access_token" -> token,
        "random_id" -> randomId
      ) ++ version

      for {
        response <- sttp.send(basicRequest.post(uri"$url").body(reqBody))
        res <- ZIO.fromEither(response.body).mapError(new Error(_))
      } yield res
    }

    private def randomId = Random.between(Int.MinValue, Int.MaxValue).toString
  }

  def sendMessage(message: String, userId: UserId): ZIO[Env, Throwable, String] =
    ZIO.accessM(_.get.sendMessage(message, userId))

  val live: ZLayer[Has[SttpClient.Service] with Has[VkConfig], Nothing, Env] =
    ZLayer.fromServices[SttpClient.Service, VkConfig, VkApi](
      VkApiLive
    )
}
