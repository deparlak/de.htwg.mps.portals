package de.htwg.mps.portals.swing

import de.htwg.mps.portals.model._
import de.htwg.mps.portals.swing.util._

object TerrainSprite {
  def apply(terrain : Terrain) : TerrainSprite = terrain match {
        case T0 	=> new T0Sprite
        case T2 	=> new T2Sprite
        case T3 	=> new T3Sprite
        case T4 	=> new T4Sprite
        case T5 	=> new T5Sprite
  }
}

trait TerrainSprite {
  def sprite : Sprite
}
class T0Sprite extends TerrainSprite {
  val sprite = new Sprite ("/sprite/default/green.png", 32, 32, 0, 0)
}
class T2Sprite extends TerrainSprite {
  val sprite = new Sprite ("/sprite/default/black.png", 32, 32, 0, 0)
}
class T3Sprite extends TerrainSprite {
  val sprite = new Sprite ("/sprite/default/brown.png", 32, 32, 0, 0)
}
class T4Sprite extends TerrainSprite {
  val sprite = new Sprite ("/sprite/default/red.png", 32, 32, 0, 0)
}
class T5Sprite extends TerrainSprite {
  val sprite = new Sprite ("/sprite/default/blue.png", 32, 32, 0, 0)
}
	
