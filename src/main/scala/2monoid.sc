import cats.Monoid
import cats.instances.all._

Monoid[Int].empty
Monoid[Int].combineAll(List())
Monoid[Int].combineAll(List(1, 2, 3))


def combineAll[A: Monoid](as: List[A]): A =
  as.foldLeft(Monoid[A].empty)(Monoid[A].combine)

combineAll(List(1, 2, 3))
