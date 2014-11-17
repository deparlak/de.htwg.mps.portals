package de.htwg.mps.portals.model

sealed trait Direction
final case object Left extends Direction
final case object Right extends Direction
final case object Up extends Direction
final case object Down extends Direction
final case object Stay extends Direction

sealed trait Player {
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
  def destroy(player : Player) = false
}

// companion object to get Terrain instances, like a factory method.
object Player {
  def HumanPlayer1 = "1"
  
  def apply(char : Char, position : Position) : Option[Player] = char match {
    case '1' => Some(Human(HumanPlayer1, position, Stay))
    case 'B' => Some(Bot(java.util.UUID.randomUUID.toString, position, Up, Stay))
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
  def invalidMove = new Human(uuid, position, Stay)
}

case class Bot(override val uuid : String,
    override val position : Position,
    override val direction : Direction,
    val lastValid : Direction) extends Player {
  override def toString = "B"
  override def destroy(player : Player) = player match {
    case (player : Human) => true
    case (bot : Bot)	  => false
  }
  def switchDirection(direction : Direction) = new Human(uuid, position, direction)
  def validMove = new Bot(uuid, nextPosition, direction, direction)
  def invalidMove = new Bot(uuid, position, switchDirection(lastValid, direction), direction)
  private def switchDirection(lastValid : Direction, lastInvalid : Direction) : Direction = (lastValid, lastInvalid) match {
    case (Up, Up) 		=> Left
    case (Up, Left) 	=> Right
    case (Up, _) 		=> Down
    case (Down, Down) 	=> Left
    case (Down, Left) 	=> Right
    case (Down, _) 	    => Up
    case (Left, Left) 	=> Up
    case (Left, Up) 	=> Down
    case (Left, _) 	    => Right
    case (Right, Right) => Up
    case (Right, Up) 	=> Down
    case (Right, _) 	=> Left
    case (Stay, Up) 	=> Left
    case (Stay, Left) 	=> Right
    case (Stay, Right)	=> Down
    case (Stay, Down) 	=> Up
    case (Stay, Stay) 	=> Up
  }
}