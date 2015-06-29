package de.htwg.mps.portals.model

sealed trait Terrain {
  def walkableBy(player : Player) = false
  def endGame = false
  def movementCost = 0
  def toString : String
}

// companion object to get Terrain instances, like a factory method.
object Terrain {
  def apply(char : Char) = char match {
case '|' => Wall
case ' ' => Grass
case '.' => Dust
  }
}

case object Wall extends Terrain {
  override def toString = "|"
  override def movementCost = 1
  override def endGame = false
  override def walkableBy(player : Player) = player match {
    case _ : Human => false
    case _ : Bot1 => true
    case _		   => false
  }
}
case object Grass extends Terrain {
  override def toString = " "
  override def movementCost = 1
  override def endGame = false
  override def walkableBy(player : Player) = player match {
    case _ : Human => true
    case _ : Bot1 => true
    case _		   => false
  }
}
case object Dust extends Terrain {
  override def toString = "."
  override def movementCost = 4
  override def endGame = false
  override def walkableBy(player : Player) = player match {
    case _ : Human => true
    case _ : Bot1 => true
    case _		   => false
  }
}
