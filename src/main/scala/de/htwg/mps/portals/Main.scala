package de.htwg.mps.portals

import com.escalatesoft.subcut.inject.AutoInjectable
import de.htwg.mps.portals.controller.IController
import de.htwg.mps.portals.model.Playground
import de.htwg.mps.portals.view._
import de.htwg.mps.portals.actor.AktorSystem
import de.htwg.mps.portals.config.TestConfiguration
import de.htwg.mps.portals.config.GuiConfiguration
import de.htwg.mps.portals.config.TuiConfiguration

object Portals extends AutoInjectable {
  implicit val bindingModule = TuiConfiguration

  def main(args: Array[String]) {
    val playground = new Playground()
    val controller = inject[IController]

    val actorSystem = inject[AktorSystem]

    val ui = inject[UserInterface]
    //    val tui = inject[UserInterface]("tui")
    //    val gui = inject[UserInterface]("gui")
    
    controller.load()
  }
}