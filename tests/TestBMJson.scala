import org.scalatest._
import lv.rbs.ds.lab04.BMmatcher
import play.api.libs.json.{Json, JsValue}


class TestBMJson extends FunSuite{
  test("testdajS"){

    val matchs:BMmatcher = new BMmatcher("ABCDABD")
    val checkeris = matchs.findAllIn("ABC ABCDAB ABCDABCDABDE")
    val jsToTest:String = matchs.toJsonTest("ABC ABCDAB ABCDABCDABDE")
    val jsFromTest:JsValue = Json.parse(jsToTest)
    val comparisons:Int = (jsFromTest\"comparisons").as[Int]
    assert(comparisons == 10)
    val algo:String = (jsFromTest \ "algorithm").as[String]
    assert(algo === "BM")
    val pattern:String = (jsFromTest \"pattern").as[String]
    assert(pattern === "ABCDABD")
    val text:String = (jsFromTest \"text").as[String]
    assert(text === "ABC ABCDAB ABCDABCDABDE")
    val goodSuffix:List[List[Int]] = (jsFromTest \"goodSuffixFun").as[List[List[Int]]]
    assert(goodSuffix === List(List(0,7), List(1,7), List(2,7), List(3,7), List(4,7), List(5,7), List(6,3), List(7,1)))
    val badChar:List[(String, Int)] = (jsFromTest \ "badCharFun").as[List[(String, Int)]]
    assert(badChar === List(("A", 4),("B", 5),("C", 2),("D", 6)))
    val steps:List[Map[String,Int]] = (jsFromTest \"steps").as[List[Map[String,Int]]]
    val stepCompare:List[Map[String, Int]] = List(
      Map("offset" -> 0, "start" -> 6, "end" -> 6),
      Map("offset" -> 4, "start" -> 6, "end" -> 6),
      Map("offset" -> 11, "start" -> 6, "end" -> 6),
      Map("offset" -> 15, "start" -> 6, "end" -> 0, "match" -> 1)
    )

    assert(stepCompare === steps)


  }


}
