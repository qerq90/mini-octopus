package core.vk_api

import core.vk_api.VkApi.{UnknownVkError, VkApi, VkApiError}
import core.vk_api.response.VkMessageSendResponse
import model.Model.UserId
import model.config.VkConfig
import sttp.client3.asynchttpclient.zio.SttpClient
import sttp.client3.{UriContext, basicRequest}
import zio.json._
import zio.{IO, ZIO}

import scala.util.Random

final case class VkApiLive(sttp: SttpClient.Service, config: VkConfig) extends VkApi {
  private val token = config.token

  override def sendMessage(message: String, userId: UserId): IO[VkApiError, VkMessageSendResponse] = {
    val url = apiUrl + "messages.send"
    val reqBody = Map(
      "message" -> message,
      "user_id" -> userId.value.toString,
      "access_token" -> token,
      "random_id" -> randomId
    ) ++ version

    for {
      response <- sttp.send(basicRequest.post(uri"$url").body(reqBody))
        .mapError(UnknownVkError)
      res <- ZIO.fromEither(response.body).map(_.fromJson[VkMessageSendResponse]).mapVkError
      answer <- ZIO.fromEither(res).mapVkError
    } yield answer
  }

  private def randomId = Random.between(Int.MinValue, Int.MaxValue).toString

  private implicit class RichZIO[-R, +A](z: ZIO[R, String, A]) {
    def mapVkError: ZIO[R, UnknownVkError, A] =
      z.mapError(errorString => UnknownVkError(new RuntimeException(errorString)))
  }
}