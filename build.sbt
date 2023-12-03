lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      scalaVersion := "2.13.12"
    )),
    name := "AOC2023"
  )

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % Test
libraryDependencies += "com.lihaoyi" %% "requests" % "0.7.0"