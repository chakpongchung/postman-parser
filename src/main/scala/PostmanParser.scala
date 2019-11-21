package postman

import scala.util.{Failure, Success, Try}
import play.api.libs.json.{JsObject, JsValue, Json}

object PostmanParser extends App {
  val postman_collections = "Postman_Echo.postman_collection.json"
  val jsonifiedString = scala.io.Source.fromFile(postman_collections).mkString
  val myJsonData = Try(Json.parse(jsonifiedString)).map(_.as[RootInterface])

  myJsonData match {
    case Success(myValue) => // compare your case class here

    //      println(myValue.info._postman_id)
    case Failure(err) => println("none")

      throw new Exception("couldnt parse: ",err)
  }
}