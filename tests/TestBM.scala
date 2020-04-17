import org.scalatest._
import lv.rbs.ds.lab04.BMmatcher


class TestBM extends FunSuite{

  test("test FindAllIn"){

    val metcher:BMmatcher = new BMmatcher("ABCDABD")

    println("printing findaal short")

    println(metcher.findAllIn("ABC ABCDAB ABCDABCDABDE"))
    metcher.findAllSteps("ABC ABCDAB ABCDABCDABDE")
  }

}
