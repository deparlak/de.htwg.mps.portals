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
  val ui = new UI()
  val player = Player.HumanPlayer1
  var level = Path("res").walkFilter { p => p.isFile }
  var currentLevel = ""
  nextLevel

  def update(e: Event) = {
    e match {
      case x: NewGame  => ui.newGame()
      case x: Update   => ui.update(x.move.player)
      case x: Wait	   => None
      case x: GameWon  => nextLevel
      case x: GameLost => ui.GameLost()
    }
  }
  
  def nextLevel: Unit = if (level.hasNext) {
    currentLevel = level.next.toString
    controller.load(currentLevel) 
  } else {
    level = Path("res") walkFilter { p => p.isFile };
    if (level.hasNext) nextLevel
  }

  def restartLevel: Unit = controller.load(currentLevel)

  class UI extends MainFrame {
    val colour = this.background
    var spriteMap : Map[Position, TerrainSprite] = Map()
    title = "Portals"
    resizable = false
    
      
   // preferredSize = new Dimension(400, 250)

    val area = new TextArea() {
      background = colour
      requestFocus
      editable = false
      font = new Font("monospaced", 0, 14)
      listenTo(keys)
      reactions += {
        case KeyPressed(_, Key.Up, _, _) 	=> controller.moveUp(player)
        case KeyPressed(_, Key.Down, _, _) 	=> controller.moveDown(player)
        case KeyPressed(_, Key.Left, _, _) 	=> controller.moveLeft(player)
        case KeyPressed(_, Key.Right, _, _) => controller.moveRight(player)
        case KeyPressed(_, Key.Enter, _, _) => restartLevel
      }
    }
    
    def update(p : Player) = {
      val sprite1 = spriteMap.get(p.position)
      (sprite1) match {
        case Some(s : TerrainSprite)=> sprite1.get.enterAnimation(p)
        case None					=> None
      }
      val sprite2 = spriteMap.get(p.nextPosition)
      (sprite2) match {
        case Some(s : TerrainSprite)=> sprite2.get.leaveAnimation(p)
        case None					=> None
      }
    }
      
    def GameLost() = {
            
    }

    def newGame() {
      spriteMap = controller.playground.terrain.map{ case(k,v) => k -> TerrainSprite(v)}
      val sorted = spriteMap.toSeq.sortWith(_._1 < _._1)
      val sprites : List[Sprite] = sorted.map{ case(k,v) => v.sprite}.toList
           
      val grid = new GridPanel(9, 26) { 
        sprites.foreach(sprite => { contents += sprite })
      }
      
      contents = new BoxPanel(Orientation.Vertical) {
    	  contents += grid  
          contents += area
    	  border = Swing.EmptyBorder(15, 15, 15, 0)
      }
      area.requestFocus
      
      // initial position of players.
      controller.playground.player.foreach({ case(k,v) => this.update(v)})
    }
    
    visible = true
  }
}