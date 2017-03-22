import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

val eventualInts = Future.traverse(List(1, 2))(fn =
  n => Future.successful(n + 1)
)
// eventualInts: scala.concurrent.Future[List[Int]] = Future(<not completed>)
Await.result(eventualInts, 1.second)
// res0: List[Int] = List(2, 3)
