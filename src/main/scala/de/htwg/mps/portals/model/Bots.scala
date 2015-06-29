package de.htwg.mps.portals.model

case class B1(
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
	def validMove(movementCost : Int) = new B1(uuid, nextPosition, direction, direction, movementCost)
	def invalidMove = new B1(uuid, position, switchDirection(lastValid, direction), direction, movementCost)
	def paiyMovementCost() = new B1(uuid, position, direction, direction, movementCost - 1)
}

case class B2(
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
	def validMove(movementCost : Int) = new B2(uuid, nextPosition, direction, direction, movementCost)
	def invalidMove = new B2(uuid, position, switchDirection(lastValid, direction), direction, movementCost)
	def paiyMovementCost() = new B2(uuid, position, direction, direction, movementCost - 1)
}

