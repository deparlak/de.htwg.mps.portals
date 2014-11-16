package de.htwg.mps.portals.model

sealed trait Direction
final case object Left extends Direction
final case object Right extends Direction
final case object Up extends Direction
final case object Down extends Direction
final case object Stay extends Direction

trait Player {
  def uuid : String = null
  def position : Position
  def direction : Direction
  def switchDirection(direction : Direction) : Player
  def nextPosition : Position = direction match {    
    case Up 	=> position.up
    case Down	=> position.down
    case Left 	=> position.left
    case Right 	=> position.right
    case Stay   => position
  }
  def validMove : Player
  def invalidMove : Player
}

// companion object to get Terrain instances, like a factory method.
object Player {
  def apply(char : Char, position : Position) : Option[Player] = char match {
    case '1' => Some(Human("1", position, Stay))
    case 'B' => Some(Bot(java.util.UUID.randomUUID.toString, position, Stay))
    case _	 => None
  }
}

case class Human(
    override val uuid : String, 
    override val position : Position,
    override val direction : Direction) extends Player {
  override def toString = "1"
  def switchDirection(direction : Direction) = new Human(uuid, position, direction)
  def validMove = new Human(uuid, nextPosition, direction)
  def invalidMove = new Human(uuid, position, direction)
}

case class Bot(override val uuid : String,
    override val position : Position,
    override val direction : Direction) extends Player {
  override def toString = "B"
  def switchDirection(direction : Direction) = new Human(uuid, position, direction)
  def validMove = new Bot(uuid, nextPosition, direction)
  def invalidMove = new Bot(uuid, position, direction)
}