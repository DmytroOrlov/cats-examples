import cats.Applicative
import cats.instances.all._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

Applicative[Option].map2(Some(42), Some("a")) { (i, s) =>
  i.toString ++ s
}


case class Connection(x: String)

val username: Option[String] = Some("username")
val password: Option[String] = Some("password")
val url: Option[String] = Some("some.login.url.here")

def connect(username: String, password: String, url: String): Connection = Connection(s"$username:$password/$url")

Applicative[Option].map3(username, password, url)(connect)


val x: Future[Option[Int]] = Future.successful(Some(5))
val y: Future[Option[Char]] = Future.successful(Some('a'))
Applicative[Future].compose[Option].map2(x, y)(_ + _).foreach(println)
