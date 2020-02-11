package catchThemAll

import play.api.libs.json.{Format, Json}

//
//{
//"num": 719,
//"accuracy": true,
//"basePower": 195,
//"category": "Special",
//"desc": "Has a very high chance for a critical hit.",
//"shortDesc": "Very high critical hit ratio.",
//"id": "10000000voltthunderbolt",
//"isNonstandard": "Past",
//"isViable": true,
//"name": "10,000,000 Volt Thunderbolt",
//"pp": 1,
//"priority": 0,
//"flags": {},
//"isZ": "pikashuniumz",
//"critRatio": 3,
//"secondary": null,
//"target": "normal",
//"type": "Electric",
//"contestType": "Cool"
//},
case class MoveSchema(num:Int
//                      , accuracy:Either[Int, Boolean]
                     )


object MoveSchema {
  implicit val format: Format[MoveSchema] = Json.format
}

