package actors.dynamics

import akka.actor.{Actor, ActorLogging}

/**
 * Created by kasonchan on 7/30/15.
 */
class Worker extends Actor with ActorLogging {

  def receive = {
    case s: String =>
      log.info(sender().path.name + ": " + s)
      sender() ! Message(context.self.path.name + ": " + s)
    case i: Int =>
      log.info(sender().path.name + ": " + i)
      sender() ! Message(context.self.path.name + ": " + i)
    case _ =>
      println("I don't understand")
  }

}
