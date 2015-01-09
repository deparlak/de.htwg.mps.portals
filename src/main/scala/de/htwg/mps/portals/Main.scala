package de.htwg.mps.portals

import de.htwg.mps.portals.controller.Controller
import de.htwg.mps.portals.model.Playground
import de.htwg.mps.portals.view._
import com.escalatesoft.subcut.inject.AutoInjectable
import de.htwg.mps.portals.config.TestConfiguration
import de.htwg.mps.portals.controller.IController

object Portals extends AutoInjectable {
  implicit val bindingModule = TestConfiguration
  // implicit val bindingModule = TuiConfiguration
  // implicit val bindingModule = GuiConfiguration
  
  def main(args: Array[String]) {
    val playground = new Playground()
    val controller = inject[IController]
    //    val ui = inject [UserInterface]
    val tui = inject[UserInterface]("tui")
    val gui = inject[UserInterface]("gui")
    controller.load()
  }
}