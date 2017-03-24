package actor

import akka.actor.{Actor, ActorSystem, Props}
import scala.concurrent.duration._
import akka.pattern.ask

object ActorApp extends App {
  val system = ActorSystem()

  val calcLength = system.actorOf(Props[CalcLength])
  val distinct = system.actorOf(Props[Distinct])

  val s = "aabccc"
  calcLength.ask(s)(1.second).mapTo[Int]

  Thread.sleep(1.second.toMillis)
  system.terminate()
}

class CalcLength extends Actor {
  def receive: Receive = {
    case s: String => sender ! s.length
  }
}

class Distinct extends Actor {
  def receive: Receive = {
    case s: String => sender ! s.distinct
  }
}
