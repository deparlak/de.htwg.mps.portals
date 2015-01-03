package de.htwg.mps.portals.controller

import org.specs2.mutable.SpecificationWithJUnit
import de.htwg.mps.portals.model.Position
import de.htwg.mps.portals.model.Player

class ControllerTest extends SpecificationWithJUnit {

  "The controller" should {
    val controller = new Controller
    controller.load("res/level1.txt")
    "initialize the timer" in {
      controller.timer must be_!=(null)
    }

    "not move up the Player with the ID \"1\" if there is a wall" in {
      reset(controller)
      val before = controller.playground.player.get(new Position(3, 1)).get.uuid
      controller moveUp "1"
      val after = controller.playground.player.get(new Position(3, 1)).get.uuid
      after must beEqualTo(before)
    }

    "not move down the Player with the ID \"1\" if there is a wall" in {
      reset(controller)
      val before = controller.playground.player.get(new Position(3, 1)).get.uuid
      controller moveDown "1"
      controller.playground.player.get(new Position(3, 1)).get.uuid must beEqualTo(before)
    }

    "move left the Player with the ID \"1\"" in {
      reset(controller)
      val before = controller.playground.player.get(new Position(3, 1)).get.uuid
      controller moveLeft "1"
      update(controller)
      var after = controller.playground.player.get(new Position(3, 1)).get.uuid
      after must beEqualTo(before)
    }

    "move right the Player with the ID \"1\"" in {
      reset(controller)
      val before = controller.playground.player.get(new Position(3, 1)).get.uuid
      controller moveRight "1"
      update(controller)
      update(controller)
      val after = controller.playground.player.get(new Position(3, 1)).get.uuid
      after must beEqualTo(before)
    }

    "end the game if a bot destroys the human" in {
      reset(controller)
      val botID = controller.playground.player.get(new Position(1, 5)).get.uuid
      val player = "1"
      for (i <- 1 to 10) {
        controller moveLeft player
        update(controller)
      }
      for (i <- 1 to 10) {
        controller moveDown player
        update(controller)
      }
      controller.timer.isRunning must beFalse
    }

    "end the game if a the human reaches the portal" in {
      reset(controller)
      val botID = controller.playground.player.get(new Position(1, 5)).get.uuid
      val player = "1"
      for (i <- 1 to 45) {
        controller moveRight player
        update(controller)
      }
      controller.timer.isRunning must beFalse
    }
  }

  def update(c: Controller) = c.timerMethod
  def reset(c: Controller) {
    c.load("res/level1.txt")
    c.timer.stop
  }

}