import lv.rbs.ds.lab03.KMPmatcher
import org.scalatest._

class TestPrefixFun extends FunSuite{

  test("check prefix"){
    val matcher:KMPmatcher = new KMPmatcher("AABAACAABAA")
    val listy:List[Int] = matcher.getPrefixFun()
    assert(listy === List(-1,0,1,0,1,2,0,1,2,3,4,5))
  }

  test("check prefix2"){
    val matcher:KMPmatcher = new KMPmatcher("ABCDE")
    val listy:List[Int] = matcher.getPrefixFun()
    val hehe = matcher.findAllSteps("MMMMMMMMMABCDE")
    assert(listy === List(-1,0,0,0,0,0))
  }

  test("check prefix3"){
    val matcher:KMPmatcher = new KMPmatcher("ABCDABD")
    val listy:List[Int] = matcher.getPrefixFun()
    val hehe = matcher.findAllSteps("ABC ABCDAB ABCDABCDABDE")
    assert(listy === List(-1,0,0,0,0,1,2,0))
  }

}
