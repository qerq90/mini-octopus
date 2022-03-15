package api.routes

import model.vk_api.request.{VkChecking, VkEvent}
import zhttp.http._
import zio._
import zio.json._

object Router {

  val routes: Http[Any, Throwable, Request, Response] =
    Http.collectZIO[Request] {
      case req @ Method.POST -> _ =>
        for {
          body <- req.bodyAsString
          json = body.fromJson[VkChecking]
          response = json match {
            case Right(v)
                if v.`type` == "confirmation" && v.groupId == 171104414 =>
              "beeb21d7"
            case _ =>
              val json = body.fromJson[VkEvent]
              json match {
                case Right(v) => println(s"\nBody:\n$json\n")
                //EventHandler.handle(v)
                case _ =>
                  println(s"\nError while parsing:\nBody:\n$body\n")
              }
              "ok"
          }
        } yield Response.text(response)

      case Method.GET -> _ =>
        UIO(Response.text("Ok"))
    }
}
