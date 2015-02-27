package actors.routers.randomrouting

import akka.actor.{Actor, ActorLogging, Props}
import akka.routing.{ActorRefRoutee, RandomRoutingLogic, Router}

/**
 * Created by kasonchan on 2/26/15. *
 * References: *
 * http://doc.akka.io/docs/akka/snapshot/scala/routing.html *
 */
class RandomRouting extends Actor with ActorLogging {
  var router = {
    val routees = Vector.fill(2) {
      val r = context.actorOf(Props[RandomRoutingWorker])
      context watch r
      ActorRefRoutee(r)
    }
    Router(RandomRoutingLogic(), routees)
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
