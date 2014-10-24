package main.scala.model

import main.scala.util.Ordered

class Position(val x : Int, val y : Int) extends Ordered[Position] {
    override def hashCode = 41 * (41 + x) + y
    override def equals(other: Any) = other match { 
      case that: Position => this.x == that.x && this.y == that.y 
      case _ => false 
    }
    override def toString() = "(" + x + "," + y + ")"
    
    override def compare(that : Position) = {
      val diff = this.y - that.y
      if (0 != diff) diff else this.x - that.x
    }
    
    def up() = new Position(x, y - 1)
    def down() = new Position(x, y + 1)
    def left() = new Position(x - 1, y)
    def right() = new Position(x + 1, y)
}