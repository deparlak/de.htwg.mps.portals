package de.htwg.mps.portals.swing

import scala.concurrent.future
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Random

import de.htwg.mps.portals.swing.util._
import de.htwg.mps.portals.model.Human
import de.htwg.mps.portals.model.Player
import de.htwg.mps.portals.model.Bot
import de.htwg.mps.portals.model.Direction
import de.htwg.mps.portals.view.UI
import de.htwg.mps.portals.util.Timer

// companion object to get Terrain instances, like a factory method.
object PlayerSprite {
  def apply(player : Player) : PlayerSprite = player match {
    case Human(_,_,_,_) => new HumanPlayerSprite
    case Bot(_,_,_,_,_) => new BotPlayerSprite
  }
}

trait PlayerSprite {
  val sprites : List[Sprite]
  val width = 32
  val height = 32
  val offset = 48

  // offset for sprite on spritesheet.
  def getDirectionOffset (player : Player) : Int = player.direction match {
    case de.htwg.mps.portals.model.Stay 	=> 0
    case de.htwg.mps.portals.model.Right 	=> 0
    case de.htwg.mps.portals.model.Left 	=> -32
    case de.htwg.mps.portals.model.Up 		=> -64
    case de.htwg.mps.portals.model.Down 	=> -96
  }
  
  // animation of the sprite as a future
  def animate(player : Player) : Future[Boolean] = Future {
    sprites foreach {case (sprite) => 
      sprite.visible = false
      sprite.move(player.nextPosition.x * width + offset, player.nextPosition.y * height)
      sprite.update(sprite.x, this.getDirectionOffset(player))
    }
     
    sprites foreach {case (sprite) => 
      sprite.visible = true
      Thread.sleep(10)
      sprite.visible = false
    }
    sprites.head.visible = true
    true
  }
}

class HumanPlayerSprite extends PlayerSprite {
  val image = "/sprite/default/player/Human.png"
  
  val sprites : List[Sprite] = List(
    new Sprite (image, width, height, 0, 0),
    new Sprite (image, width, height, -32, 0),
    new Sprite (image, width, height, -64, 0),
    new Sprite (image, width, height, -96, 0),
    new Sprite (image, width, height, -128, 0),
    new Sprite (image, width, height, -160, 0),
    new Sprite (image, width, height, -192, 0),
    new Sprite (image, width, height, 0, 0)
  )
}

class BotPlayerSprite extends PlayerSprite {
  val image = "/sprite/default/player/Bot.png"
  
  val sprites : List[Sprite] = List(
    new Sprite (image, width, height, 0, 0),
    new Sprite (image, width, height, -32, 0),
    new Sprite (image, width, height, -64, 0),
    new Sprite (image, width, height, -96, 0),
    new Sprite (image, width, height, -128, 0),
    new Sprite (image, width, height, -160, 0),
    new Sprite (image, width, height, -192, 0),
    new Sprite (image, width, height, 0, 0)
  )
}