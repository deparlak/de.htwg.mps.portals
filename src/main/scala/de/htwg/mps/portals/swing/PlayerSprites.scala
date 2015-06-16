package de.htwg.mps.portals.swing

import de.htwg.mps.portals.model.Bot
import de.htwg.mps.portals.model.Human
import de.htwg.mps.portals.model.Player

// companion object to get Terrain instances, like a factory method.
object PlayerSprites {
  def apply(player : Player) : PlayerSprite = player match {
    case Human(_,_,_,_) => new HumanPlayerSprite
    case Bot(_,_,_,_,_) => new BotPlayerSprite
  }
}

class HumanPlayerSprite extends PlayerSprite {
  val image = "/sprite/default/player/Human.png"
}

class BotPlayerSprite extends PlayerSprite {
  val image = "/sprite/default/player/Bot.png"
}