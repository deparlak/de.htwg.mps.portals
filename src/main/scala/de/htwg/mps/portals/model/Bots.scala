package de.htwg.mps.portals.model

case class Bot1(
  override val uuid : String,
  override val position : Position,
  override val direction : Direction,
  val lastValid : Direction,
  override val movementCost : Int) extends Bot {
	override def toString = "B"
	override def destroy(player : Player) = player match {
	  	case (player : Human) => true
		case _ => false
	}
	def validMove(movementCost : Int) = new Bot1(uuid, nextPosition, direction, direction, movementCost)
	def invalidMove = new Bot1(uuid, position, switchDirection(lastValid, direction), direction, movementCost)
	def paiyMovementCost() = new Bot1(uuid, position, direction, direction, movementCost - 1)
}

case class Bot2(
  override val uuid : String,
  override val position : Position,
  override val direction : Direction,
  val lastValid : Direction,
  override val movementCost : Int) extends Bot {
	override def toString = "T"
	override def destroy(player : Player) = player match {
	  	case (player : Human) => true
		case _ => false
	}
	def validMove(movementCost : Int) = new Bot2(uuid, nextPosition, direction, direction, movementCost)
	def invalidMove = new Bot2(uuid, position, switchDirection(lastValid, direction), direction, movementCost)
	def paiyMovementCost() = new Bot2(uuid, position, direction, direction, movementCost - 1)
}

