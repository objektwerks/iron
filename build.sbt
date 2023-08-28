name := "iron"
version := "0.1-SNAPSHOT"
scalaVersion := "3.3.1-RC6"
organization := "objektwerks"
libraryDependencies ++= {
  val ironVersion = "2.2.1"
  Seq(
    "io.github.iltotore" %% "iron" % ironVersion,
    "io.github.iltotore" %% "iron-jsoniter" % ironVersion,
    "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core" % "2.23.3",
    "org.scalatest" %% "scalatest" % "3.2.16" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)