lazy val `cats-examples` = (project in file("."))
  .settings(
    version := "1.0",
    scalaVersion := "2.12.1",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats" % "0.9.0",
      "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    )
  )
