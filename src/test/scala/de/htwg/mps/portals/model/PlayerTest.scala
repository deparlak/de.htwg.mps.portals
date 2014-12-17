package de.htwg.mps.portals.model

import org.specs2.mutable.Specification
import java.util.UUID
import org.specs2.mutable.SpecificationWithJUnit

class PlayerTest extends SpecificationWithJUnit {
  val bot1 = Bot(UUID.randomUUID.toString, new Position(0, 0), Right, Stay, 0)
  val bot2 = Bot(UUID.randomUUID.toString, new Position(1, 0), Right, Stay, 0)
  val human1 = Human(UUID.randomUUID.toString, new Position(0, 1), Stay, 0)
  val human2 = Human(UUID.randomUUID.toString, new Position(1, 1), Stay, 0)

  "A Bot" should {
    "destroy a Human" in {
      bot1.destroy(human1) must beTrue
    }

    "not destroy another Bot" in {
      bot1.destroy(bot2) must beFalse
    }
  }

  "A Human" should {
    " not destroy a Human" in {
      human1.destroy(human2) must beFalse
    }

    "not destroy a Bot" in {
      human1.destroy(bot2) must beFalse
    }
  }

  "A Human at Position [1|1]" should {
    "move up to [1|2]" in {
      val human = Human(UUID.randomUUID.toString, new Position(1, 1), Up, 0)
      human.validMove(0).position must beEqualTo(new Position(1, 0))
    }

    "move right to [2|1]" in {
      val human = Human(UUID.randomUUID.toString, new Position(1, 1), Right, 0)
      human.validMove(0).position must beEqualTo(new Position(2, 1))
    }

    "move left to [0|1]" in {
      val human = Human(UUID.randomUUID.toString, new Position(1, 1), Left, 0)
      human.validMove(0).position must beEqualTo(new Position(0, 1))
    }

    "move down to [1|0]" in {
      val human = Human(UUID.randomUUID.toString, new Position(1, 1), Down, 0)
      human.validMove(0).position must beEqualTo(new Position(1, 2))
    }

    "stay at [1|1]" in {
      val human = Human(UUID.randomUUID.toString, new Position(1, 1), Stay, 0)
      human.validMove(0).position must beEqualTo(new Position(1, 1))
    }

  }

}