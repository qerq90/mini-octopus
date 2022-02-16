package core.vk_api

import model.Model.UserId
import model.config.VkConfig
import model.vk_api.Attachment
import model.vk_api.response.{VkMessageSendResponse, VkUserGetResponse}
import sttp.client3._
import sttp.client3.asynchttpclient.zio.SttpClient
import zio.{Has, IO, ZIO, ZLayer}

object VkApi {
  type Env = Has[VkApi]

  trait VkApi {
    val apiUrl = uri"https://api.vk.com/method/"

    def getUser(ids: List[UserId]): IO[VkApiError, VkUserGetResponse]

    def sendMessage(
        message: String,
        userId: UserId,
        attachments: List[Attachment]
    ): IO[VkApiError, VkMessageSendResponse]

    def sendMessage(
        message: String,
        userId: UserId): IO[VkApiError, VkMessageSendResponse]
  }

  def sendMessage(
      message: String,
      userId: UserId): ZIO[Env, VkApiError, VkMessageSendResponse] =
    ZIO.accessM(_.get.sendMessage(message, userId))

  def sendMessage(
      message: String,
      userId: UserId,
      attachments: List[Attachment]
  ): ZIO[Env, VkApiError, VkMessageSendResponse] =
    ZIO.accessM(_.get.sendMessage(message, userId, attachments))

  def getUser(ids: List[UserId]): ZIO[Env, VkApiError, VkUserGetResponse] =
    ZIO.accessM(_.get.getUser(ids))

  val live: ZLayer[Has[SttpClient.Service] with Has[VkConfig], Nothing, Env] =
    ZLayer.fromServices[SttpClient.Service, VkConfig, VkApi](
      VkApiLive
    )

  // for future errors in api
  sealed abstract class VkApiError(message: String, cause: Option[Throwable])
      extends RuntimeException(message, cause.orNull)

  final case class UnknownVkError(cause: Throwable)
      extends VkApiError("Unknown error occured", Some(cause))
}
