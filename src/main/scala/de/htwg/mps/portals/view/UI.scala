package de.htwg.mps.portals.view

import scala.swing._
import scala.swing.event._
import scala.reflect.io.Path
import de.htwg.mps.portals.controller.Controller
import de.htwg.mps.portals.model.Player

class UI(val controller: Controller, output: Component) extends MainFrame {
  val player = Player.HumanPlayer1
  var level = Path("res").walkFilter { p => p.isFile }
  var currentLevel = level.next.toString
  val colour = this.background

  // MainFrame attributes
  title = "Portals"
  preferredSize = new Dimension(1024, 512)
  resizable = false
  visible = true
  menuBar = new MenuBar {
    contents += new Menu("Game") {
      contents += new MenuItem(Action("Restart") { restartLevel })
      contents += new MenuItem(Action("Help") { popupMenu.show(output, 0, 0) })
      contents += new Separator
      contents += new MenuItem(Action("Exit") { System.exit(0) })
    }
  }

  val popupMenu = new PopupMenu {
    contents += new Label("Portals")
    contents += new Label("Das Ziel ist das erreichen des Portals.")
    contents += new Label("Hierfuer navigiert man mit den Pfeiltasten")
    contents += new Label("Gegner duerfen einen nicht beruehren")
  }

  // Label which is for status output and handling user input
  val input = new TextArea() {
    background = colour
    requestFocus
    editable = false
    font = new Font("monospaced", 0, 14)
    listenTo(keys)
    reactions += {
      case KeyPressed(_, Key.Up, _, _) => controller.moveUp(player)
      case KeyPressed(_, Key.Down, _, _) => controller.moveDown(player)
      case KeyPressed(_, Key.Left, _, _) => controller.moveLeft(player)
      case KeyPressed(_, Key.Right, _, _) => controller.moveRight(player)
      case KeyPressed(_, Key.Enter, _, _) => restartLevel
    }
  }

  contents = new BoxPanel(Orientation.Vertical) {
    contents += output
    contents += input
    border = Swing.EmptyBorder(15, 15, 0, 15)
  }

  input.requestFocus

  def gameWon = nextLevel
  def gameLost = status("Game lost. Hit enter to restart this level.")

  // method for restarting a level and going to the nextLevel
  def restartLevel: Unit = {
    controller.load(currentLevel)
    status("")
  }
  def nextLevel: Unit = if (level.hasNext) {
    currentLevel = level.next.toString
    controller.load(currentLevel)
    status("")
  } else {
    level = Path("res") walkFilter { p => p.isFile };
    if (level.hasNext) nextLevel
  }

  // the status of the UI
  def status(text: String) = input.text = text
}