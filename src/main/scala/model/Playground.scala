package main.scala.model

// Possible moves.
sealed trait Move

final case class PlayerToWay() extends Move
final case class PlayerToPortal() extends Move
final case class BotToWay() extends Move
final case class InvalidMove(reason : String) extends Move


class Playground(var items : Map[Position, Item]) {
    def getPlayers = items.filter(_._2.isInstanceOf[Player])
    def getBots = items.filter(_._2.isInstanceOf[Bot])
    
	def move(from : Position, to : Position) : Move = {
	  val fromItem = items.get(from)
	  val toItem = items.get(to)
	  if (None == fromItem)	return InvalidMove("position not exist : "+from)
	  if (None == toItem) return InvalidMove("position not exist : "+to)
	  
	  def doMove = {
		  items += (from -> fromItem.get.rebuild)
		  items += (to -> fromItem.get)
	  }
	  
	  // check for valid moves.
	  (fromItem.get, toItem.get) match {
	    case (player : Player, way : Way) 		=> doMove; PlayerToWay()
	    case (player : Player, portal : Portal) => doMove; PlayerToPortal()
	    case (bot : Bot, way : Way)				=> doMove; BotToWay()
	    case _ 				 					=> InvalidMove("Invalid Move")
	  }
	}
    
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
}