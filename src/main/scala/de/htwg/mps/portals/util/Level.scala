package de.htwg.mps.portals.util

class Level {
  var mode = "normal"
  var current = 1
  val max = 4
  
  private def getLevel(level : Int) : String =  "/level/" + mode + "/" + current.toString + ".txt"
  
  def nextLevel : String = {
    current = if (hasNext) current + 1 else 1
    getLevel(current)
  }
  
  def currentLevel = getLevel(current)
  
  def hasNext : Boolean = if (max > current + 1) true else false
  
  def firstNormalLevel : String = {
    current = 1
    mode = "normal"
    getLevel(current)
  }
}