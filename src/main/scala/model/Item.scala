package main.scala.model

trait Item {
  def movable : Boolean
  def displaceable : Boolean
  def rebuild : Item
}