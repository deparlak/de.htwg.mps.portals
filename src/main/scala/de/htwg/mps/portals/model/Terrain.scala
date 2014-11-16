package de.htwg.mps.portals.model

sealed trait Terrain {
  def walkableBy(player : Player) = false
  def endGame = false
  def liveDecrease = 0
  def movementCost = 0
  def toString : String
}

// companion object to get Terrain instances, like a factory method.
object Terrain {
  def apply(char : Char) = char match {
    case '|' => Wall
    case 'P' => Portal
    case 'X' => Fire
    case '.' => Swamp
    case ' ' => Grass
    case _   => Grass
  }
}

case object Grass extends Terrain {
  override def toString = " "
  override def walkableBy(player : Player) = true
}

case object Fire extends Terrain {
  override def toString = "X"
  override def walkableBy(player : Player) = player match {
    case _ : Human => true
    case _		   => false
  }
}

case object Swamp extends Terrain {
  override def toString = "." 
  override def walkableBy(player : Player) = true
}

case object Wall extends Terrain {
  override def toString = "|"
}

case object Portal extends Terrain {
  override def toString = "P"
  override def walkableBy(player : Player) = player match {
    case _ : Human => true
    case _		   => false
  }
  override def endGame = true
}
