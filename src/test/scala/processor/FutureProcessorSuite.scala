package processor

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FunSuite, MustMatchers}

import scala.concurrent.Future

class FutureProcessorSuite extends FunSuite with MustMatchers with ScalaFutures {
  test("FutureProcessor") {
    whenReady(new FutureProcessor().apply(Future.successful(1))) {
      _ mustBe 2
    }
  }
}
