name := "scr-2024-02-play"

organization := "ru.otus"


val sharedSettings = List(
  version := "0.1",
  scalaVersion := "2.11.5"
)

lazy val di = Project(id = "di", base = file("modules/di"))
  .settings(sharedSettings :_*)
  .settings(libraryDependencies += Dependencies.Guice)

lazy val auth = Project(id = "auth", base = file("modules/auth"))
  .settings(sharedSettings :_*)
  .enablePlugins(PlayScala)

lazy val scr = Project(id = "scr", base = file("modules/scr"))
  .dependsOn(auth, di)
  .settings(sharedSettings :_*)
  .enablePlugins(PlayScala)

lazy val root = (project in file("."))
  .settings(sharedSettings :_*)
  .dependsOn(scr)
  .aggregate(auth, scr, di)
  .enablePlugins(PlayScala)