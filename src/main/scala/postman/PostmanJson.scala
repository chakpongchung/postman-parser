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

case class Event(listen: String, script: Script)

object Event {
  implicit val format: Format[Event] = Json.format
}

case class Info(_postman_id: String,
                name: String,
                description: String,
                schema: String)

object Info {
  implicit val format: Format[Info] = Json.format
}

case class Item(name: String, item: Seq[Item1], description: Option[String])

object Item {
  implicit val format: Format[Item] = Json.format
}
//most complex part,
// first Request, TODO
// then Response, TODO
case class Item1(
  name: String,
  event: Seq[Event],
  request: Request,
  response: Seq[Response] //"response": [], cannot be Seq[Option[Response]], why?
)

object Item1 {
  implicit val format: Format[Item1] = Json.format
}

case class Request(method: String,
                   header: Seq[Header], // header: []
                   url: Option[Url] /*= None*/,
                   body: Option[Body],
                   description: Option[String] //String= ""
)

object Request {
  implicit val format: Format[Request] =
    Json.using[Json.WithDefaultValues].format
}

case class Response(name: String,
                    originalRequest: OriginalRequest,
                    status: String,
                    code: Int,
                    _postman_previewlanguage: String,
                    header: Seq[Header],
                    cookie: Seq[Cookie],
                    body: String)

object Response {
  implicit val format: Format[Response] = Json.format
}

case class Cookie(expires: String,
                  hostOnly: Option[Boolean],
                  httpOnly: Boolean,
                  domain: String,
                  path: String,
                  secure: Boolean,
                  value: String,
                  key: String)

object Cookie {
  implicit val format: Format[Cookie] = Json.format
}

case class OriginalRequest(method: Option[String],
                           header: Seq[Header],
                           url: Url)

object OriginalRequest {
  implicit val format: Format[OriginalRequest] = Json.format
}

case class Body(mode: String,
                raw: Option[String],
                urlencoded: Option[Seq[Urlendoded]])

object Body {
  implicit val format: Format[Body] = Json.format
}

case class Urlendoded(key: String, value: String, `type`: String)

object Urlendoded {
  implicit val format: Format[Urlendoded] = Json.format
}

case class Header(key: String, value: String)

object Header {
  implicit val format: Format[Header] = Json.format
}

case class RootInterface(info: Info, item: Seq[Item])

object RootInterface {
  implicit val format: Format[RootInterface] = Json.format
}

case class Script(
                  //                   id: String,
                  exec: Seq[String],
                  `type`: String)

object Script {
  implicit val format: Format[Script] = Json.format
}

case class Url(raw: String,
               protocol: Option[String],
               host: Option[Seq[String]],
               path: Option[Seq[String]],
               query: Option[Seq[Query]])

object Url {
  implicit val format: Format[Url] = Json.format
}

case class Query(key: String, value: Option[String] = null)

object Query {
  implicit val format: Format[Query] = Json.format
}

object PathAdditions {
  implicit class PathAdditions(path: JsPath) {

    def readNullableIterable[A <: Iterable[_]](
      implicit reads: Reads[A]
    ): Reads[A] =
      Reads(
        (json: JsValue) =>
          path
            .applyTillLast(json)
            .fold(
              error => error,
              result =>
                result.fold(invalid = (_) => reads.reads(JsArray()), valid = {
                  case JsNull => reads.reads(JsArray())
                  case js     => reads.reads(js).repath(path)
                })
          )
      )

    def writeNullableIterable[A <: Iterable[_]](
      implicit writes: Writes[A]
    ): OWrites[A] =
      OWrites[A] { (a: A) =>
        if (a.isEmpty) Json.obj()
        else JsPath.createObj(path -> writes.writes(a))
      }

    /** When writing it ignores the property when the collection is empty,
      * when reading undefined and empty jsarray becomes an empty collection */
    def formatNullableIterable[A <: Iterable[_]](
      implicit format: Format[A]
    ): OFormat[A] =
      OFormat[A](
        r = readNullableIterable(format),
        w = writeNullableIterable(format)
      )

  }
}
