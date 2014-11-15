package de.htwg.mps.portals.controller

import de.htwg.mps.portals.model._
import de.htwg.mps.portals.model.Update
import de.htwg.mps.portals.util.Observable
import de.htwg.mps.portals.util.Timer

class Controller(var playground: Playground) extends Observable[Event] {
  // move a item with the specified id. The item will not be move directly. Otherwise
  // the direction will be set to which we like to move and a cyclic method will do
  // the move after a specified timeout
  def moveUp(player: String) = ""
  def moveDown(player: String) = ""
  def moveLeft(player: String) = ""
  def moveRight(player: String) = ""

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

  }
}
