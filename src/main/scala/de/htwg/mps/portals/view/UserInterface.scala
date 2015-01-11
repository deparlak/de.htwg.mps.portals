package de.htwg.mps.portals.view

import de.htwg.mps.portals.controller.Event

trait UserInterface {
  def update(e: Event)
}