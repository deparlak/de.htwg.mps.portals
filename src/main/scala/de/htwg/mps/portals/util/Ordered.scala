package de.htwg.mps.portals.util

trait Ordered[A] {
  def compare(that : A) : Int
  def < (that : A) : Boolean = (this compare that) < 0
  def > (that : A) : Boolean = (this compare that) > 0
}