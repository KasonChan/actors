package actors.dynamics

import akka.actor.SupervisorStrategy.{Resume, Stop}
import akka.actor.{Actor, ActorLogging, OneForOneStrategy, Props, _}

import scala.concurrent.duration._

/**
 * Created by kasonchan on 7/30/15.
 */
class Supervisor extends Actor with ActorLogging {

  var workers = Vector[ActorRef]()

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: ArithmeticException => Resume
      case _: NullPointerException => Stop
      case _: IllegalArgumentException => Stop
      case _: Exception => Stop
    }

  def receive = {
    case s: String =>
      val newWorker = context.actorOf(Props[Worker])
      context watch newWorker
      newWorker ! s
    case i: Int =>
      val newWorker = context.actorOf(Props[Worker])
      context watch newWorker
      newWorker ! i
    case Message(m) =>
      log.info(m)
      context.stop(sender())
    case _ =>
      log.info("Idk")
  }

}
