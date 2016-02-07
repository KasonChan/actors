package demo

import actors.dynamics.Supervisor
import akka.actor.{ActorSystem, Props}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by kasonchan on 7/30/15.
  */
object Dynamics extends App {

  println("Dynamics")

  val system = ActorSystem("dynamics")

  val supervisors = system.actorOf(Props[Supervisor], "supervisor")

  for (i <- 1 to 10)
    supervisors ! i

  system.terminate()
  Await.result(system.terminate, Duration.Inf)

}
