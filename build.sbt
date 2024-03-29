name := "iron"
version := "0.1-SNAPSHOT"
scalaVersion := "3.4.1-RC2"
organization := "objektwerks"
libraryDependencies ++= {
  val ironVersion = "2.5.0"
  val jsoniterVersion = "2.28.4"
  Seq(
    "io.github.iltotore" %% "iron" % ironVersion,
    "io.github.iltotore" %% "iron-jsoniter" % ironVersion,
    "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core" % jsoniterVersion,
    "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % jsoniterVersion % "provided",
    "org.scalatest" %% "scalatest" % "3.2.18" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
