package main.scala.controller

import main.scala.util.Observable
import main.scala.model._

class Controller(val playground : Playground) extends Observable[Event]{
	def moveUp(position : Position) = move(position, position.up())
	def moveDown(position : Position) = move(position, position.down())
	def moveLeft(position : Position) = move(position, position.left())
	def moveRight(position : Position) = move(position, position.right())
	
	private def move(from : Position, to : Position) : Position = {
	  println("Move " + from + " -> " + to)
	  playground.move(from, to) match {
	    case moved : PlayerToWay 	=> notifyObservers(new Update); to
	    case moved : PlayerToPortal	=> notifyObservers(new GameEnd); to
	    case moved : BotToWay		=> notifyObservers(new Update); to
	    case error : InvalidMove  	=> println(error.reason); from
	  }
	}
}