lazy val catsVersion = "1.5.0"

lazy val `cats-examples` = (project in file("."))
  .settings(
    version := "1.0",
    scalaVersion := "2.12.7",
    scalacOptions += "-Ypartial-unification",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-stream" % "2.5.18",
      "org.typelevel" %% "cats-core" % catsVersion,
      "org.typelevel" %% "cats-macros" % catsVersion,
      "org.typelevel" %% "cats-kernel" % catsVersion,
      "org.typelevel" %% "cats-free" % catsVersion,
      "org.typelevel" %% "cats-laws" % catsVersion,
      "io.monix" %% "monix" % "3.0.0-RC1",
      "org.scalatest" %% "scalatest" % "3.0.5" % Test
    )
  )
