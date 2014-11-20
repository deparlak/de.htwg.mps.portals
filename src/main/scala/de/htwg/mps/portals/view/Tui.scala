package de.htwg.mps.portals.view

import scala.swing._
import scala.swing.event._
import scala.reflect.io.Path
import de.htwg.mps.portals.util.Observer
import de.htwg.mps.portals.model.Player
import de.htwg.mps.portals.controller.Event
import de.htwg.mps.portals.controller._


class Tui(val controller: Controller) extends Observer[Event] {
  controller.add(this)
  val ui = new UI()
  val player = Player.HumanPlayer1
  var level = Path("res").walkFilter { p => p.isFile }
  var currentLevel = ""
  nextLevel

  def update(e: Event) = {
    e match {
      case _: NewGame  => ui.update(controller.playground.toString)
      case _: Update   => ui.update(controller.playground.toString)
      case _: Wait	   => None
      case _: GameWon  => nextLevel
      case _: GameLost => ui.update(controller.playground.toString + "\nYou lost the game. Hit enter to restart.")
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
    title = "Portals"
    preferredSize = new Dimension(400, 250)

    val area = new TextArea() {
      text = controller.playground.toString
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
    
    def update(text : String) = area.text = text

    contents = new BoxPanel(Orientation.Vertical) {
      contents += area
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    visible = true
  }
}