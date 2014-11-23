package de.htwg.mps.portals.util

import de.htwg.mps.portals.model._

object TerrainSprite {
  def apply(terrain : Terrain) : TerrainSprite = terrain match {
    case Wall 	=> new WallSprite
    case Portal => new PortalSprite
    case Fire 	=> new FireSprite
    case Swamp 	=> new SwampSprite
    case Grass	=> new GrassSprite
  }
}

trait TerrainSprite {
  def sprite : Sprite
  def enterAnimation(player : Player)
  def leaveAnimation(player : Player)
}

class WallSprite extends TerrainSprite {
  val sprite = new Sprite ("res/sprite/default/wall.png", 32, 32, 0, 0)
  def enterAnimation(player : Player) = {}
  def leaveAnimation(player : Player) = {}
}

class PortalSprite extends TerrainSprite {
  val sprite = new Sprite ("res/sprite/default/portal.png", 32, 32, 0, 0)
  def enterAnimation(player : Player) = { }
  def leaveAnimation(player : Player) = { }
}

class FireSprite extends TerrainSprite {
  val sprite = new Sprite ("res/sprite/default/fire.png", 32, 32, 0, 0)
  def enterAnimation(player : Player) = sprite.update(-32, if (player.isInstanceOf[Bot]) -32 else 0)
  def leaveAnimation(player : Player) = sprite.update(0, if (player.isInstanceOf[Bot]) -32 else 0)
}

class SwampSprite extends TerrainSprite {
  val sprite = new Sprite ("res/sprite/default/swamp.png", 32, 32, 0, 0)
  def enterAnimation(player : Player) = sprite.update(-32, if (player.isInstanceOf[Bot]) -32 else 0)
  def leaveAnimation(player : Player) = sprite.update(0, if (player.isInstanceOf[Bot]) -32 else 0)
}

class GrassSprite extends TerrainSprite {
  val sprite = new Sprite ("res/sprite/default/grass.png", 32, 32, 0, 0)
  def enterAnimation(player : Player) = sprite.update(-32, if (player.isInstanceOf[Bot]) -32 else 0)
  def leaveAnimation(player : Player) = sprite.update(0, if (player.isInstanceOf[Bot]) -32 else 0)
}