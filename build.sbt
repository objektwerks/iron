name := "iron"
version := "0.1-SNAPSHOT"
scalaVersion := "3.6.4"
organization := "objektwerks"
libraryDependencies ++= {
  val ironVersion = "3.0.0-RC1"
  val jsoniterVersion = "2.33.2"
  Seq(
    "io.github.iltotore" %% "iron" % ironVersion,
    "io.github.iltotore" %% "iron-jsoniter" % ironVersion,
    "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core" % jsoniterVersion,
    "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % jsoniterVersion % "provided",
    "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
