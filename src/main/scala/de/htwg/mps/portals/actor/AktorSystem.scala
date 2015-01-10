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

class AktorSystem(implicit val bindingModule: BindingModule) extends Observer[Event] with Injectable {
  val controller = inject[IController]
  controller.add(this)

  // Create an Akka system
  val system = ActorSystem("WorkerSystem")

  // create the master
  var master = system.actorOf(Props(new MasterActor), name = "master")

  
  def createActorForPlayer(id: String) {
    if (master.isTerminated)
      master = system.actorOf(Props(new MasterActor), name = "master2")
      
    master ! CreatePlayer(id)
  }

  def move(id: String, direction: Direction) {
    master ! PlayerMove(id, direction)
  }

  def event(event: Event) {
    master ! GameEvent(event)
  }

  def update(e: Event) = {
    e match {
      case x: NewGame  => master ! GameEvent(x)
      case x: Update   => master ! PlayerMove(x.move.player.uuid, x.move.player.direction)
      case x: GameWon  => master ! GameEvent(x)
      case x: GameLost => master ! GameEvent(x)
      case x: Wait     => None
    }
  }

}