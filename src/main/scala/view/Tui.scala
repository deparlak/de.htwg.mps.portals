package main.scala.view

import scala.swing._
import scala.swing.event._
import main.scala.util.Observer
import main.scala.controller.Controller
import main.scala.model.Event
import main.scala.model._

class Tui(var controller: Controller) extends Observer[Event] {
  controller.add(this)
  val ui = new UI()
  var position = controller.playground.getPlayers.head._1

  def update(e : Event) = {
    e match {
      case _    	: Update  => ui.area.text = controller.playground.toString
      case _ 		: GameEnd => println("Game end")
    }
    ui.area.text = controller.playground.toString
  }
  
  class UI extends MainFrame {
    title = "Portals"
	preferredSize = new Dimension(320, 240)

	val area = new TextArea() {
      text = controller.playground.toString
	  editable = false
	  font = new Font("monospaced", 0, 14)
	  listenTo(keys)
	  reactions += {
	    case KeyPressed(_, Key.Up    , _, _) => position = controller.moveUp(position)
	    case KeyPressed(_, Key.Down  , _, _) => position = controller.moveDown(position)
	    case KeyPressed(_, Key.Left  , _, _) => position = controller.moveLeft(position)
	    case KeyPressed(_, Key.Right , _, _) => position = controller.moveRight(position)
	  }
	}
    
    contents = new BoxPanel(Orientation.Vertical) {
      contents += area
      border = Swing.EmptyBorder(10,10,10,10)
    }
    
    visible = true
  }
}