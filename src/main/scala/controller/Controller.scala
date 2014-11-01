package main.scala.controller

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
	
	//
	private def move(from : Position, to : Position) : Unit = {
	  println("Move " + from + " -> " + to)
	  playground.move(from, to) match {
	    case moved : PlayerToWay 	=> notifyObservers(new Update);
	    case moved : PlayerToPortal	=> notifyObservers(new GameEnd);
	    case moved : BotToWay		=> notifyObservers(new Update);
	    case error : InvalidMove  	=> // ignore invalid moves.
	  }
	}
	
	def load (file : String) {
	  playground = playground.load(file)
	  notifyObservers(new NewGame);
	}
		
	Timer(50, true) {
	  playground.getMoves.foreach(println)
	  playground.getMoves.foreach(x => (x._1, x._2.nextMove) match {
	    case (position : Position, _ : Up) 	  => move(position, position.up)
	    case (position : Position, _ : Down)  => move(position, position.down)
	    case (position : Position, _ : Left)  => move(position, position.left)
	    case (position : Position, _ : Right) => move(position, position.right)
	    case _							      => println("No Move")
	  })
	}
}

// Timer which calls a anonym method cylic
object Timer {
  def apply(interval: Int, repeats: Boolean = true)(op: => Unit) {
    val timeOut = new javax.swing.AbstractAction() {
      def actionPerformed(e : java.awt.event.ActionEvent) = op
    }
    val t = new javax.swing.Timer(interval, timeOut)
    t.setRepeats(repeats)
    t.start()
  }
}