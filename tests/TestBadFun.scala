import org.scalatest._
import lv.rbs.ds.lab04.BMmatcher


class TestBadFun extends FunSuite {

  test("first test bad fun"){

    val matchy:BMmatcher = new BMmatcher("ABCDABD")
    val listy = matchy.getBadCharFun()

    val match2:BMmatcher = new BMmatcher("abcdea")
    val list2 = match2.getBadCharFun()
  }

}
