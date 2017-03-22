package processor

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class FutureProcessor {
  def apply(value: Future[Int]): Future[Int] = value.map(_ + 1)
}
