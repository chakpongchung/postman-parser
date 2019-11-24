package postman
import play.api.libs.json.Json

import scala.util.Failure
import scala.util.Success
import scala.util.Try

object PostmanParser extends App {
  val postman_collections = "./resources/Postman_Echo.postman_collection.json"
  val jsonifiedString = scala.io.Source.fromFile(postman_collections).mkString
  val myJsonData = Try(Json.parse(jsonifiedString)).map(_.as[RootInterface])

  myJsonData match {
    case Success(myValue) => // compare your case class here

        println(myValue.info._postman_id)
    case Failure(err) => println("none")

      throw new Exception("couldnt parse: ",err)
  }
}
