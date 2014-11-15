package de.htwg.mps.portals.model

import scala.io.Source._

// Possible moves.
sealed trait Move

final case class PlayerToWay() extends Move
final case class PlayerToPortal() extends Move
final case class BotToWay() extends Move
final case class BotToPlayer() extends Move
final case class InvalidMove(reason: String) extends Move

class Playground(val terrain : Map[Position, Terrain] = Map(), val player : Map[Position, Player] = Map()) {
  
  def setMove(uuid : String, direction : Direction) : Playground = {
    val player = this.player.map{
      case (position, player) => if (uuid == player.uuid) (position, player.switchDirection(direction)) else (position, player) 
    }
    new Playground (terrain, player)
  }
  
  // move the following player
  def move(player : String) : Playground = {
    new Playground
  }

  // the complete playground as a formated string
  override def toString() = {
    var str = ""
    var y = 0
    val sorted = terrain.toSeq.sortWith(_._1 < _._1)
    sorted.foreach(x => {
      if (x._1.y > y) { y += 1; str += "\n" }
      str += x._2
    })
    str
  }

  // load a new playground
  def load(file: String): Playground = {
    val source = fromFile(file)
    val arr = source.map(_.toChar).toArray
    var terrain: Map[Position, Terrain] = Map()
    var x = 0
    var y = 0

    arr.foreach(input =>
      input match {
        case '\n' => x = 0; y += 1
        case '\r' => None
        case _ => terrain += (new Position(x, y) -> Terrain(input)); x += 1
      })
    source.close()
    new Playground(terrain)
  }
}