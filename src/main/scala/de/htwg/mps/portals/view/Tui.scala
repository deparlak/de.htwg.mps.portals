package de.htwg.mps.portals.view

import scala.swing._
import scala.swing.event._
import de.htwg.mps.portals.util.Observer
import de.htwg.mps.portals.model._
import scala.collection.JavaConversions._
import scala.reflect.io.Path
import de.htwg.mps.portals.controller.Event
import de.htwg.mps.portals.controller._


class Tui(val controller: Controller) extends Observer[Event] {
  controller.add(this)
  val ui = new UI()
  val player = "1"
  var level = Path("res") walkFilter { p => p.isFile }
  var gameRunning = false
  ui.area.text = "Welcome to Portals. Press enter to start."

  def update(e: Event) = {
    e match {
      case _: NewGame => newGame
      case _: Update => updatePlayground
      case _: GameEnd => gameEnd
      case _: GameLost => gameLost
    }
  }

  def gameLost = {
    ui.area.text = "You lost the game. Hit enter to restart."
    gameRunning = false
  }

  def newGame = {
    ui.area.text = controller.playground.toString
    gameRunning = true
  }

  def updatePlayground = if (gameRunning) ui.area.text = controller.playground.toString

  def gameEnd = {
    ui.area.text = "Game End. Press enter for the next level."
    gameRunning = false
  }

  def firstLevel: Unit = {
    level = Path("res") walkFilter { p => p.isFile };
    if (level.hasNext) nextLevel
  }

  def nextLevel: Unit = if (level.hasNext) controller.load(level.next.toString) else firstLevel

  def restartLevel: Unit = firstLevel

  class UI extends MainFrame {
    title = "Portals"
    preferredSize = new Dimension(400, 240)

    val area = new TextArea() {
      text = controller.playground.toString
      editable = false
      font = new Font("monospaced", 0, 14)
      listenTo(keys)
      reactions += {
        case KeyPressed(_, Key.Up, _, _) => if (gameRunning) controller.moveUp(player)
        case KeyPressed(_, Key.Down, _, _) => if (gameRunning) controller.moveDown(player)
        case KeyPressed(_, Key.Left, _, _) => if (gameRunning) controller.moveLeft(player)
        case KeyPressed(_, Key.Right, _, _) => if (gameRunning) controller.moveRight(player)
        case KeyPressed(_, Key.Enter, _, _) => if (gameRunning) restartLevel else nextLevel
      }
    }

    contents = new BoxPanel(Orientation.Vertical) {
      contents += area
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    visible = true
  }
}