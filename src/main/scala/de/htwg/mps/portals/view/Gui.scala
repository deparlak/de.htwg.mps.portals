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


class Gui(val controller: Controller) extends Observer[Event] {
  controller.add(this)
  val output = new FlowPanel()
  val ui = new UI(controller, output)
  var spriteMap : Map[Position, TerrainSprite] = Map()
  
  def update(e: Event) = {
    e match {
      case x: NewGame  => newGame()
      case x: Update   => update(x.move.player)
      case x: Wait	   => None
      case x: GameWon  => ui.gameWon
      case x: GameLost => ui.gameLost
    }
  }
  
  def update(p : Player) = {
    spriteMap.get(p.position) match {
      case Some(s : TerrainSprite)	=> s.leaveAnimation(p)
      case None						=> None
    }
    spriteMap.get(p.nextPosition) match {
      case Some(s : TerrainSprite)	=> s.enterAnimation(p)
      case None						=> None
    }
  }


  def newGame() {
    spriteMap = controller.playground.terrain.map{ case(k,v) => k -> TerrainSprite(v)}
    val sorted = spriteMap.toSeq.sortWith(_._1 < _._1)
    val sprites : List[Sprite] = sorted.map{ case(k,v) => v.sprite}.toList
    output.contents.clear

    val grid = new GridPanel(9, 26) { 
      sprites.foreach(sprite => { contents += sprite })
    }
    output.contents += grid
    ui.visible = true
    // visible initial position of players.
    controller.playground.player.foreach({ case(k,v) => this.update(v)})
  }
}