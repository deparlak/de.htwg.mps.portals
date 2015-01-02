package de.htwg.mps.portals.model

import org.specs2.mutable.Specification
import java.util.UUID
import org.specs2.mutable.SpecificationWithJUnit
import java.io.File
import scala.io.Source._

class PlaygroundTest extends SpecificationWithJUnit {
  val playground = new Playground
  val level1 = playground.load("res/level1.txt")
  val bot1 = Bot(UUID.randomUUID.toString, new Position(0, 0), Right, Stay, 0)
  val human1 = Human(UUID.randomUUID.toString, new Position(0, 1), Stay, 0)

  "The text representation of the playground" should {
    val input = fromFile("res/level1.txt").mkString
    "be the same as the input" in {
      level1.toString == input
    }
  }

  "A player" should {
    "not move from an invalid position" in {
      val player = Human(UUID.randomUUID.toString, new Position(0, 1), Right, 0)
      val x = level1.move(player)
      x._1 shouldEqual InvalidMove("From invalid position")
    }

    "not move to an invalid position" in {
      val x = level1.player.get(new Position(3, 1)) match {
        case Some(player) => level1.move(player.switchDirection(Up))
      }
      x._1 shouldEqual InvalidMove()
    }

    "move to a valid position" in {
      val player: Player = level1.player.get(new Position(3, 1)) match {
        case Some(player) => player.switchDirection(Right)
      }
      val move = level1.move(player)
      move._1 shouldEqual Moved(new Human("1", new Position(3, 1), Stay, 0), Grass)
    }

  }

}