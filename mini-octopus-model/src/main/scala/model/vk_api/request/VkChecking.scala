package model.vk_api.request

import zio.json._

case class VkChecking(`type`: String, @jsonField("group_id") groupId: Long)

object VkChecking {

  implicit val decoder: JsonDecoder[VkChecking] =
    DeriveJsonDecoder.gen[VkChecking]
}
