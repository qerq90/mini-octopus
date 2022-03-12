package api.routes

import model.vk_api.request.VkChecking
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
            case _ => println(body); "ok"
          }
        } yield Response.text(response)

      case Method.GET -> _ =>
        UIO(Response.text("Ok"))
    }
}

/* {
                "type":"message_new",
                "object":{
                  "message":{
                    "date":1647049000,
                    "from_id":51422811,
                    "id":3774,
                    "out":0,
                    "attachments":[],
                    "conversation_message_id":2551,
                    "fwd_messages":[],
                    "important":false,
                    "is_hidden":false,
      !!!!!         "peer_id":51422811,
                    "random_id":0,
      !!!!!         "text":"1231"
                   },
                   "client_info":{
                    "button_actions":["text","vkpay","open_app","location","open_link","callback","intent_subscribe","intent_unsubscribe"],
                    "keyboard":true,
                    "inline_keyboard":true,
                    "carousel":true,
                    "lang_id":0
                   }
                  },
                  "group_id":171104414,
                  "event_id":"45586712573ba1939fbad3b0f37ed885781bb8a6"
                 }*/
