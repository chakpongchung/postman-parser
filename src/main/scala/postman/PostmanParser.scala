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
import play.api.libs.json.Json

import scala.util.Failure
import scala.util.Success
import scala.util.Try

object PostmanParser extends App {
//  val PostmanCollections = "./resources/Postman_Echo.postman_collection.json"
  val PostmanCollections = "./resources/testing.json"

  val JsonifiedString = scala.io.Source.fromFile(PostmanCollections).mkString
  val PostmanCollectionsJson =
    Try(Json.parse(JsonifiedString)).map(_.as[RootInterface])

  PostmanCollectionsJson match {
    case Success(parsedJson) => // compare your case class here

      println(parsedJson.info._postman_id)
//      println(myValue.item(0).item(0).response(0).originalRequest)

      parsedJson.item(0).item match {
        case Some(it) =>
          it(0).request.url match {
            case Some(url) =>
              println(url.raw)
          }
      }

    case Failure(err) =>
      println("none")

      throw new Exception("couldnt parse: ", err)
  }

}
