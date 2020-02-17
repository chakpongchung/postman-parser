package catchThemAll

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder


object MoveParser extends App {


  case class Move(num: Int,
                  accuracy: AccuracyType,
                  basePower: Int,
                  category: String,
                  desc: Option[String],
                  shortDesc: String,
                  id: String,
                  isNonstandard: Option[String],
                  isViable: Option[Boolean],
                  name: String,
                  pp: Int,
                  priority: Int,
                  flags: Flags,
                  secondary: Option[SecondaryType])


  type AccuracyType = Either[Int, Boolean]
  type SecondaryType = Either[Boolean, Secondary]

  case class Flags(authentic: Option[Int], bite: Option[Int])

  case class Secondary(chance: Option[Int],
                       volatileStatus: Option[String],
                       boosts: Option[Boosts],
                       self: Option[Self],
                       status: Option[String])

  case class Boosts(atk: Option[Int], `def`: Option[Int])

  case class Self(boosts: Option[Boosts])


  val move_collections = "./resources/moves.json"
  val jsonifiedString = scala.io.Source.fromFile(move_collections).mkString

  implicit final val BoostsDecoder: Decoder[Boosts] = deriveDecoder
  implicit final val selfDecoder: Decoder[Self] = deriveDecoder

  implicit final val SecondaryDecoder: Decoder[Secondary] = deriveDecoder
  implicit final val secondaryTypeDecoder: Decoder[SecondaryType] = Decoder[Boolean] either Decoder[Secondary]

  implicit final val AccuracyTypeDecoder: Decoder[AccuracyType] = Decoder[Int] either Decoder[Boolean]

  implicit final val flagsDecoder: Decoder[Flags] = deriveDecoder
  implicit final val moveDecoder: Decoder[Move] = deriveDecoder

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
              case secondaryType: SecondaryType =>
                //                println(yy)
                secondaryType match {

                  case secondar: Secondary =>
//                    secondar.boosts match {
//                      case Some(boostsV) =>
//
//                        boostsV.atk match {
//                          case Some(atkV) => println("atk: " + atkV)
//                          case  None => println()
//                        }
//                    }

                }


            }

          case None =>
            //            println("none")
            println()


        }
      }

    case Left(err) =>
      println(err)
      println("none")
    //      throw new Exception("couldnt parse: ", err)
  }


}
