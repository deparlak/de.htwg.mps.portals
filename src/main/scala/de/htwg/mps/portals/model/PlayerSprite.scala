package de.htwg.mps.portals.swing

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

import de.htwg.mps.portals.model.Player
import de.htwg.mps.portals.swing.util._

trait PlayerSprite {
  val sprites: List[Sprite]
  val image: String
  val width = 32
  val height = 32
  val offset = 48

  // offset for sprite on spritesheet.
  def getDirectionOffset(player: Player): Int = player.direction match {
    case de.htwg.mps.portals.model.Stay  => 0
    case de.htwg.mps.portals.model.Right => 0
    case de.htwg.mps.portals.model.Left  => -32
    case de.htwg.mps.portals.model.Up    => -64
    case de.htwg.mps.portals.model.Down  => -96
  }
  

  // animation of the sprite as a future
  def animate(player: Player): Future[Boolean] = Future {
    sprites foreach {
      case (sprite) =>
        sprite.visible = false
        sprite.move(player.nextPosition.x * width + offset, player.nextPosition.y * height)
        sprite.update(sprite.x, this.getDirectionOffset(player))
    }

    sprites foreach {
      case (sprite) =>
        sprite.visible = true
        Thread.sleep(10)
        sprite.visible = false
    }
    sprites.head.visible = true
    true
  }
}