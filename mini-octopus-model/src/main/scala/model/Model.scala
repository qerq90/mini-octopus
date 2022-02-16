package model

import zio.json._

object Model {
  case class UserId(value: Int) extends AnyVal

  implicit val UserIdDecoder: JsonDecoder[UserId] =
    JsonDecoder[Int].map(UserId)
}
