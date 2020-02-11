package postman
import play.api.libs.json.Json

import scala.util.Failure
import scala.util.Success
import scala.util.Try

object PostmanParser extends App {
  val postman_collections = "./resources/Postman_Echo.postman_collection.json"
  val jsonifiedString = scala.io.Source.fromFile(postman_collections).mkString
  val postman_collections_json =
    Try(Json.parse(jsonifiedString)).map(_.as[PostmanSchema])

  postman_collections_json match {
    case Success(myValue) => // compare your case class here

      println(myValue.info._postman_id)
      println(myValue.item(0).item(0).response(0).originalRequest)

    case Failure(err) =>
      println("none")

      throw new Exception("couldnt parse: ", err)
  }

}
