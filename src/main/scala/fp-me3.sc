import scala.concurrent.Future

def calcLenght(s: String): Future[Int] = Future.successful(s.length)

def distinct(s: String): Future[String] = Future.successful(s.distinct)

distinct("aassdd")
