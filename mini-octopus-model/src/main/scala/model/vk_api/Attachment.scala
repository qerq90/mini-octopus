package model.vk_api

case class Attachment(`type`: AttachmentType, ownerId: String, id: String) {
  override def toString: String = s"${`type`}${ownerId}_$id"
}
