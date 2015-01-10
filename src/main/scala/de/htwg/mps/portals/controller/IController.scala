package de.htwg.mps.portals.controller

import de.htwg.mps.portals.util.Level
import de.htwg.mps.portals.util.Observable
import de.htwg.mps.portals.model.Playground

trait IController extends Observable[Event]  {
  def moveUp(uuid: String)
  def moveDown(uuid: String)
  def moveLeft(uuid: String)
  def moveRight(uuid: String)

  // load a new playground
  def load(file: String = new Level().firstNormalLevel)

  def timerMethod
  
  var playground: Playground
}