package main.scala.model

import scala.io.Source._
import main.scala.model._

// Possible moves.
sealed trait Move

final case class PlayerToWay() extends Move
final case class PlayerToPortal() extends Move
final case class BotToWay() extends Move
final case class InvalidMove(reason : String) extends Move


class Playground(var items : Map[Position, Item]) {
    // get all Items which should move in any direction.
    // These Items return with the nextMove method something else than NoMove
    def getMoves = items.filter(!_._2.nextMove.isInstanceOf[NoMove])
    
    // set the direction of the next move of a item with the specified id. 
    def setMove(id : String, move : Direction) : Unit = {
    	items.foreach(x => if(x._2.getId == id) items += (x._1 -> Player(id, move)))
    }
     
    // move the item "from" position "to" another position
	def move(from : Position, to : Position) : Move = {
	  val fromItem = items.get(from)
	  val toItem = items.get(to)
	  if (None == fromItem)	{invalidMove; return InvalidMove("position not exist : "+from)}
	  if (None == toItem) {invalidMove; return InvalidMove("position not exist : "+to)}
	  
	  def validMove = {
		  items += (from -> fromItem.get.rebuild)
		  items += (to -> fromItem.get.onValidMove)
	  }
	  
	  def invalidMove = {
		  items += (from -> fromItem.get.onInvalidMove)
	  }
	  
	  // check for valid moves.
	  (fromItem.get, toItem.get) match {
	    case (player : Player, way : Way) 		=> validMove; 	PlayerToWay()
	    case (player : Player, portal : Portal) => validMove; 	PlayerToPortal()
	    case (bot : Bot, way : Way)				=> validMove; 	BotToWay()
	    case _ 				 					=> invalidMove; InvalidMove("Invalid Move")
	  }
	}
    
	// the complete playground as a formated string
    override def toString() = {
      var str = ""
      var y = 0
      val sorted = items.toSeq.sortWith(_._1 < _._1)
      sorted.foreach(x => {
      	  if (x._1.y > y) {y+=1; str += "\n"}
    	  str += x._2
      	}
      )
      str
    }
    
    // load a new playground
    def load(file : String) : Playground = {
      val source = fromFile(file)
	  val arr = source.map(_.toChar).toArray
	  var items : Map[Position, Item] = Map()
	  var x = 0
	  var y = 0
	  
	  arr.foreach( input => 
	  	input match {
	  	  case '|' 		=> items += (new Position(x, y) -> new Wall); x += 1
	  	  case '1'      => items += (new Position(x, y) -> new Player("1", NoMove())); x += 1
	  	  case ' '      => items += (new Position(x, y) -> new Way); x += 1
	  	  case 'P'      => items += (new Position(x, y) -> new Portal); x += 1
	  	  case 'B'		=> items += (new Position(x, y) -> new Bot(Left())); x += 1
	  	  case '\n'		=> x = 0; y += 1
	  	  case '\r'     => None
	      case _ 		=> print("TODO Exception return, because of invalid character")
	  	}
	  )
	  source.close()
	  new Playground(items)
    }
}