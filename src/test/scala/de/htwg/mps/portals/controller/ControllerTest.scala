package de.htwg.mps.portals.controller

import org.specs2.mutable.SpecificationWithJUnit
import de.htwg.mps.portals.model.Position
import de.htwg.mps.portals.model.Player

class ControllerTest extends SpecificationWithJUnit {
  val controller = new Controller
  controller.load("res/level1.txt")

  "The controller" should {
    "initialize the timer" in {
      controller.timer must be_!=(null)
    }

    "not move up the Player with the ID \"1\" if there is a wall" in {
      controller.load("res/level1.txt")
      val before = controller.playground.player.get(new Position(3, 1)).get.uuid
      controller moveUp "1"
      val after = controller.playground.player.get(new Position(3, 1)).get.uuid
      after must beEqualTo(before)
    }

    "not move down the Player with the ID \"1\" if there is a wall" in {
      controller.load("res/level1.txt")
      val before = controller.playground.player.get(new Position(3, 1)).get.uuid
      controller moveDown "1"
      controller.playground.player.get(new Position(3, 1)).get.uuid must beEqualTo(before)
    }

    "move left the Player with the ID \"1\"" in {
      controller.load("res/level1.txt")
      val before = controller.playground.player.get(new Position(3, 1)).get.uuid
      controller moveLeft "1"
      var after = controller.playground.player.get(new Position(3, 1)).get.uuid
      after must beEqualTo(before)
    }

    "move right the Player with the ID \"1\"" in {
      controller.load("res/level1.txt")
      val before = controller.playground.player.get(new Position(3, 1)).get.uuid
      controller moveRight "1"
      val after = controller.playground.player.get(new Position(3, 1)).get.uuid
      after must beEqualTo(before)
    }
  }

}