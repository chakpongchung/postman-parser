package postman

import play.api.libs.json.Format
import play.api.libs.json.JsArray
import play.api.libs.json.JsNull
import play.api.libs.json.JsPath
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.OFormat
import play.api.libs.json.OWrites
import play.api.libs.json.Reads
import play.api.libs.json.Writes
import play.api.libs.functional.syntax._
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
                  request: Request//,
                 // response: Seq[String] //"response": []
                )

object Item1 {
  implicit val format: Format[Item1] = Json.format
}

case class Request(
                    method: String,
                    header: Seq[HeaderItem], // header: []
                    url: Option[Url] = None,
                    description: String = ""
                  )
object Request {
  implicit val format: Format[Request] = Json.using[Json.WithDefaultValues].format


}

case class HeaderItem(key: String, value: String)

object HeaderItem {
  implicit val format: Format[HeaderItem] = Json.format
}

case class RootInterface(
                          info: Info,
                          item: Seq[Item]
                        )

object RootInterface {
  implicit val format: Format[RootInterface] = Json.format
}

case class Script(
                   `type`: String,
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

object PathAdditions {
  implicit class PathAdditions(path: JsPath) {

    def readNullableIterable[A <: Iterable[_]](implicit reads: Reads[A]): Reads[A] =
      Reads((json: JsValue) => path.applyTillLast(json).fold(
        error => error,
        result => result.fold(
          invalid = (_) => reads.reads(JsArray()),
          valid = {
            case JsNull => reads.reads(JsArray())
            case js => reads.reads(js).repath(path)
          })
      ))

    def writeNullableIterable[A <: Iterable[_]](implicit writes: Writes[A]): OWrites[A] =
      OWrites[A]{ (a: A) =>
        if (a.isEmpty) Json.obj()
        else JsPath.createObj(path -> writes.writes(a))
      }

    /** When writing it ignores the property when the collection is empty,
     * when reading undefined and empty jsarray becomes an empty collection */
    def formatNullableIterable[A <: Iterable[_]](implicit format: Format[A]): OFormat[A] =
      OFormat[A](r = readNullableIterable(format), w = writeNullableIterable(format))

  }
}

