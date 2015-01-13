package de.htwg.mps.portals.actor

import com.escalatesoft.subcut.inject.AutoInjectable
import com.escalatesoft.subcut.inject.Injectable
import de.htwg.mps.portals.controller.Event
import de.htwg.mps.portals.controller.IController
import de.htwg.mps.portals.model.Direction
import de.htwg.mps.portals.util.Observer
import com.escalatesoft.subcut.inject.BindingModule
import akka.actor.ActorSystem
import akka.actor.Props
import de.htwg.mps.portals.controller.NewGame
import de.htwg.mps.portals.controller.GameWon
import de.htwg.mps.portals.controller.GameLost
import de.htwg.mps.portals.controller.Wait
import de.htwg.mps.portals.model.Player
import de.htwg.mps.portals.controller.Update
import akka.actor.ActorRef
import de.htwg.mps.portals.controller.NewGame
import com.typesafe.config.ConfigValueFactory
import com.typesafe.config.ConfigFactory

class AktorSystem(implicit val bindingModule: BindingModule) extends Observer[Event] with Injectable {
  val controller = inject[IController]
  controller.add(this)

  var gameID = 1;

  // Create an Akka system
  val config = ConfigFactory.load()
     .withValue("akka.loglevel", ConfigValueFactory.fromAnyRef("OFF"))
     .withValue("akka.stdout-loglevel", ConfigValueFactory.fromAnyRef("OFF"))
  val system = ActorSystem("WorkerSystem", config)

  // create the master
  var gameActor = system.actorOf(Props(new GameActor(gameID)), name = "game" + gameID)

  def createMaster() {
    system.stop(gameActor);
    gameID += 1
    gameActor = system.actorOf(Props(new GameActor(gameID)), name = "game" + gameID)
  }

  def update(e: Event) = {
    e match {
      case event: NewGame =>
        createMaster()
        controller.playground.player.foreach(player => gameActor ! CreatePlayer(player._2.uuid))
        gameActor ! GameEvent(event)
      case event: Update   => gameActor ! PlayerMove(event.move.player.uuid, event.move.player.direction)
      case event: GameWon  => gameActor ! GameEvent(event)
      case event: GameLost => gameActor ! GameEvent(event)
      case event: Wait     => None
    }
  }

}