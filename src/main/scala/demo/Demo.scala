package demo

import actors.MagicWorker.{MagicString, Undo}
import actors.routers.randomrouting.RandomRouting
import actors.routers.roundrobin.{RoundRobin, RoundRobinWorker}
import actors.supervisor.{SelfStopSupervisor, Supervisor}
import actors.{Echo, MagicWorker}
import akka.actor.{ActorRef, ActorSystem, Inbox, Props}
import akka.routing.{RoundRobinPool, SmallestMailboxPool}

import scala.concurrent.duration._

/**
 * Created by kasonchan on 2/26/15.
 * References: *
 * http://doc.akka.io/docs/akka/snapshot/scala/routing.html *
 * http://doc.akka.io/docs/akka/snapshot/scala/actors.html Recommended Practices *
 */
object Demo {

  def main(args: Array[String]) {

    /**
     * Create an actor system called Routers*
     */
    val system: ActorSystem = ActorSystem("Routers")

    /**
     * Create inbox for the system * 
     */
    val inbox = Inbox.create(system)

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
     * Creates a supervisor actor with SelfStopWorker*
     */
    val selfStopSupervisor: ActorRef = system.actorOf(Props[SelfStopSupervisor], "SelfStopSupervisor")

    selfStopSupervisor ! "How are you?"

    selfStopSupervisor ! 1
    selfStopSupervisor ! "get"

    selfStopSupervisor ! new ArithmeticException
    selfStopSupervisor ! "get"

    selfStopSupervisor ! 100
    selfStopSupervisor ! "get"

    /**
     * Creates a supervisor actor with Worker*
     */
    val supervisor: ActorRef = system.actorOf(Props[Supervisor], "Supervisor")

    supervisor ! "How are you?"

    supervisor ! 1
    supervisor ! "get"

    supervisor ! new ArithmeticException
    supervisor ! "get"

    supervisor ! 100
    supervisor ! "get"

    /**
     * Create a Echo actor * 
     */
    val echo: ActorRef = system.actorOf(Props[Echo], "Echo")

    inbox.send(echo, "How do you do?")
    val echoReply = inbox.receive(1.seconds)
    println(s"$echoReply")

    system.shutdown()
    system.awaitTermination()
  }
}
