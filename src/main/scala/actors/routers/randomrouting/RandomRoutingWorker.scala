package actors.routers.randomrouting

import akka.actor.{Actor, ActorLogging}

/**
 * Created by kasonchan on 2/26/15.
 */
class RandomRoutingWorker extends Actor with ActorLogging {
  def receive = {
    case s: String =>
      log.info(s)
    case x =>
      log.warning(x.toString)
  }
}
