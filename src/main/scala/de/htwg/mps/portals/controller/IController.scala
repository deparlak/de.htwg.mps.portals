package de.htwg.mps.portals.controller

import de.htwg.mps.portals.util.Level

trait IController {
  def moveUp(uuid: String)
  def moveDown(uuid: String)
  def moveLeft(uuid: String)
  def moveRight(uuid: String)

  // load a new playground
  def load(file: String = new Level().firstNormalLevel)

  def timerMethod
}