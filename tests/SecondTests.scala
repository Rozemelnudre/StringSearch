import lv.rbs.ds.lab03.KMPmatcher
import org.scalatest._


class SecondTests extends FunSuite {

  test("first test"){
    val matcher:KMPmatcher = new KMPmatcher("ABCDABD")
    val listy:List[Int] = matcher.getPrefixFun()
    var chhhh = matcher.findAllSteps("ABC ABCDAB ABCDABCDABDE")
    assert(listy === List(-1,0,0,0,0,1,2,0))
  }

}
