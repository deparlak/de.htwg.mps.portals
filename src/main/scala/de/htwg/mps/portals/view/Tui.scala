package de.htwg.mps.portals.view

import scala.swing._
import scala.swing.event._
import scala.reflect.io.Path
import de.htwg.mps.portals.util.Observer
import de.htwg.mps.portals.model.Player
import de.htwg.mps.portals.controller.Event
import de.htwg.mps.portals.controller._
import com.escalatesoft.subcut.inject.AutoInjectable
import com.escalatesoft.subcut.inject.BindingModule


class Tui(implicit val bindingModule: BindingModule) extends Observer[Event]  with UserInterface with AutoInjectable {
  val controller = inject [Controller]
  controller.add(this)
  val output = new TextArea() {
    focusable = false
    editable = false
    font = new Font("monospaced", 0, 14)
    preferredSize = new Dimension(400, 250)
    text = controller.playground.toString
  }
  val ui = new UI(controller, output)
  
  def update(e: Event) = {
    e match {
      case _: NewGame  => output.text = controller.playground.toString
      case _: Update   => output.text = controller.playground.toString
      case _: Wait	   => None
      case _: GameWon  => ui.gameWon
      case _: GameLost => ui.gameLost
    }
  }
  
}