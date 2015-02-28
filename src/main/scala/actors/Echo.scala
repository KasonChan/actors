package actors

import akka.actor.{Actor, ActorLogging}

/**
 * Created by kasonchan on 2/28/15.
 * Echo actor send the received messages back to sender*
 */
class Echo extends Actor with ActorLogging {
  def receive: PartialFunction[Any, Unit] = {
    case s: String =>
      log.info(s)
      sender() ! self.path.name + " " + s
    case x =>
      log.info(x.toString)
      sender() ! self.path.name + " " + x
  }
}
