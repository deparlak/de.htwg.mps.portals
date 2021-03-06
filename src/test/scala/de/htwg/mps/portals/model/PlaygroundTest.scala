package de.htwg.mps.portals.model

import java.util.UUID
import scala.io.Source._
import org.specs2.mutable.SpecificationWithJUnit
import de.htwg.mps.portals.util.Level

class PlaygroundTest extends SpecificationWithJUnit {
  val playground = new Playground
  val firstLevel = new Level().firstNormalLevel
  var level1 = playground.load(firstLevel)

  "The text representation of the playground" should {
    level1 = playground.load(firstLevel)
    val input = fromFile("src/main/resources" + firstLevel).mkString
    "be the same as the input" in {
      level1.toString == input
    }
  }

  "A player" should {
    level1 = playground.load(firstLevel)
    "not move from an invalid position" in {
      val player = Human(UUID.randomUUID.toString, new Position(0, 1), Right, 0)
      val x = level1.move(player)
      x._1 shouldEqual InvalidMove("From invalid position")
    }

    "not move to an invalid position" in {
      val player = level1.player.get(new Position(3, 1)).get.switchDirection(Up)
      val move = level1.move(player)
      move._1 shouldEqual InvalidMove()
    }

    "move to a valid position" in {
      val player: Player = level1.player.get(new Position(3, 1)).get.switchDirection(Right)
      val move = level1.move(player)
      move._1 shouldEqual Moved(new Human("1", new Position(3, 1), Stay, 0), Grass)
    }

    "pay movement costs" in {
      val player: Player = Human(UUID.randomUUID.toString, new Position(1, 1), Right, 1)
      val move = level1.move(player)
      move._1 shouldEqual PayMovement(player)
    }
  }

  "A Human" should {
    level1 = playground.load(firstLevel)
    "not move to a position where another Player already is" in {
      val player: Player = level1.player.get(new Position(3, 1)).get
      val bot: Player = level1.player.get(new Position(1, 5)).get

      level1 = level1.setMove("1", Left)
      level1 = level1.move(getPlayer(new Position(3, 1)))._2
      level1 = level1.setMove("1", Left)
      level1 = level1.move(getPlayer(new Position(2, 1)))._2
      level1 = level1.setMove("1", Down)
      level1 = level1.move(getPlayer(new Position(1, 1)))._2
      level1 = level1.setMove("1", Down)
      level1 = level1.move(getPlayer(new Position(1, 2)))._2
      level1 = level1.setMove("1", Down)
      level1 = level1.move(getPlayer(new Position(1, 3)))._2
      val move = level1.move(getPlayer(new Position(1, 4)))
      move._1 shouldEqual InvalidMove()
    }
  }

  "A Bot" should {
    "move to a position where another Human already is and destroy it" in {
      level1 = playground.load(firstLevel)
      var player: Player = level1.player.get(new Position(3, 1)).get
      val bot: Player = level1.player.get(new Position(1, 5)).get

      level1 = level1.setMove("1", Left)
      level1 = level1.move(getPlayer(new Position(3, 1)))._2
      level1 = level1.setMove("1", Left)
      level1 = level1.move(getPlayer(new Position(2, 1)))._2
      level1 = level1.setMove("1", Down)
      level1 = level1.move(getPlayer(new Position(1, 1)))._2
      level1 = level1.setMove("1", Down)
      level1 = level1.move(getPlayer(new Position(1, 2)))._2
      level1 = level1.setMove("1", Down)
      level1 = level1.move(getPlayer(new Position(1, 3)))._2
      player = getPlayer(new Position(1, 4))

      val destroyed = Destroyed(bot, player)
      val move = level1.move(bot)
      move._1 shouldEqual destroyed
    }
  }

  def getPlayer(p: Position): Player = {
    level1.player.get(p).get
  }

}