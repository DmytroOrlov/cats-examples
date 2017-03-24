package applicative

import cats.Applicative
import cats.instances.all._
import cats.laws.discipline.ApplicativeTests
import org.scalatest.WordSpec
import org.scalatest.prop.Checkers

class ApplicativeSuite extends WordSpec with Checkers {
  implicit val applicativeInstancesForOption = new Applicative[Option] {
    override def map2[A, B, Z](fa: Option[A], fb: Option[B])(f: (A, B) => Z): Option[Z] = {
      fa match {
        case None => fb.asInstanceOf[Option[Z]]
        case a => a.flatMap(a => fb.map(b => f(a, b)))
      }
    }

    override def pure[A](x: A): Option[A] = Some(x)

    override def ap[A, B](ff: Option[(A) => B])(fa: Option[A]): Option[B] = ff.flatMap(f => fa.map(f))
  }

  "Applicative[Option]" should {
    "satisfy laws" in {
      val rules = ApplicativeTests[Option](applicativeInstancesForOption)
        .applicative[Int, Int, Int]
        .props
        .foreach { p =>
          check(p._1 |: p._2)
        }
    }
  }
}
