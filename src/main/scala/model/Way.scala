package main.scala.model

class Way extends Item {
  def movable = false
  def displaceable = true
  def rebuild = new Way
  override def toString = " "
}