package actor

import akka.actor.{Actor, ActorSystem, Props}

import scala.concurrent.duration._
import akka.pattern.ask

import scala.concurrent.Await

object ActorApp extends App {
  val system = ActorSystem()

  val calcLength = system.actorOf(Props[CalcLength])
  val distinct = system.actorOf(Props[Distinct])

  val s = "aabccc"
  val f = calcLength.ask(s)(1.second).mapTo[Int]

  println(Await.result(f, 1.second))
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
