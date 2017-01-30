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


def combine[K, V: Semigroup](lhs: Map[K, V], rhs: Map[K, V]): Map[K, V] = {
  def optionCombine[A: Semigroup](a: A, opt: Option[A]): A =
    opt.map(a |+| _).getOrElse(a)

  lhs.foldLeft(rhs) {
    case (acc, (k, v)) => acc.updated(k, optionCombine(v, opt = acc.get(k)))
  }
}
val xm1 = Map('a' -> 1, 'c' -> 4)
val xm2 = Map('a' -> 1, 'b' -> 3)
val x = combine(xm1, xm2)
