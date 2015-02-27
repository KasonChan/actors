package actors.routers.roundrobin

import akka.actor.{Actor, ActorLogging}

/**
 * Created by kasonchan on 2/26/15.
 */
class RoundRobinWorker extends Actor with ActorLogging {
  def receive = {
    case s: String =>
      log.info(s)
    case x =>
      log.warning(x.toString)
  }
}
