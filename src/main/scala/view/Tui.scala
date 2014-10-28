package main.scala.view

import scala.swing._
import scala.swing.event._
import main.scala.util.Observer
import main.scala.controller.Controller
import main.scala.model.Event
import main.scala.model._
import scala.collection.JavaConversions._
import scala.reflect.io.Path

class Tui(val controller: Controller) extends Observer[Event] {
  controller.add(this)
  val ui = new UI()
  var files = Path("res") walkFilter { p => p.isFile }
  var position = new Position(0,0)
  var gameRunning = false
 
  printMenu
  

  def update(e : Event) = {
    e match {
      case _		: NewGame => ui.area.text = controller.playground.toString; gameRunning = true; position = controller.playground.getPlayers.head._1;
      case _    	: Update  => ui.area.text = controller.playground.toString
      case _ 		: GameEnd => ui.area.text = "Game End. Press enter for the next level."; gameRunning = false;
    }
  }
  
  def printMenu : Unit = ui.area.text = "Welcome to Portals. Press enter to start."
    
  def firstLevel : Unit = {
    println("firstLevel")
    files = Path("res") walkFilter { p => p.isFile }; 
    nextLevel
  }
    
  def nextLevel : Unit = if (files.hasNext) controller.load(files.next.toString) else firstLevel
  
  class UI extends MainFrame {
    title = "Portals"
	preferredSize = new Dimension(400, 240)

	val area = new TextArea() {
      text = controller.playground.toString
	  editable = false
	  font = new Font("monospaced", 0, 14)
	  listenTo(keys)
	  reactions += {
	    case KeyPressed(_, Key.Up    , _, _) => if (gameRunning) controller.moveUp(position)
	    case KeyPressed(_, Key.Down  , _, _) => if (gameRunning) controller.moveDown(position)
	    case KeyPressed(_, Key.Left  , _, _) => if (gameRunning) controller.moveLeft(position)
	    case KeyPressed(_, Key.Right , _, _) => if (gameRunning) controller.moveRight(position)
	    case KeyPressed(_, Key.Enter , _, _) => if (!gameRunning) nextLevel
	  }
	}
    
    contents = new BoxPanel(Orientation.Vertical) {
      contents += area
      border = Swing.EmptyBorder(10,10,10,10)
    }
    
    visible = true
  }
}