package de.htwg.mps.portals.swing.util

import scala.swing._

// Null Panel, which allow us to set the panel to a self defined position (without a layout manager).
class NullPanel extends Panel {
  peer.setLayout(null)
    
  protected def add(comp: Component, x: Int, y: Int): Unit = {
    val p = comp.peer
	p.setLocation(x, y)
	p.setSize(p.getPreferredSize) // !
	peer.add(p)
  }
  
  def getAll() {
     peer.getComponent(0) 
  }
}