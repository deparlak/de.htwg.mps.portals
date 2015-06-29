package de.htwg.mps.portals.util

import scala.swing.Panel
import scala.swing.Component
import java.awt.Color
import java.awt.Dimension


class PlayerSpritelala extends Panel {
  peer.setLayout(null)
  peer.setSize(32 * 13, 32 * 29)

  protected def add(comp: Component, x: Int, y: Int, z : Int): Unit = {
    val p = comp.peer
    p.setBackground(Color.RED)
    p.setLocation(x, y)
    p.setSize(new Dimension(32, 32))
    peer.setComponentZOrder(p, z)
    peer.add(p)
  }
}

