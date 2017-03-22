import cats.instances.all._
import processor.ContextProcessor

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

val f = new ContextProcessor().apply(Future.successful(1))
// f: scala.concurrent.Future[Int] = Future(<not completed>)
Await.result(f, 1.second)
// res0: Int = 2
