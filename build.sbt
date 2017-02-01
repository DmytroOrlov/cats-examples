lazy val `cats-examples` = (project in file("."))
  .settings(
    version := "1.0",
    scalaVersion := "2.12.1",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-stream" % "2.4.16",
      "org.typelevel" %% "cats" % "0.9.0",
      "io.monix" %% "monix" % "2.2.1",
      "org.scalatest" %% "scalatest" % "3.0.1" % Test
    )
  )
