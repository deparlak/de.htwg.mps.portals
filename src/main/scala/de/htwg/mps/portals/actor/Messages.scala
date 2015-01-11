package de.htwg.mps.portals.actor

import de.htwg.mps.portals.controller.Event
import de.htwg.mps.portals.model.Direction


sealed trait Message
case class CreatePlayer(id: String) extends Message
case class PlayerMove(id: String, direction: Direction) extends Message
case class GameEvent(event: Event) extends Message
case class Result(id: String, right: Int, left: Int, up: Int, down: Int, moves: Int) extends Message
