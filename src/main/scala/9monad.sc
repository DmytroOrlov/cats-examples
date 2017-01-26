import cats.implicits._
import cats.Monad

Monad[Option].ifM(None)(Some(1), Some(0))
Monad[Option].ifM(Some(true))(Some(1), Some(0))
Monad[Option].ifM(Some(false))(Some(1), Some(0))
