import cats.implicits._

val o42: Option[Int] = Some(42)
val oHello: Option[String] = Some("hello")

(o42 |@| oHello).map((i, s) => i.toString ++ s)

(o42 |@| oHello).tupled

(o42, oHello).map2((i, s) => i.toString ++ s)
