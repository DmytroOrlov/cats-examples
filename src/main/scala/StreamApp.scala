import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}
import monix.execution.{FutureUtils, Scheduler}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object StreamApp extends App {
  implicit val as = ActorSystem("StreamApp")
  implicit val sc = Scheduler(as.dispatcher)
  implicit val mat = ActorMaterializer()

  val s = Source(1 to 10).map(DK)

  def saveToDb(x: Int): Marker.type = {
    println(s"saved $x")
    Marker
  }

  val sink: Sink[DK, NotUsed] = Flow[DK].mapAsync(1)(dk => FutureUtils.delayedResult(500.millis)(println(dk))).to(Sink.ignore)

  val sinkAlso: Sink[DK, Future[Marker.type]] = Flow[DK].alsoTo(sink).fold(0) {
    case (acc, dk) =>
      println(dk.x)
      acc + 1
  }.toMat(Sink.head)(Keep.right).mapMaterializedValue(_.map(saveToDb))

  val f = s.runWith(sinkAlso)
  f.onComplete(_ => as.terminate())
  Await.ready(f, 20.seconds)
}

case class DK(x: Int)

case object Marker
