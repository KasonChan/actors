package actors.routers.roundrobin

import akka.actor.{Actor, ActorLogging, Props}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}

/**
 * Created by kasonchan on 2/26/15. *
 * References: *
 * http://doc.akka.io/docs/akka/snapshot/scala/routing.html *
 */
class RoundRobin extends Actor with ActorLogging {
  var router = {
    val routees = Vector.fill(2) {
      val r = context.actorOf(Props[RoundRobinWorker])
      context watch r
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }

  def receive = {
    case s: String =>
      log.info(s)
      //      Routes the message to the routee
      router.route(s, sender())
    case x =>
      log.warning(x.toString)
  }
}
