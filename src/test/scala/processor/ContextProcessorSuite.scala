package processor

import org.scalatest.{FunSuite, MustMatchers}

import cats.Id

class ContextProcessorSuite extends FunSuite with MustMatchers {
  test("ContextProcessor") {
    val one: Id[Int] = 1
    new ContextProcessor().apply(one) mustBe 2
  }
}
