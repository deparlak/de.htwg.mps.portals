package de.htwg.mps.portals.swing

import de.htwg.mps.portals.model._
import de.htwg.mps.portals.swing.util._

object TerrainSprite {
  def apply(terrain : Terrain) : TerrainSprite = terrain match {
        case Wall 	=> new WallSprite
        case Grass 	=> new GrassSprite
        case Dust 	=> new DustSprite
  }
}

trait TerrainSprite {
  def sprite : Sprite
}
class WallSprite extends TerrainSprite {
  val sprite = new Sprite ("/sprite/default/red.png", 32, 32, 0, 0)
}
class GrassSprite extends TerrainSprite {
  val sprite = new Sprite ("/sprite/default/green.png", 32, 32, 0, 0)
}
class DustSprite extends TerrainSprite {
  val sprite = new Sprite ("/sprite/default/brown.png", 32, 32, 0, 0)
}
	
