import org.scalatest._
import play.api.libs.json.{JsValue, Json}
import lv.rbs.ds.lab03.KMPmatcher


class TestJson extends FunSuite {

  test("check json"){

    val matchs:KMPmatcher = new KMPmatcher("ABCDABD")
    val checkeris = matchs.findAllIn("ABC ABCDAB ABCDABCDABDE")
    val jsToTest:String = matchs.toJsonTest("ABC ABCDAB ABCDABCDABDE")
    val jsFromTest:JsValue = Json.parse(jsToTest)
    val comparisons:Int = (jsFromTest\"comparisons").as[Int]
    assert(comparisons == 27)
    val algo:String = (jsFromTest \ "algorithm").as[String]
    assert(algo === "KMP")
    val pattern:String = (jsFromTest \"pattern").as[String]
    assert(pattern === "ABCDABD")
    val text:String = (jsFromTest \"text").as[String]
    assert(text === "ABC ABCDAB ABCDABCDABDE")
    val prefix:List[List[Int]] = (jsFromTest \"prefixFun").as[List[List[Int]]]
    assert(prefix === List(List(0,-1), List(1,0), List(2,0), List(3,0), List(4,0), List(5,1), List(6,2), List(7,0)))
    val steps:List[Map[String,Int]] = (jsFromTest \"steps").as[List[Map[String,Int]]]
    val stepCompare:List[Map[String, Int]] = List(
      Map("offset" -> 0, "start" -> 0, "end" -> 3),
      Map("offset" -> 3, "start" -> 0, "end" -> 0),
      Map("offset" -> 4, "start" -> 0, "end" -> 6),
      Map("offset" -> 8, "start" -> 2, "end" -> 2),
      Map("offset" -> 10, "start" -> 0, "end" -> 0),
      Map("offset" -> 11, "start" -> 0, "end" -> 6),
      Map("offset" -> 15, "start" -> 2, "end" -> 6, "match" -> 1),
      Map("offset" -> 22, "start" -> 0, "end" -> 0)
    )

    assert(stepCompare === steps)


  }

}
