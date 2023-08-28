name := "iron"
version := "0.1-SNAPSHOT"
scalaVersion := "3.3.1-RC6"
organization := "objektwerks"
libraryDependencies ++= {
  Seq(
    "io.github.iltotore" %% "iron" % "2.2.1"
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)