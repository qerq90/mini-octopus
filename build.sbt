import Dependencies._

scalaVersion := "2.13.7"
name := "mini-octopus"
organization := "qerq90"
version := "1.0"

lazy val root = (project in file("."))
	.aggregate(core, api, model)


lazy val api = (project in file("mini-octopus-api"))
  .settings(standartSettings)
  .dependsOn(core, model)

lazy val core = (project in file("mini-octopus-core"))
	.settings(standartSettings, libraryDependencies ++= Seq(
        zio,
        sttp,
        jsonZIO,
		configZIO,
		typesafeConfigZIO,
		magnoliaConfigZIO
	))
	.dependsOn(model)

lazy val model = (project in file("mini-octopus-model"))
	.settings(standartSettings, libraryDependencies ++= Seq(
		enumeratum
	))

lazy val standartSettings = Seq(
	scalaVersion := "2.13.7"
)