package catchThemAll

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder


object MoveParser extends App {

  implicit final val BoostsDecoder: Decoder[Boosts] = deriveDecoder
  implicit final val selfDecoder: Decoder[Self] = deriveDecoder
  implicit final val SecondaryDecoder: Decoder[Secondary] = deriveDecoder
  implicit final val secondaryTypeDecoder: Decoder[Either[Boolean, Secondary]] = Decoder[Boolean] either Decoder[Secondary]
  implicit final val AccuracyTypeDecoder: Decoder[Either[Int, Boolean]] = Decoder[Int] either Decoder[Boolean]
  implicit final val flagsDecoder: Decoder[Flags] = deriveDecoder
  implicit final val moveDecoder: Decoder[Move] = deriveDecoder


  val move_collections = "./resources/moves.json"
  val jsonifiedString = scala.io.Source.fromFile(move_collections).mkString

  val result = io.circe.parser.decode[List[Move]](jsonifiedString)
  println(result)

  result match {
    case Right(x) => // compare your case class here
      //          println(x(0).category)
      //          println(x(0).secondary)
      for (i <- 0 until x.length) {
        println(s"$i is ${x(i)}")
        val move = x(i)
        move.secondary
        match {
          case Some(sec) =>

            sec match {
              case Left(b) =>
                println("secondary is boolean: " + b)

              case Right(s) =>
                s.boosts match {

                  case Some(boosts) =>

                    boosts.atk match {
                      case Some(atk) => println("atk: " + atk)
                      case None => println("boosts has no atk")
                    }
                  case None =>

                    println("secondary has no boost")
                }
            }

          case None =>
            println("no secondary")


        }
      }

    case Left(err) =>
      throw new Exception("couldnt parse: ", err)
  }


}
