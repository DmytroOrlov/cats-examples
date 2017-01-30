import cats.{Applicative, Traverse}
import cats.instances.all._
import cats.syntax.traverse._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

val f = { (i: Int) =>
  if (i % 2 == 0) Some(i) else None
}
Traverse[List].traverse(List(2, 4))(f)
List(2, 4).traverse(f)
List(1).traverse(f)
List(1).map(f)
List(1).map(f).sequence

val someInts: List[Option[Int]] = List(Some(2), Some(4))
someInts.traverse(identity)
someInts.sequence
List(Some(2), None).traverse(identity)
List(Some(2), None).sequence

def traverse[F[_] : Applicative, A, B](as: List[A])(f: A => F[B]): F[List[B]] =
  as.foldRight(Applicative[F].pure(List.empty[B])) { (a: A, acc: F[List[B]]) =>
    val fb: F[B] = f(a)
    Applicative[F].map2(fb, acc)(_ :: _)
  }

traverse(List(1))(f)
traverse(List(2, 4))(f)


val fs1 = Future.traverse(List(1, 2))(n => Future.successful(n + 1))
Await.result(fs1, 1.second)
val fs2 = List(1, 2).traverse(n => Future.successful(n + 1))
Await.result(fs2, 1.second)

val someInt: Option[Int] = Some(1)
val fs2_2 = someInt.traverse(n => Future.successful(n + 1))
Await.result(fs2_2, 1.second)

val noneInt: Option[Int] = None
val fs2_3 = noneInt.traverse(n => Future.successful(n + 1))
Await.result(fs2_3, 1.second)
