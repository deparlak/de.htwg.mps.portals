package main.scala.controller

import main.scala.util.Observable
import main.scala.model.Playground
import main.scala.model.Position
import main.scala.model.Move
import main.scala.model.PlayerMove
import main.scala.model.InvalidMove
import main.scala.model.InvalidMove
import main.scala.model.Update
import main.scala.model.Event

class Controller(var playground : Playground) extends Observable[Event]{
	def moveUp(position : Position) = move(position, position.up())
	def moveDown(position : Position) = move(position, position.down())
	def moveLeft(position : Position) = move(position, position.left())
	def moveRight(position : Position) = move(position, position.right())
	
	private def move(from : Position, to : Position) : Position = {
	  println("Move " + from + " -> " + to)
	  playground.move(from, to) match {
	    case moved : PlayerMove	 	=> notifyObservers(new Update); moved.to
	    case error : InvalidMove  	=> println(error.reason); from
	  }
	}
}