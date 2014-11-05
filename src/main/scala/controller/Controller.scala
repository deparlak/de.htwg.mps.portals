package main.scala.controller

import main.scala.util.Timer
import main.scala.util.Observable
import main.scala.model._

class Controller(var playground : Playground) extends Observable[Event] {
    // move a item with the specified id. The item will not be move directly. Otherwise
    // the direction will be set to which we like to move and a cyclic method will do
    // the move after a specified timeout
	def moveUp(player : String) = playground.setMove(player, Up())
	def moveDown(player : String) = playground.setMove(player, Down())
	def moveLeft(player : String) = playground.setMove(player, Left())
	def moveRight(player : String) = playground.setMove(player, Right())
	
	// move the item "from" position "to" another position and notify the observers
	private def move(from : Position, to : Position) : Unit = {
	  println("Move " + from + " -> " + to)
	  playground.move(from, to) match {
	    case moved : PlayerToWay 	=> notifyObservers(new Update);
	    case moved : PlayerToPortal	=> notifyObservers(new GameEnd);
	    case moved : BotToWay		=> notifyObservers(new Update);
	    case moved : BotToPlayer	=> timer.stop; notifyObservers(new GameLost);
	    case error : InvalidMove  	=> // ignore invalid moves.
	  }
	}
	
	// load a new playground
	def load (file : String) {
	  playground = playground.load(file)
	  notifyObservers(new NewGame);
	}
	
	// A timer which code will be executed in the given interval
	// The method get all items from the playground which should be moved
	// Items which should be moved can be for example Players or Bots.
	val timer = Timer(50, true) {
	  playground.getMoves.foreach(x => (x._1, x._2.nextMove) match {
	    case (position : Position, _ : Up) 	  => move(position, position.up)
	    case (position : Position, _ : Down)  => move(position, position.down)
	    case (position : Position, _ : Left)  => move(position, position.left)
	    case (position : Position, _ : Right) => move(position, position.right)
	    case _							      => println("No Move")
	  })
	}
}
