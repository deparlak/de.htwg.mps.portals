package de.htwg.mps.portals.model

sealed trait Direction
final case object Left extends Direction
final case object Right extends Direction
final case object Up extends Direction
final case object Down extends Direction
final case object Stay extends Direction

trait Player {
  def uuid : String = null
  def direction : Direction
  def switchDirection(direction : Direction) : Player
}

// companion object to get Terrain instances, like a factory method.
object Player {
  def apply(char : Char) : Option[Player] = char match {
    case '1' => Some(Human(java.util.UUID.randomUUID.toString, Stay))
    case 'B' => Some(Bot(java.util.UUID.randomUUID.toString, Stay))
    case _	 => None
  }
}

case class Human(override val uuid : String, override val direction : Direction) extends Player {
  override def toString = "1"
  def switchDirection(direction : Direction) = new Human(uuid, direction)
}

case class Bot(override val uuid : String, override val direction : Direction) extends Player {
  override def toString = "B"
  def switchDirection(direction : Direction) = new Human(uuid, direction)
}