name := "iron"
version := "0.1-SNAPSHOT"
scalaVersion := "3.3.1-RC7"
organization := "objektwerks"
libraryDependencies ++= {
  val ironVersion = "2.2.1"
  val jsoniterVersion = "2.23.4"
  Seq(
    "io.github.iltotore" %% "iron" % ironVersion,
    "io.github.iltotore" %% "iron-jsoniter" % ironVersion,
    "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-core" % jsoniterVersion,
    "com.github.plokhotnyuk.jsoniter-scala" %% "jsoniter-scala-macros" % jsoniterVersion % "provided",
    "org.scalatest" %% "scalatest" % "3.2.16" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
