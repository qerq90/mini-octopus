package core.vk_api

import core.vk_api.VkApi.{UnknownVkError, VkApi, VkApiError}
import model.Model.UserId
import model.config.VkConfig
import model.vk_api.Attachment
import model.vk_api.response.{VkMessageSendResponse, VkUserGetResponse}
import sttp.client3.asynchttpclient.zio.SttpClient
import sttp.client3.{basicRequest, UriContext}
import zio.json._
import zio.{IO, ZIO}

import scala.util.Random

final case class VkApiLive(sttp: SttpClient.Service, config: VkConfig)
    extends VkApi {
  private val token = config.token

  override def sendMessage(
      message: String,
      userId: UserId,
      attachments: List[Attachment]): IO[VkApiError, VkMessageSendResponse] = {
    val url = apiUrl + "messages.send"
    val reqBody = Map(
      "message" -> message,
      "user_id" -> userId.value.toString,
      "access_token" -> token,
      "random_id" -> randomId,
      "attachment" -> attachments.mkString(",")
    ) ++ version

    for {
      response <- sttp
        .send(basicRequest.post(uri"$url").body(reqBody))
        .mapError(UnknownVkError)
      res <- ZIO
        .fromEither(response.body)
        .map(_.fromJson[VkMessageSendResponse])
        .mapVkError
      answer <- ZIO.fromEither(res).mapVkError
    } yield answer
  }

  override def sendMessage(
      message: String,
      userId: UserId): IO[VkApiError, VkMessageSendResponse] = {
    sendMessage(message, userId, List())
  }

  override def getUser(ids: List[UserId]): IO[VkApiError, VkUserGetResponse] = {
    val url = apiUrl + "users.get"
    val reqBody = Map(
      "user_ids" -> ids.map(_.value).mkString(","),
      "access_token" -> token
    ) ++ version

    for {
      response <- sttp
        .send(basicRequest.post(uri"$url").body(reqBody))
        .mapError(UnknownVkError)
      res <- ZIO
        .fromEither(response.body)
        .map(_.fromJson[VkUserGetResponse])
        .mapVkError
      answer <- ZIO.fromEither(res).mapVkError
    } yield answer
  }

  private def randomId = Random.between(Int.MinValue, Int.MaxValue).toString

  implicit private class RichZIO[-R, +A](z: ZIO[R, String, A]) {

    def mapVkError: ZIO[R, UnknownVkError, A] =
      z.mapError(errorString =>
        UnknownVkError(new RuntimeException(errorString)))
  }
}
