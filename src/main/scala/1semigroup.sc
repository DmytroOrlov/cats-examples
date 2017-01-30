import cats.Semigroup
import cats.instances.all._
import cats.syntax.semigroup._

Semigroup[Int].combine(1, 2)
Semigroup[Int].combineAllOption(List())
Semigroup[Int].combineAllOption(List(1, 2, 3))

val map1 = Map("hello" -> 1, "world" -> 1)
val map2 = Map("hello" -> 2, "cats"  -> 2)

map1 |+| map2


def combine[A: Semigroup](a: A, b: A): A =
  Semigroup[A].combine(a, b)

combine(1, 2)
