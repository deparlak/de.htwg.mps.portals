package de.htwg.mps.portals.view

import scala.swing._
import scala.swing.event._
import scala.reflect.io.Path
import de.htwg.mps.portals.util.Observer
import de.htwg.mps.portals.model.Player
import de.htwg.mps.portals.controller.Event
import de.htwg.mps.portals.controller._
import de.htwg.mps.portals.model.Position
import de.htwg.mps.portals.swing.TerrainSprite
import de.htwg.mps.portals.swing.util.NullPanel
import de.htwg.mps.portals.swing.PlayerSprite
import de.htwg.mps.portals.swing.util.Sprite


class Gui(val controller: Controller) extends Observer[Event] {
  controller.add(this)
  val playerList = scala.collection.mutable.HashMap[String, PlayerSprite]()
  val offset = 48
  val output = new FlowPanel()
  val ui = new UI(controller, output)
  var sprites = List()
  
  def update(e: Event) = {
    e match {
      case x: NewGame  => newGame()
      case x: Update   => update(x.move.player)
      case x: Wait	   => None
      case x: GameWon  => ui.gameWon
      case x: GameLost => ui.gameLost
    }
  }
  
  def update(player : Player) = {
    val visual = playerList.get(player.uuid).get

    visual.animate(player)
  }



  def newGame() {
    val terrain = controller.playground.terrain.map{ case(position, terreain) => position -> TerrainSprite(terreain).sprite }
    val player = controller.playground.player.map{ case(position, player) => player -> PlayerSprite(player) }
    
    playerList.clear
    output.contents.clear

    val grid = new NullPanel() {
      preferredSize = new Dimension(1024,512)

      player foreach {case (player, visual) => 
        add(visual.sprite, visual.sprite.width * player.position.x + offset, visual.sprite.height * player.position.y)
        playerList += (player.uuid -> visual)
      }
      
      terrain foreach {case (position, sprite) => 
        add(sprite, sprite.width * position.x + offset, sprite.height * position.y)
      }
    }
    
    output.contents += grid
    
    ui.visible = true
    // visible initial position of players.
    controller.playground.player.foreach({ case(k,v) => this.update(v)})
  }
}