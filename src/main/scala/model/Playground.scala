package main.scala.model

sealed trait Move

final case class PlayerMove(from : Position, fromItem : Item, to : Position, toItem : Item) extends Move
final case class InvalidMove(reason : String) extends Move

class Playground(var items : Map[Position, Item]) {
    def getPlayers = items.filter(_._2.isInstanceOf[Player])
    
	def move(from : Position, to : Position) : Move = {
	  var fromItem = items.get(from)
	  var toItem = items.get(to)
	  if (None == fromItem)	return InvalidMove("position not exist : "+from)
	  if (None == toItem) return InvalidMove("position not exist : "+to)
	  if (!fromItem.get.movable) return InvalidMove("Not allowed to move from : "+from)
	  if (!toItem.get.displaceable) return InvalidMove("Not allowed to move to : "+to)

	  items += (from -> fromItem.get.rebuild)
	  items += (to -> fromItem.get)
	  	  
	  PlayerMove(from, fromItem.get, to, toItem.get)
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