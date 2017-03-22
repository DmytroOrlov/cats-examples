import cats.Functor
import cats.instances.all._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

Functor[Option].map(Some(2))(_ + 1)
Functor[Option].lift((i: Int) => i + 1)(Some(2))


def needsFunctor[O[_]: Functor](fa: O[Int]) = Functor[O].map(fa)(_ + 1)

needsFunctor(List(1, 2))
Await.result(
  needsFunctor(Future.successful(2)),
  1.second)
needsFunctor[Option](Some(1))


val futureListOption = Future(List(Some(1), None, Some(2)))
val f: Future[List[Option[Int]]] = Functor[Future].compose[List].compose[Option].map(futureListOption)(_ + 1)
Await.result(f, 1.second)
