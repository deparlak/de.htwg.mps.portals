package main.scala.model

trait Item {
  def rebuild : Item
}

case class Player() extends Item {
  def rebuild = new Way
  override def toString = "O"
}

case class Portal() extends Item {
  def rebuild = new Portal
  override def toString = "P"
}

case class Wall() extends Item {
  def rebuild = new Wall
  override def toString = "|"
}

case class Bot() extends Item {
  def rebuild = new Way
  override def toString = "B"
}

case class Way() extends Item {
  def rebuild = new Way
  override def toString = " "
}