import cats.instances.all._
import cats.syntax.traverse._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

val eventualInts = List(1, 2).traverse(n => Future.successful(n + 1))
// eventualInts: scala.concurrent.Future[List[Int]] = Future(<not completed>)
Await.result(eventualInts, 1.second)
// res0: List[Int] = List(2, 3)


val someInt: Option[Int] = Some(1)
val eventualSomeInt = someInt.traverse(n => Future.successful(n + 1))
// eventualSomeInt: scala.concurrent.Future[Option[Int]] = Future(<not completed>)
Await.result(eventualSomeInt, 1.second)
// res1: Option[Int] = Some(2)

val noneInt: Option[Int] = None
val eventualNoneInt = noneInt.traverse(n => Future.successful(n + 1))
// eventualNoneInt: scala.concurrent.Future[Option[Int]] = Future(<not completed>)
Await.result(eventualNoneInt, 1.second)
// res2: Option[Int] = None


def isPositive: Int => Option[Int] = {
  case n if n > 0 => Some(n)
  case _ => None
}

val forallPositive = List(1, 2).traverse(isPositive)
// forallPositive: Option[List[Int]] = Some(List(1, 2))
val someZero: Option[Int] = Some(0)
val invalid = someZero.traverse(isPositive)
// invalid: Option[Option[Int]] = None
val containsNegative = List(-1, 2).traverse(isPositive)
// containsNegative: Option[List[Int]] = None


import cats.Applicative

def traverse[F[_] : Applicative, A, B](as: List[A])(f: A => F[B]): F[List[B]] =
  as.foldRight(Applicative[F].pure(List.empty[B])) { (a: A, facc: F[List[B]]) =>
    val fb: F[B] = f(a)
    Applicative[F].map2(fb, facc) { (b: B, acc: List[B]) =>
      b :: acc
    }
  }
traverse(List(-1, 2))(isPositive)
// res3: Option[List[Int]] = None


type F[T] = Option[T]
type A = Int
type B = Int
val facc0 = Applicative[Option].pure(List.empty[Int])
// facc0: Option[List[Int]] = Some(List())
val fb1 = isPositive(2)
// fb1: Option[Int] = Some(2)
val facc1 = Applicative[Option].map2(fb1, facc0)(_ :: _)
// facc1: Option[List[Int]] = Some(List(2))
val fb2 = isPositive(-1)
// fb2: Option[Int] = None
val facc2 = Applicative[Option].map2(fb2, facc1)(_ :: _)
// facc2: Option[List[Int]] = None


val applicativeInstancesForOption = new Applicative[Option] {
  override def map2[A, B, Z](fa: Option[A], fb: Option[B])(f: (A, B) => Z): Option[Z] = {
    fa match {
      case None => fb.asInstanceOf[Option[Z]]
      case a => a.flatMap(a => fb.map(b => f(a, b)))
    }
  }

  override def pure[A](x: A): Option[A] = Some(x)

  override def ap[A, B](ff: Option[(A) => B])(fa: Option[A]): Option[B] = ff.flatMap(f => fa.map(f))
}
cats.instances.option.catsStdInstancesForOption.map2(None, Some(List(2)))(_ :: _)
applicativeInstancesForOption.map2(None, Some(List(2)))(_ :: _)
