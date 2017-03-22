package processor

import cats.Functor

class ContextProcessor {
  def apply[F[_]: Functor](value: F[Int]): F[Int] = Functor[F].map(value)(_ + 1)
}
