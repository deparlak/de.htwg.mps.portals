package de.htwg.mps.portals.view

import scala.swing._
import scala.swing.event._
import scala.reflect.io.Path
import de.htwg.mps.portals.util.Observer
import de.htwg.mps.portals.model.Player
import de.htwg.mps.portals.controller.Event
import de.htwg.mps.portals.controller._
import de.htwg.mps.portals.util.Sprite
import de.htwg.mps.portals.util.TerrainSprite
import de.htwg.mps.portals.model.Position
import de.htwg.mps.portals.util.PlayerSprite


class Gui(val controller: Controller) extends Observer[Event] {
  controller.add(this)
  val output = new FlowPanel()
  val ui = new UI(controller, output)
  
  def update(e: Event) = {
    e match {
      case x: NewGame  => newGame()
      case x: Update   => update(x.move.player)
      case x: Wait	   => None
      case x: GameWon  => ui.gameWon
      case x: GameLost => ui.gameLost
    }
  }
  
  def update(p : Player) = {/*
 //   spriteMap.get(p.position) match {
   //   case Some(s : TerrainSprite)	=> s.leaveAnimation(p)
      case None						=> None
    }
  //  spriteMap.get(p.nextPosition) match {
   //   case Some(s : TerrainSprite)	=> s.enterAnimation(p)
      case None						=> None
    }*/
  }


  def newGame() {
    val offset = 48
    val sprites = controller.playground.terrain.map{ case(position, terreain) => position -> TerrainSprite(terreain).sprite }
    
    output.contents.clear

    val grid = new NullPanel() {
      preferredSize = new Dimension(1024,512)

      sprites foreach {case (position, sprite) => 
        add(sprite, sprite.width * position.x + offset, sprite.height * position.y) 
      }
    }
    
    output.contents += grid
    ui.visible = true
    // visible initial position of players.
    controller.playground.player.foreach({ case(k,v) => this.update(v)})
  }
  
  // Null Panel, which allow us to set the panel to a self defined position (without a layout manager).
  class NullPanel extends Panel {
	peer.setLayout(null)
    protected def add(comp: Component, x: Int, y: Int): Unit = {
	  val p = comp.peer
	  p.setLocation(x, y)
	  p.setSize(p.getPreferredSize) // !
	  peer.add(p)
	 }
   }
}