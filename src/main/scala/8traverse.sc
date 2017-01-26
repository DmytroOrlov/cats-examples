import cats.Applicative
import cats.instances.all._
import cats.syntax.traverse._

val f = { (i: Int) =>
  if (i % 2 == 0) Some(i) else None
}
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
