lazy val `cats-examples` = (project in file("."))
  .settings(
    version := "1.0",
    scalaVersion := "2.12.4",
    scalacOptions += "-Ypartial-unification",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-stream" % "2.5.9",
      "org.typelevel" %% "cats-core" % "1.0.1",
      "org.typelevel" %% "cats-macros" % "1.0.1",
      "org.typelevel" %% "cats-kernel" % "1.0.1",
      "io.monix" %% "monix" % "3.0.0-M3",
      "org.scalatest" %% "scalatest" % "3.2.0-SNAP10" % Test
    )
  )
