package de.htwg.mps.portals.swing

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._
import de.htwg.mps.portals.model.Player
import de.htwg.mps.portals.swing.util._
import de.htwg.mps.portals.model._

// companion object to get Terrain instances, like a factory method.
object PlayerSprites {
  def apply(player : Player) : PlayerSprite = player match {
    case Human(_,_,_,_) => new HumanSprite
case Bot1(_,_,_,_,_) => new Bot1Sprite
case Bot2(_,_,_,_,_) => new Bot2Sprite
  }
}

class HumanSprite extends PlayerSprite {
  val image = "/sprite/default/player/green.png"
  val sprites: List[Sprite] = List(
    new Sprite(image, width, height, 0, 0),
    new Sprite(image, width, height, -32, 0),
    new Sprite(image, width, height, -64, 0),
    new Sprite(image, width, height, -96, 0),
    new Sprite(image, width, height, -128, 0),
    new Sprite(image, width, height, -160, 0),
    new Sprite(image, width, height, -192, 0),
    new Sprite(image, width, height, 0, 0))
}

class Bot1Sprite extends PlayerSprite {
  val image = "/sprite/default/player/blue.png"
  val sprites: List[Sprite] = List(
    new Sprite(image, width, height, 0, 0),
    new Sprite(image, width, height, -32, 0),
    new Sprite(image, width, height, -64, 0),
    new Sprite(image, width, height, -96, 0),
    new Sprite(image, width, height, -128, 0),
    new Sprite(image, width, height, -160, 0),
    new Sprite(image, width, height, -192, 0),
    new Sprite(image, width, height, 0, 0))
}
class Bot2Sprite extends PlayerSprite {
  val image = "/sprite/default/player/green.png"
  val sprites: List[Sprite] = List(
    new Sprite(image, width, height, 0, 0),
    new Sprite(image, width, height, -32, 0),
    new Sprite(image, width, height, -64, 0),
    new Sprite(image, width, height, -96, 0),
    new Sprite(image, width, height, -128, 0),
    new Sprite(image, width, height, -160, 0),
    new Sprite(image, width, height, -192, 0),
    new Sprite(image, width, height, 0, 0))
}
