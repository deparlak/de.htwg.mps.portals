package de.htwg.mps.portals.actor

import scala.collection.immutable.ListMap
import scala.collection.mutable.HashMap
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props

class MasterActor(val masterId: Int, var resultCount: Int = 0) extends Actor {

  val players = new HashMap[String, ActorRef]
  val results = new HashMap[String, Int]

  def receive = {
    case CreatePlayer(id) =>
        players.+=((id, context.actorOf(Props(new PlayerActor(id)), "PlayerActor_" + masterId + "_" + id)))
//        println("Create PlayerActor: PlayerActor_" + masterId + "_" + id)

    case PlayerMove(id, direction) =>
      players.get(id).get ! PlayerMove(id, direction)

    case GameEvent(event) =>
      players.foreach(player => player._2 ! GameEvent(event))
      println(event)

    case Result(id, right, left, up, down, moves) =>
      resultCount += 1
      results.+=((id, moves))

      println("Player " + (id + "     ").substring(0, 5) +
        " - Right: " + right + " Left: " + left + " Up: " + up + " Down: " + down)

      if (resultCount == players.size) {
        val moves = ListMap(results.toSeq.sortWith(_._2 > _._2): _*)
        println("\nThe player with the most moves is: " +
          (moves.head._1 + "     ").substring(0, 5) + " with " + moves.head._2 + " moves in total")
        context.stop(self)
      }
  }
}

