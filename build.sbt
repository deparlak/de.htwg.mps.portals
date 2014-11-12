name := "de.htwg.mps.portals"

organization := "de.htwg.mps"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "2.4" % "test",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scala-lang.modules" %% "scala-swing" % "1.0.1",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value
)


EclipseKeys.withSource := true

EclipseKeys.withJavadoc := true
