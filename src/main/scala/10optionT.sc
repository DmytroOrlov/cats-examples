import cats.data.OptionT
import cats.instances.all._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

val customGreeting: Future[Option[String]] = Future.successful(Some("welcome back, Lola"))

val customGreetingT: OptionT[Future, String] = OptionT(customGreeting)

val excitedGreeting: Future[Option[String]] = customGreeting.map(_.map(_ + "!"))
val excitedGreetingT: OptionT[Future, String] = customGreetingT.map(_ + "!")

val hasWelcome: Future[Option[String]] = customGreeting.map(_.filter(_.contains("welcome")))
val withWelcomeT: OptionT[Future, String] = customGreetingT.filter(_.contains("welcome"))

val noWelcome: Future[Option[String]] = customGreeting.map(_.filterNot(_.contains("welcome")))
val noWelcomeT: OptionT[Future, String] = customGreetingT.filterNot(_.contains("welcome"))

val withFallback: Future[String] = customGreeting.map(_.getOrElse("hello, there!"))
val withFallbackT: Future[String] = customGreetingT.getOrElse("hello, there!")
