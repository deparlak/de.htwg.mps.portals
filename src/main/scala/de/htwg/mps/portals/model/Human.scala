package de.htwg.mps.portals.model

case class Human(
	   override val uuid : String, 
	   override val position : Position,
	   override val direction : Direction,
	   override val movementCost : Int) extends Player {
	override def toString = "1"
	def switchDirection(direction : Direction) = new Human(uuid, position, direction, movementCost)
	def validMove(movementCost : Int) = new Human(uuid, nextPosition, direction, movementCost)
	def invalidMove = new Human(uuid, position, Stay, movementCost)
	def paiyMovementCost() = new Human(uuid, position, direction, movementCost - 1);
	override def destroy(player : Player) = player match {
		case _ => false
	}
}
