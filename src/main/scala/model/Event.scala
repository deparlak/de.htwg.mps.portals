package main.scala.model

// Events which could occur on the playground, after moving an Item
sealed trait Event

final case class GameEnd() extends Event

final case class Update() extends Event

