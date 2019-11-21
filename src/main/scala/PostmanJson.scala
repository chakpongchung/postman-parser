package postman

import play.api.libs.json.{Format, Json}

//class PostmanJson {
//
//
//}

case class Event(
                  listen: String,
                  script: Script
                )

object Event {
  implicit val format: Format[Event] = Json.format
}

case class Info(
                 _postman_id: String,
                 name: String,
                 description: String,
                 schema: String
               )

object Info {
  implicit val format: Format[Info] = Json.format
}

case class Item(
                 name: String,
                 item: Seq[Item1],
                 description: Option[String]
               )

object Item {
  implicit val format: Format[Item] = Json.format
}

case class Item1(
                  name: String,
                  event: Seq[Event],
                  request: Request,
                  response: Seq[String] //"response": []
                )

object Item1 {
  implicit val format: Format[Item1] = Json.format
}

case class Request(
                    method: String,
                    header: Seq[String], // header: []
                    url: Url,
                    description: String
                  )
object Request {
  implicit val format: Format[Request] = Json.format
}

case class RootInterface(
                          info: Info,
                          item: Seq[Item]
                        )

object RootInterface {
  implicit val format: Format[RootInterface] = Json.format
}

case class Script(
                   _type: String,
                   exec: Seq[String]
                 )

object Script {
  implicit val format: Format[Script] = Json.format
}
case class Url(
                raw: String,
                protocol: String,
                host: Seq[String],
                path: Seq[String]
              )


object Url {
  implicit val format: Format[Url] = Json.format
}

