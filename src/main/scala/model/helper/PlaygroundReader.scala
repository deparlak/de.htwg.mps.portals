package main.scala.model.helper

import main.scala.model._
import scala.io.Source._

class PlaygroundReader(file : String) {
  val source = fromFile(file)
  val arr = source.map(_.toChar).toArray
  var items : Map[Position, Item] = Map()
  var x = 0
  var y = 0
  
  arr.foreach( input => 
  	input match {
  	  case '|' 		=> items += (new Position(x, y) -> new Wall); x += 1
  	  case 'O'      => items += (new Position(x, y) -> new Player); x += 1
  	  case ' '      => items += (new Position(x, y) -> new Way); x += 1
  	  case '\n'		=> x = 0; y += 1
  	  case '\r'     => None
      case _ 		=> print("TODO Exception return, because of invalid character")
  	}
  )
  source.close()
}