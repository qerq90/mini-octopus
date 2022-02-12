package core.vk_api.response

import zio.json._

sealed trait VkMessageSendResponse

final case class Response(response: Int) extends VkMessageSendResponse

@jsonHint("error")
final case class Error(
    @jsonField("error_code") errorCode: Int,
    @jsonField("error_msg") errorMessage: String,
    @jsonField("request_params") reqParams: List[Map[String, String]])
    extends VkMessageSendResponse

object VkMessageSendResponse {

  // Dont have a better solution to parsing "{'response': 1234}"
  // while still retaining ADT deriving
  implicit val decoder: JsonDecoder[VkMessageSendResponse] = DeriveJsonDecoder
    .gen[Response]
    .orElse(DeriveJsonDecoder.gen[VkMessageSendResponse])
}
