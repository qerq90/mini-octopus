package model.vk_api.request

import zio.json.{jsonField, DeriveJsonDecoder, JsonDecoder}

case class VkEvent(`type`: String, `object`: ObjectMessage)

case class ObjectMessage(message: Message)

case class Message(text: String, @jsonField("peer_id") peerId: Long)

object VkEvent {

  implicit val decoder: JsonDecoder[VkEvent] =
    DeriveJsonDecoder.gen[VkEvent]
}

object ObjectMessage {

  implicit val decoder: JsonDecoder[ObjectMessage] =
    DeriveJsonDecoder.gen[ObjectMessage]
}

object Message {

  implicit val decoder: JsonDecoder[Message] =
    DeriveJsonDecoder.gen[Message]
}
