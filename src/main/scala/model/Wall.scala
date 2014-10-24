package main.scala.model

class Wall extends Item {
  def movable = false
  def displaceable  = false
  def rebuild = new Wall
  override def toString = "|"
}