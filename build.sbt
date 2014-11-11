name := "de.htwg.mps.portals"

organization := "de.htwg.mps"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.10.4" // swing isn't available for 2.11

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "2.4" % "test",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scala-lang" % "scala-swing" % scalaVersion.value,
  "org.scala-lang" % "scala-reflect" % scalaVersion.value
)
