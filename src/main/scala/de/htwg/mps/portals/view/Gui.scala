package de.htwg.mps.portals.view

import scala.swing._
import scala.swing.event._
import scala.reflect.io.Path
import de.htwg.mps.portals.util.Observer
import de.htwg.mps.portals.model.Player
import de.htwg.mps.portals.controller.Event
import de.htwg.mps.portals.controller._
import de.htwg.mps.portals.model.Position
import com.escalatesoft.subcut.inject.AutoInjectable
import com.escalatesoft.subcut.inject.BindingModule
import de.htwg.mps.portals.swing.TerrainSprite
import de.htwg.mps.portals.swing.util.NullPanel
import de.htwg.mps.portals.swing.PlayerSprite
import de.htwg.mps.portals.swing.util.Sprite
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.future
import scala.concurrent.Future
import scala.concurrent.Awaitable
import de.htwg.mps.portals.swing.PlayerSprites


class Gui(implicit val bindingModule: BindingModule) extends Observer[Event] with UserInterface with AutoInjectable {
  val controller = inject [IController]
  controller.add(this)
  val playerList = scala.collection.mutable.HashMap[String, PlayerSprite]()
  val playerAnimation = scala.collection.mutable.HashMap[String, Future[Boolean]]()
  val offset = 48
  val output = new FlowPanel()
  val ui = new UI(controller, output)
  var sprites = List()
  
  def update(e: Event) = {
    e match {
      case x: NewGame  => newGame()
      case x: Update   => update(x.move.player)
      case x: Wait	   => None
      case x: GameWon  => ui.gameWon
      case x: GameLost => ui.gameLost
    }
  }
  

  def update(player : Player) = {
    val visual = playerList.get(player.uuid)
    if (None != visual) {
    	visual.get.animate(player)
    }
  }



  def newGame() {
    val terrain = controller.playground.terrain.map{ case(position, terreain) => position -> TerrainSprite(terreain).sprite }
    val player = controller.playground.player.map{ case(position, player) => player -> PlayerSprites(player) }
    
    playerAnimation.clear
    playerList.clear
    output.contents.clear

    val grid = new NullPanel() {
      preferredSize = new Dimension(1024,512)

      player foreach {case (player, visual) => 
        visual.sprites foreach {case (sprite) =>
          add(sprite, sprite.width * player.position.x + offset, sprite.height * player.position.y)
        }
        playerList += (player.uuid -> visual)
      }
      

      terrain foreach {case (position, t) => 
        add(t, t.width * position.x + offset, t.height * position.y)
      }


    }

    output.contents += grid
    
    ui.visible = true
    // visible initial position of players.
    controller.playground.player.foreach({ case(k,v) => this.update(v)})
  }
}