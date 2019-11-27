// Copyright (C) 2011-2012 the original author or authors.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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
                description: Option[String],
                schema: String)

object Info {
  implicit val format: Format[Info] = Json.format
}

case class Item(name: String,
                item: Option[Seq[Item1]],
                description: Option[String])

object Item {
  implicit val format: Format[Item] = Json.format
}
//most complex part,
// first Request, TODO
// then Response, TODO
case class Item1(
  name: String,
  event: Option[Seq[Event]],
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
