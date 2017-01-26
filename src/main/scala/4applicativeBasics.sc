import cats.Applicative
import cats.instances.all._

Applicative[Option].pure(1)
Applicative[Option].product(Some(1), Some("a"))
Applicative[Option].map(Some(1))(_ + 1)

def product3[F[_]: Applicative, A, B, C](fa: F[A], fb: F[B], fc: F[C]): F[(A, B, C)] = {
  val F = Applicative[F]
  val fabc = F.product(F.product(fa, fb), fc)
  F.map(fabc) { case ((a, b), c) => (a, b, c) }
}

product3(List(1), List("a"), List('b'))
