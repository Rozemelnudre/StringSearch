import lv.rbs.ds.lab03.KMPmatcher
import org.scalatest._

class TestPrefixFun extends FunSuite{

  test("check prefix"){
    val matcher:KMPmatcher = new KMPmatcher("AABAACAABAA")
    val listy:List[Int] = matcher.getPrefixFun()
    assert(listy === List(-1,0,1,0,1,2,0,1,2,3,4,5))
  }



  test("check prefix2") {
    val matcher: KMPmatcher = new KMPmatcher("ABCDE")
    val listy: List[Int] = matcher.getPrefixFun()
    val hehe = matcher.findAllSteps("MMMMMMABCDE")
    assert(listy === List(-1, 0, 0, 0, 0, 0))
  }

  test("test3"){
    val matchs:KMPmatcher = new KMPmatcher("ABCDABD")
    val checkeris = matchs.findAllSteps("ABC ABCDAB ABCDABCDABDE")
    val prefix = matchs.getPrefixFun()
    assert(prefix === List(-1,0,0,0,0,1,2,0))
  }


}
