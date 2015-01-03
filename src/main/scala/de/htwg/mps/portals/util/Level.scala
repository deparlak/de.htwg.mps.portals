package de.htwg.mps.portals.util

import scala.reflect.io.Path

class Level {
  var level = Path("res").walkFilter { p => p.isFile }
  var current = level.next.toString
  
  def nextLevel : String = {
    if (level.hasNext) {
      current = level.next.toString
    } else {
      level = Path("res") walkFilter { p => p.isFile };
      current = level.next.toString
    }
    current
  }
  
  def currentLevel = current
  
  def hasNext : Boolean = level.hasNext
}