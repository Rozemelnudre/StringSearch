import org.scalatest._
import lv.rbs.ds.lab04.BMmatcher


class TestGoodSuffix extends FunSuite{

  test("test goodSuffix"){

    val matchy:BMmatcher = new BMmatcher("ABCDABD")
    val goodlist = matchy.getGoodSuffixFun()
  }

}
