package main.scala

import main.scala.controller._
import main.scala.model._
import main.scala.view.Tui

object Portals {
  def main(args: Array[String]) {
    var items : Map[Position, Item] = Map()
    val playground = new Playground(items)
    val tui = new Tui(new Controller(playground))
  }
}