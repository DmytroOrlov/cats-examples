sealed trait Exp

case class Num(a: Double) extends Exp

case class Div(a: Exp, b: Exp) extends Exp

def evaluate(exp: Exp): Double =
  exp match {
    case Num(a) => a
    case Div(a, b) => evaluate(a) / evaluate(b)
  }

evaluate(Num(1))
evaluate(Div(Num(12), Num(3)))

sealed trait Exception[A]

case class Raise[A](message: String) extends Exception[A]

case class Return[A](a: A) extends Exception[A]

case class Seed(long: Long) {
  def next = Seed(long * 6364136223846793005L + 1442695040888963407L)
}


//case class State[A](run: Int => (Int, A))
import cats.data.State

object ReturnState {
  def apply[A](a: A): State[Int, A] =
    State(s => (s, a))
}

def evaluateState(exp: Exp): State[Int, Double] =
  exp match {
    case Num(a) => ReturnState(a)
    case Div(a, b) =>
      State { n =>
        val (s, v) = (for {
          a1 <- evaluateState(a)
          b1 <- evaluateState(b)
        } yield a1 / b1).run(n).value
        (s + 1, v)
      }
  }

evaluateState(Num(1)).run(0).value
evaluateState(Div(Num(12), Num(3))).run(0).value
evaluateState(Div(Div(Num(12), Num(3)), Num(2))).run(0).value


import cats.Monad
import cats.syntax.all._

object Exception {
  def raiseWhen(f: => Boolean, message: => String): Exception[Double] =
    if (f) Raise[Double](message)
    else Return(0)
}

def evaluate2(exp: Exp): Exception[Double] =
  exp match {
    case Num(a) => Return(a)
    case Div(a, b) =>
      for {
        a1 <- evaluate2(a)
        b1 <- evaluate2(b)
        _ <- Exception.raiseWhen(b1 == 0, "error: division by 0")
      } yield a1 / b1
  }

implicit val exceptionMonad: Monad[Exception] = new Monad[Exception] {
  override def flatMap[A, B](fa: Exception[A])(f: (A) => Exception[B]): Exception[B] =
    fa match {
      case Raise(e) => Raise[B](e)
      case Return(r) => f(r)
    }

  @annotation.tailrec
  override def tailRecM[A, B](a: A)(f: (A) => Exception[Either[A, B]]): Exception[B] =
    f(a) match {
      case Raise(e) => Raise[B](e)
      case Return(Right(r)) => Return(r)
      case Return(Left(r)) => tailRecM(a)(f)
    }

  override def pure[A](x: A): Exception[A] = Return(x)
}

evaluate2(Num(1))
evaluate2(Div(Num(12), Num(3)))
evaluate2(Div(Num(12), Num(0)))
