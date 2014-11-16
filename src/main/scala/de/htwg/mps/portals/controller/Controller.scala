package de.htwg.mps.portals.controller

import de.htwg.mps.portals.model._
import de.htwg.mps.portals.util.Observable
import de.htwg.mps.portals.util.Timer

// Events which could be fired by the controller
sealed trait Event
final case class GameEnd() extends Event
final case class Update() extends Event
final case class NewGame() extends Event
final case class GameLost() extends Event


class Controller(var playground: Playground) extends Observable[Event] {
  // move a player with the specified uuid. The player will not be move directly. Otherwise
  // the direction will be set to which we like to move and a cyclic method will do
  // the move after a specified timeout
  def moveUp(uuid : String) = playground = playground.setMove(uuid, Up);
  def moveDown(uuid : String) = playground = playground.setMove(uuid, Down);
  def moveLeft(uuid : String) = playground = playground.setMove(uuid, Left);
  def moveRight(uuid : String) = playground = playground.setMove(uuid, Right);

  // move the item "from" position "to" another position and notify the observers
  private def move(from: Position, to: Position): Unit = {
    println("Move " + from + " -> " + to)
  }

  // load a new playground
  def load(file: String) {
    timer.stop
    playground = playground.load(file)
    notifyObservers(new NewGame);
    timer.start
  }

  // A timer which code will be executed in the given interval
  // The method get all items from the playground which should be moved
  // Items which should be moved can be for example Players or Bots.
  val timer = Timer(50, true) {
    playground.player.foreach{
      	case (position, player) => {
      		notifyObservers(new Update);
      		println(player)
      		playground = playground.move(player)._2 
      	}
      }
  }
}
