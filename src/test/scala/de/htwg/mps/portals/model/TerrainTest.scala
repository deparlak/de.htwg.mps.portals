package de.htwg.mps.portals.model

import org.specs2.mutable.Specification
import java.util.UUID
import org.specs2.mutable.SpecificationWithJUnit

class TerrainTest extends SpecificationWithJUnit {
  val bot1 = Bot(UUID.randomUUID.toString, new Position(0, 0), Right, Stay, 0)
  val human1 = Human(UUID.randomUUID.toString, new Position(0, 1), Stay, 0)

  "Grass" should {
    val grass = Grass
    "be walkable by a Bot" in {
      grass walkableBy bot1 must beTrue
    }

    "be walkable by a Human" in {
      grass walkableBy human1 must beTrue
    }
  }

  "Fire" should {
    val fire = Fire
    "not be walkable by a Bot" in {
      fire walkableBy bot1 must beFalse
    }

    "be walkable by a Human" in {
      fire walkableBy human1 must beTrue
    }
  }

  "Swamp" should {
    val swamp = Swamp
    "be walkable by a Bot" in {
      swamp walkableBy bot1 must beTrue
    }

    "be walkable by a Human" in {
      swamp walkableBy human1 must beTrue
    }

    "have a higher movemnet cost then grass" in {
      val grass = Grass
      swamp.movementCost > grass.movementCost must beTrue
    }
  }

  "A Wall" should {
    val wall = Wall
    "not be walkable by a Bot" in {
      wall walkableBy bot1 must beFalse
    }

    "not be walkable by a Human" in {
      wall walkableBy human1 must beFalse
    }
  }

  "A Portal" should {
    val portal = Portal
    "not be walkable by a Bot" in {
      portal walkableBy bot1 must beFalse
    }

    "be walkable by a Human" in {
      portal walkableBy human1 must beTrue
    }

    "have the end state" in {
      portal.endGame must beTrue
    }
  }

}