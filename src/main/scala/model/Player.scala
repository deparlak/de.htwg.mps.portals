package main.scala.model

class Player extends Item {
  def movable = true
  def displaceable = true
  def rebuild = new Way
  override def toString = "O"
}