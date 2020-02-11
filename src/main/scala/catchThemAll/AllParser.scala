package catchThemAll

package postman

import play.api.libs.json.Json
import play.api.libs.json._

import scala.util.Failure
import scala.util.Success
import scala.util.Try
import play.api.libs.functional.syntax._

object AllParser extends App {
  val move_collections = "./resources/moves.json"
  val jsonifiedString = scala.io.Source.fromFile(move_collections).mkString
  val postman_collections_json =
    Try(Json.parse(jsonifiedString))
      .map(_.as[Seq[MoveSchema]])


  println(postman_collections_json)
  postman_collections_json match {
    case Success(myValue) => // compare your case class here

          println(myValue)
    //      println(myValue.item(0).item(0).response(0).originalRequest)

    case Failure(err) =>
      println("none")

//      throw new Exception("couldnt parse: ", err)
  }


//  case class Group(id: String, name: String)
//
//  implicit val twitterGroupReads: Reads[Group] = (
//    (JsPath \\ "id_str").read[String] and
//      (JsPath \\ "name").read[String]
//    ) (Group.apply _)
//  //  twitterGroupReads: play.api.libs.json.Reads[Group] = play.api.libs.json.Reads$$anon$8@f2fae02
//
//  val json = Json.parse("""[{"id_str":"67979542","name":"account"}, {"id_str":"12345678","name":"account2"}, {"id_str":"3423423423","name":"account3"}]""")
//  //  json: play.api.libs.json.JsValue = [{"id_str":"67979542","name":"account"},{"id_str":"12345678","name":"account2"},{"id_str":"3423423423","name":"account3"}]
//
//  println(json.as[Seq[Group]])

}
