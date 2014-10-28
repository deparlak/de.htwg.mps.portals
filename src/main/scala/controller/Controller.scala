package main.scala.controller

import main.scala.util.Observable
import main.scala.model._

class Controller(var playground : Playground) extends Observable[Event] {
	def moveUp(position : Position) = move(position, position.up())
	def moveDown(position : Position) = move(position, position.down())
	def moveLeft(position : Position) = move(position, position.left())
	def moveRight(position : Position) = move(position, position.right())
	
	private def move(from : Position, to : Position) = {
	  println("Move " + from + " -> " + to)
	  playground.move(from, to) match {
	    case moved : PlayerToWay 	=> notifyObservers(new Update);
	    case moved : PlayerToPortal	=> notifyObservers(new GameEnd);
	    case moved : BotToWay		=> notifyObservers(new Update);
	    case error : InvalidMove  	=> println(error.reason); from
	  }
	}
	
	def load (file : String) {
	  playground = playground.load(file)
	  notifyObservers(new NewGame);
	}
}