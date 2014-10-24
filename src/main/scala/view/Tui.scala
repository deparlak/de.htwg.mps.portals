package main.scala.view

import scala.swing._
import scala.swing.event._
import main.scala.util.Observer
import main.scala.controller.Controller
import main.scala.model.Event
import main.scala.model.GameEnd
import main.scala.model.Update

class Tui(var controller: Controller) extends Observer[Event] {
  controller.add(this)
  val ui = new UI  
  ui.area.text = controller.playground.toString
  def update(e : Event) = {
    e match {
      case update	: Update => ui.area.text = controller.playground.toString
      case moved 	: GameEnd => println("Game end")
    }
    ui.area.text = controller.playground.toString
  }
  ui.visible = true
  
  var position = controller.playground.getPlayers.head._1
  println("Number of Players : "+controller.playground.getPlayers.size);

  class UI extends MainFrame {
    title = "Portals"
	preferredSize = new Dimension(320, 240)

	val area = new TextArea("*hallo test") {
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
  }
}