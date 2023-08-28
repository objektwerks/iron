name := "iron"
version := "0.1-SNAPSHOT"
scalaVersion := "3.3.1-RC6"
organization := "objektwerks"
libraryDependencies ++= {
  Seq(
    "io.github.iltotore" %% "iron" % "2.2.1",
    "io.github.iltotore" %% "iron-jsoniter" % "2.2.1",
    "org.scalatest" %% "scalatest" % "3.2.16" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)