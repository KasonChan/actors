package demo

import actors.MagicWorker
import actors.MagicWorker.{MagicString, Undo}
import actors.routers.randomrouting.RandomRouting
import actors.routers.roundrobin.{RoundRobin, RoundRobinWorker}
import actors.supervisor.Supervisor
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.routing.{RoundRobinPool, SmallestMailboxPool}

/**
 * Created by kasonchan on 2/26/15.
 * References: *
 * http://doc.akka.io/docs/akka/snapshot/scala/routing.html *
 * http://doc.akka.io/docs/akka/snapshot/scala/actors.html Recommended Practices *
 */
object Demo {
  def main(args: Array[String]) {
    val system: ActorSystem = ActorSystem("Routers")

    /**
     * Creates a router with the props of RoundRobin class which contains two
     * routees *
     */
    val roundRobin: ActorRef = system.actorOf(Props[RoundRobin], "RoundRobin")

    roundRobin ! "1"
    roundRobin ! "2"
    roundRobin ! "3"
    roundRobin ! "4"

    /**
     * Creates a router with RoundRobinPool logic and the props of RoundRobinWorker. *
     * It has two routees in this router. *
     */
    val roundRobin2: ActorRef =
      system.actorOf(RoundRobinPool(2).props(Props[RoundRobinWorker]), "RoundRobin2")

    roundRobin2 ! "5"
    roundRobin2 ! "6"
    roundRobin2 ! "7"
    roundRobin2 ! "8"

    /**
     * Creates a router with the props of RandomRouting class which contains * 
     * two routees. *
     */
    val randomRouting: ActorRef = system.actorOf(Props[RandomRouting], "RandomRouting")
    
    randomRouting ! "101"
    randomRouting ! "102"
    randomRouting ! "103"
    randomRouting ! "104"
    randomRouting ! "105"

    /**
     * Creates a router with SmallestMailBoxPool logic and the props of MagicWorker. *
     * It has two routees in this router. *
     */
    val smallestMailbox: ActorRef =
      system.actorOf(SmallestMailboxPool(2).props(MagicWorker.props("Magic string")), "SmallestMailbox")

    smallestMailbox ! "9"
    smallestMailbox ! MagicString("RandomPool")
    smallestMailbox ! "10"
    smallestMailbox ! "11"
    smallestMailbox ! "12"
    smallestMailbox ! Undo
    smallestMailbox ! "13"
    smallestMailbox ! "14"

    /**
     * Creates a supervisor actor *
     */
    val supervisor: ActorRef = system.actorOf(Props[Supervisor], "Supervisor")

    system.shutdown()
  }
}
