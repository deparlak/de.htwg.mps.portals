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
  var player = Player.HumanPlayer1
  var level = Path("res") walkFilter { p => p.isFile }
  var currentLevel = ""
  var gameRunning = false
  var gameWasWon = true
  ui.area.text = "Welcome to Portals. Press enter to start."

  def update(e: Event) = {
    e match {
      case _: NewGame  => newGame
      case _: Update   => updatePlayground
      case _: GameWon  => gameWon
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
    gameWasWon = false
  }

  def updatePlayground = if (gameRunning) ui.area.text = controller.playground.toString

  def gameWon = {
    ui.area.text = "You won the game. Press enter for the next level."
    gameRunning = false
    gameWasWon = true
  }

  def firstLevel: Unit = {
    level = Path("res") walkFilter { p => p.isFile };
    if (level.hasNext) nextLevel
  }

  def nextLevel: Unit = if (level.hasNext) {
    currentLevel = level.next.toString
    controller.load(currentLevel) 
  } else {
    firstLevel
  }

  def restartLevel: Unit = controller.load(currentLevel)

  class UI extends MainFrame {
    title = "Portals"
    preferredSize = new Dimension(400, 240)

    val area = new TextArea() {
      text = controller.playground.toString
      editable = false
      font = new Font("monospaced", 0, 14)
      listenTo(keys)
      reactions += {
        case KeyPressed(_, Key.Up, _, _) 	=> if (gameRunning) controller.moveUp(player)
        case KeyPressed(_, Key.Down, _, _) 	=> if (gameRunning) controller.moveDown(player)
        case KeyPressed(_, Key.Left, _, _) 	=> if (gameRunning) controller.moveLeft(player)
        case KeyPressed(_, Key.Right, _, _) => if (gameRunning) controller.moveRight(player)
        case KeyPressed(_, Key.Enter, _, _) => if (gameWasWon) nextLevel else restartLevel
      }
    }

    contents = new BoxPanel(Orientation.Vertical) {
      contents += area
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    visible = true
  }
}