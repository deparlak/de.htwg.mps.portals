package de.htwg.mps.portals

import de.htwg.mps.portals.controller.Controller
import de.htwg.mps.portals.model.Playground
import de.htwg.mps.portals.view._

object Portals {
  def main(args: Array[String]) {
    val playground = new Playground()
    val controller = new Controller()
    val gui = new Gui(controller)
    val tui = new Tui(controller)
    controller.load()
  }
}