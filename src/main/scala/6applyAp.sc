import cats.implicits._
import cats.{Applicative, Apply}

val f: (Int, Char) => Double = (i, c) => (i + c).toDouble

val char: Option[Char] = Some('a')
val int: Option[Int] = Some(5)
Applicative[Option].map2(int, char)(f)
(int, char).map2(f)
(int |@| char).map(f)


val ff: Option[(Char) => Double] = int.map { i =>
  (c: Char) => f(i, c)
}

Apply[Option].ap(ff)(char)


Applicative[Option].lift((c: Char) => f(5, c))(char)
Applicative[Option].map(char)((c: Char) => f(5, c))
