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
    case _				=> new EmptySprite
  }
}

trait PlayerSprite {
  val width = 32
  val height = 32
  val offset = 48
  def sprite : Sprite
  def animate (player : Player) : Future[Boolean]
  def getDirectionOffset (player : Player) : Int = player.direction match {
      case de.htwg.mps.portals.model.Stay 	=> 0
      case de.htwg.mps.portals.model.Right 	=> 0
      case de.htwg.mps.portals.model.Left 	=> -32
      case de.htwg.mps.portals.model.Up 	=> -64
      case de.htwg.mps.portals.model.Down 	=> -96
    }
}

class HumanPlayerSprite extends PlayerSprite {
  val sprite = new Sprite ("/sprite/default/player/Human.png", width, height, 0, 0)
  def animate(player : Player) : Future[Boolean] = Future {
    
    for( x <- 1 until 8){
      sprite.move(player.nextPosition.x * 32 + offset, player.nextPosition.y * 32)
      sprite.update(-32 * x, this.getDirectionOffset(player))
      Thread.sleep(10)
    }
    true
  }
}

class BotPlayerSprite extends PlayerSprite {
  val sprite = new Sprite ("/sprite/default/player/Bot.png", 32, 32, 0, 0)
  def animate(player : Player) : Future[Boolean] = Future {
    
    for( x <- 1 until 8){
      sprite.move(player.nextPosition.x * 32 + offset, player.nextPosition.y * 32)
      sprite.update(-32 * x, this.getDirectionOffset(player))
      Thread.sleep(10)
    }
    true
  }
}

class EmptySprite extends PlayerSprite {
  val sprite = new Sprite ("/sprite/default/white.png", 32, 32, 0, 0)
  def animate(player : Player) : Future[Boolean] = Future {
    true
  }
}