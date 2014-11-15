package de.htwg.mps.portals

import de.htwg.mps.portals.controller.Controller
import de.htwg.mps.portals.model.Item
import de.htwg.mps.portals.model.Playground
import de.htwg.mps.portals.model.Position
import de.htwg.mps.portals.view.Tui

object Portals {
  def main(args: Array[String]) {
    val playground = new Playground()
    val tui = new Tui(new Controller(playground))
  }
}