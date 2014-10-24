package main.scala.model

// Events which could occur on the playground
sealed trait Event

final case class GameEnd() extends Event

final case class Update() extends Event

sealed trait Move

final case class PlayerMove(from : Position, fromItem : Item, to : Position, toItem : Item) extends Move
final case class InvalidMove(reason : String) extends Move
