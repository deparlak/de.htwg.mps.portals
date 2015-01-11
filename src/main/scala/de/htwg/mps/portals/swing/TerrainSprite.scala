package de.htwg.mps.portals.swing

import de.htwg.mps.portals.model._
import de.htwg.mps.portals.swing.util._

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
}

class WallSprite extends TerrainSprite {
  val sprite = new Sprite ("/sprite/default/wall.png", 32, 32, 0, 0)
}

class PortalSprite extends TerrainSprite {
  val sprite = new Sprite ("/sprite/default/portal.png", 32, 32, 0, 0)
}

class FireSprite extends TerrainSprite {
  val sprite = new Sprite ("/sprite/default/fire.png", 32, 32, 0, 0)
}

class SwampSprite extends TerrainSprite {
  val sprite = new Sprite ("/sprite/default/swamp.png", 32, 32, 0, 0)
}

class GrassSprite extends TerrainSprite {
  val sprite = new Sprite ("/sprite/default/grass.png", 32, 32, 0, 0)
}