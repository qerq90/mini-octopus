package model.vk_api.response

import model.Model.UserId
import zio.json._

sealed trait VkUserGetResponse

final case class UserGetResponse(response: List[UserInfo])
    extends VkUserGetResponse

case class UserInfo(
    id: UserId,
    @jsonField("first_name") firstName: String,
    @jsonField("last_name") lastName: String,
    @jsonField("can_access_closed") canAccessClosed: Boolean,
    @jsonField("is_closed") isClosed: Boolean)

object VkUserGetResponse {

  implicit val decoder: JsonDecoder[VkUserGetResponse] = DeriveJsonDecoder
    .gen[UserGetResponse]
    .orElse(DeriveJsonDecoder.gen[VkUserGetResponse])

}

object UserInfo {

  implicit val decoder: JsonDecoder[UserInfo] =
    DeriveJsonDecoder.gen[UserInfo]
}
