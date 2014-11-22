package de.htwg.mps.portals.view

import scala.swing._
import scala.swing.event._
import scala.reflect.io.Path
import de.htwg.mps.portals.controller.Controller
import de.htwg.mps.portals.model.Player

class UI(val controller : Controller, output : Component) extends MainFrame {
    val player = Player.HumanPlayer1
    var level = Path("res").walkFilter { p => p.isFile }
    var currentLevel = ""
    val colour = this.background

    // MainFrame attributes
    title = "Portals"
    preferredSize = new Dimension(1024, 512)
    resizable = true
    visible = true
    menuBar = new MenuBar {   
      contents += new Menu("A Menu") {      
        contents += new MenuItem("An item")      
        contents += new MenuItem(Action("Action item") { println(title) })       
        contents += new Separator        
        contents += new CheckMenuItem("Check me")
      }
    }

    // Label which is for status output and handling user input
    val input = new TextArea() {
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
    
    contents = new BoxPanel(Orientation.Vertical) {
      contents += output  
      contents += input
      border = Swing.EmptyBorder(15, 15, 0, 15)
    }
    
    input.requestFocus
    
    def gameWon = nextLevel
    def gameLost = status("Game lost")
    
    // method for restarting a level and going to the nextLevel
    def restartLevel: Unit = controller.load(currentLevel) 
    def nextLevel: Unit = if (level.hasNext)  {
      currentLevel = level.next.toString
      controller.load(currentLevel) 
    } else {
      level = Path("res") walkFilter { p => p.isFile };
      if (level.hasNext) nextLevel
    }
    
    // the status of the UI
    def status(text : String) = input.text = text
}