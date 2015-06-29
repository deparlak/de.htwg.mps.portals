package de.htwg.mps.portals.model
	
// companion object to get Terrain instances, like a factory method.
object PlayerFactory {
  def HumanPlayer1 = "1"

  def apply(char: Char, position: Position): Option[Player] = char match {
    case '1' =>
      Some(Human(HumanPlayer1, position, Stay, 0))
case 'B' =>
	val id = java.util.UUID.randomUUID.toString
		        Some(B1(id, position, Up, Stay, 0))
case 'T' =>
	val id = java.util.UUID.randomUUID.toString
		        Some(B2(id, position, Up, Stay, 0))
    case _ => None
  }
}
