package core.vk_api.response

import zio.json._

sealed trait VkMessageSendResponse

final case class Response(response: Int)
  extends VkMessageSendResponse

object VkMessageSendResponse {

  implicit val decoder: JsonDecoder[VkMessageSendResponse] = DeriveJsonDecoder.gen[Response]
    .orElse(DeriveJsonDecoder.gen[VkMessageSendResponse])
}
