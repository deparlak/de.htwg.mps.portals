package de.htwg.mps.portals.model

sealed trait Terrain {
  def walkableBy(player : Player) = false
  def endGame = false
  def movementCost = 0
  def toString : String
}

object DefaultTerrain {
def get = T0
}

// companion object to get Terrain instances, like a factory method.
object Terrain {
  def apply(char : Char) = char match {
case ' ' => T0
case '|' => T2
case '.' => T3
case 'X' => T4
case 'P' => T5
	case _ => T0
  }
}

case object T0 extends Terrain {
  override def toString = " "
  override def movementCost = 1
  override def endGame = false
  override def walkableBy(player : Player) = player match {
    case _ : Human => true
    case _ : B1 => true
    case _ : B2 => true
    case _		   => false
  }
}
case object T2 extends Terrain {
  override def toString = "|"
  override def movementCost = 1
  override def endGame = false
  override def walkableBy(player : Player) = player match {
    case _ : Human => false
    case _		   => false
  }
}
case object T3 extends Terrain {
  override def toString = "."
  override def movementCost = 4
  override def endGame = false
  override def walkableBy(player : Player) = player match {
    case _ : Human => true
    case _		   => false
  }
}
case object T4 extends Terrain {
  override def toString = "X"
  override def movementCost = 4
  override def endGame = false
  override def walkableBy(player : Player) = player match {
    case _ : Human => false
    case _		   => false
  }
}
case object T5 extends Terrain {
  override def toString = "P"
  override def movementCost = 0
  override def endGame = true
  override def walkableBy(player : Player) = player match {
    case _ : Human => true
    case _		   => false
  }
}
