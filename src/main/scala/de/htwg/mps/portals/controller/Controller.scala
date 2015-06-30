package de.htwg.mps.portals.controller

import de.htwg.mps.portals.model._
import de.htwg.mps.portals.util.Observable
import de.htwg.mps.portals.util.Timer
import de.htwg.mps.portals.util.Level
import com.escalatesoft.subcut.inject.BindingModule
import com.escalatesoft.subcut.inject.Injectable

// Events which could be fired by the controller
sealed trait Event
final case class Update(move : Moved) extends Event
final case class Wait(move : PayMovement) extends Event
final case class GameLost() extends Event
final case class GameWon() extends Event
final case class NewGame() extends Event


class Controller(var playground: Playground = new Playground) extends IController {
  // move a player with the specified uuid. The player will not be move directly. Otherwise
  // the direction will be set to which we like to move and a cyclic method will do
  // the move after a specified timeout
  def moveUp(uuid : String) = playground = playground.setMove(uuid, Up);
  def moveDown(uuid : String) = playground = playground.setMove(uuid, Down);
  def moveLeft(uuid : String) = playground = playground.setMove(uuid, Left);
  def moveRight(uuid : String) = playground = playground.setMove(uuid, Right);

  // move the item "from" position "to" another position and notify the observers
  private def move(player : Player): Unit = if (timer.isRunning) {
	//notifyObservers(new Update);
	playground.move(player) match {
	  case (m : InvalidMove, p : Playground) => playground = p;
	  case (m : Moved, p : Playground)		 => playground = p; onMoved(m)
	  case (m : PayMovement, p : Playground) => playground = p; onPayMovement(m)
	  case (m : Destroyed, p : Playground)	 => playground = p; onDestroyed(m)
	}
  }
  
  def onDestroyed(destroyed : Destroyed) {
	  if (!destroyed.destroyed.isInstanceOf[Player]) {
	    timer.stop
	    notifyObservers(GameLost())
	  }
  }
  
  def onPayMovement(payMovement : PayMovement) {
    notifyObservers(Wait(payMovement))
  }
  
  def onMoved(move : Moved) {
    notifyObservers(Update(move))
    if (move.terrain.endGame) {
      timer.stop
      notifyObservers(GameWon())
    }
  }

  // load a new playground
  def load(file: String = new Level().firstNormalLevel) {
    timer.stop
    playground = playground.load(file)
    notifyObservers(new NewGame);
    timer.start
  }

  // A timer which code will be executed in the given interval
  // The method get all items from the playground which should be moved
  // Items which should be moved can be for example Players or Bots.
  val timer = Timer(50, true) {
    timerMethod
  }

  def timerMethod = playground.player.foreach {
    case (position, player) => { if (player.direction != Stay) move(player) }
  }
}
