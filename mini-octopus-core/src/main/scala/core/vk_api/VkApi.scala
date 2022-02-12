package core.vk_api

import core.vk_api.response.VkMessageSendResponse
import model.Model.UserId
import model.config.VkConfig
import sttp.client3._
import sttp.client3.asynchttpclient.zio.SttpClient
import zio.{Has, IO, ZIO, ZLayer}

object VkApi {
  type Env = Has[VkApi]

  trait VkApi {
    val apiUrl = uri"https://api.vk.com/method/"
    val version = Map("v" -> "5.131")

    def sendMessage(
        message: String,
        userId: UserId): IO[VkApiError, VkMessageSendResponse]
  }

  def sendMessage(
      message: String,
      userId: UserId): ZIO[Env, VkApiError, VkMessageSendResponse] =
    ZIO.accessM(_.get.sendMessage(message, userId))

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
