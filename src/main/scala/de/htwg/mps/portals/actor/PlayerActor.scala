package de.htwg.mps.portals.actor

import akka.actor.Actor
import akka.actor._
import de.htwg.mps.portals.controller.NewGame
import de.htwg.mps.portals.model.Right
import de.htwg.mps.portals.model.Left
import de.htwg.mps.portals.model.Up
import de.htwg.mps.portals.model.Down
import de.htwg.mps.portals.model.Stay

class PlayerActor(
  id: String,
  var right: Int = 0,
  var left: Int = 0,
  var up: Int = 0,
  var down: Int = 0) extends Actor {

  def receive = {
    case PlayerMove(id, direction) =>
      direction match {
        case Right => right += 1
        case Left  => left += 1
        case Up    => up += 1
        case Down  => down += 1
        case Stay  => None
      }
    case GameEvent(event) => event match {
      case event: NewGame => None
      case _              => sender ! Result(id, right, left, up, down, right + left + up + down)
    }
  }
}