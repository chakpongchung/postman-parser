//
//object PathAdditions {
//  implicit class PathAdditions(path: JsPath) {
//
//    def readNullableIterable[A <: Iterable[_]](implicit reads: Reads[A]): Reads[A] =
//      Reads((json: JsValue) => path.applyTillLast(json).fold(
//        error => error,
//        result => result.fold(
//          invalid = (_) => reads.reads(JsArray()),
//          valid = {
//            case JsNull => reads.reads(JsArray())
//            case js => reads.reads(js).repath(path)
//          })
//      ))
//
//    def writeNullableIterable[A <: Iterable[_]](implicit writes: Writes[A]): OWrites[A] =
//      OWrites[A]{ (a: A) =>
//        if (a.isEmpty) Json.obj()
//        else JsPath.createObj(path -> writes.writes(a))
//      }
//
//    /** When writing it ignores the property when the collection is empty,
//     * when reading undefined and empty jsarray becomes an empty collection */
//    def formatNullableIterable[A <: Iterable[_]](implicit format: Format[A]): OFormat[A] =
//      OFormat[A](r = readNullableIterable(format), w = writeNullableIterable(format))
//
//  }
//}
//
