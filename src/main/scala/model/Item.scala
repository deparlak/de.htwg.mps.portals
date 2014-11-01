package main.scala.model

sealed trait Direction
final case class Left() extends Direction
final case class Right() extends Direction
final case class Up() extends Direction
final case class Down() extends Direction
final case class NoMove() extends Direction

trait Item {
  def nextMove : Direction = NoMove()
  def onValidMove : Item
  def onInvalidMove : Item
  def rebuild : Item
  def getId : String = null
}

case class Player(val id : String, val move : Direction) extends Item {
  override def nextMove = move
  def onValidMove = new Player(id, NoMove())
  def onInvalidMove = new Player(id, NoMove())  
  def rebuild = new Way
  override def getId = id
  override def toString = id
}

case class Portal() extends Item {
  def onValidMove = new Portal
  def onInvalidMove = new Portal
  def rebuild = new Way
  override def toString = "P"
}

case class Wall() extends Item {
  def onValidMove = new Wall
  def onInvalidMove = new Wall
  def rebuild = new Wall
  override def toString = "|"
}

case class Bot(val move : Direction) extends Item {
  override def nextMove = move
  def onValidMove = new Bot(move)
  def onInvalidMove = new Bot(switchDirection(move))
  private def switchDirection(actual : Direction) : Direction = actual match {
    case _ : Left 	=> Right()
    case _ : Right	=> Up()
    case _ : Up		=> Down()
    case _ 		    => Left()
  }
  def rebuild = new Way
  override def toString = "B"
}

case class Way() extends Item {
  def onValidMove = new Way
  def onInvalidMove = new Portal
  def rebuild = new Way
  override def toString = " "
}