package de.htwg.mps.portals.swing

import de.htwg.mps.portals.swing.util._
import de.htwg.mps.portals.model.Human
import de.htwg.mps.portals.model.Player
import de.htwg.mps.portals.model.Bot
import de.htwg.mps.portals.model.Direction
import de.htwg.mps.portals.view.UI

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
  def animate(player : Player)
}

class HumanPlayerSprite extends PlayerSprite {
  val sprite = new Sprite ("/sprite/default/player/Human.png", width, height, 0, 0)
  def animate(player : Player) {
    sprite.move(player.nextPosition.x * 32 + offset, player.nextPosition.y * 32)
    
    val direction = player.direction match {
      case de.htwg.mps.portals.model.Stay 	=> 0
      case de.htwg.mps.portals.model.Right 	=> 0
      case de.htwg.mps.portals.model.Left 	=> -32
      case de.htwg.mps.portals.model.Up 	=> -64
      case de.htwg.mps.portals.model.Down 	=> -96
    }
    for( a <- 1 to 1000){
	    sprite.update(-32,  direction)
	    sprite.update(-64,  direction)
	    sprite.update(-96,  direction)
	    sprite.update(-128, direction)
	    sprite.update(-160, direction)
	    sprite.update(-192, direction)
	    sprite.update(-224, direction)
	//    sprite.update(0,    direction)
    }

  }
}

class BotPlayerSprite extends PlayerSprite {
  val sprite = new Sprite ("/sprite/default/player/Bot.png", 32, 32, 0, 0)
  def animate(player : Player) {
    sprite.move(player.nextPosition.x * 32 + offset, player.nextPosition.y * 32)
  }
}

class EmptySprite extends PlayerSprite {
  val sprite = new Sprite ("/sprite/default/white.png", 32, 32, 0, 0)
  def animate(player : Player) {}
}