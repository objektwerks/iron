lazy val indigo = (project in file("."))
  .enablePlugins(ScalaJSPlugin, SbtIndigo)
  .settings(
    name := "indigo",
    version := "0.1-SNAPSHOT",
    scalaVersion := "3.3.1-RC6",
    organization := "objektwerks"
  )
  .settings(
    title := "Game",
    gameAssetsDirectory := "assets",
    windowStartWidth := 720,
    windowStartHeight := 480,
    showCursor := true,
    libraryDependencies ++= {
      val indigoVersion = "0.15.0-RC3"
      Seq(
        "io.indigoengine" %%% "indigo" % indigoVersion,
        "io.indigoengine" %%% "indigo-extras" % indigoVersion,
        "io.indigoengine" %%% "indigo-json-circe" % indigoVersion,
      )
    }
  )
scalacOptions ++= Seq(
  "-Wunused:all"
)
