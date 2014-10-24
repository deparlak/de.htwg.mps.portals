package main.scala

import main.scala.controller.Controller
import main.scala.model._
import main.scala.model.helper._
import main.scala.view.Tui

object Portals {
  def main(args: Array[String]) {
    var items : Map[Position, Item] = Map()
    val reader = new PlaygroundReader("res/level1.txt")
    val playground = new Playground(reader.items)
    val tui = new Tui(new Controller(playground))
    println (playground.getPlayers)
  }
}