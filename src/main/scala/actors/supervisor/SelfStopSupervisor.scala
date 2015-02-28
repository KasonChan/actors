package actors.supervisor

import akka.actor.SupervisorStrategy._
import akka.actor.{Actor, ActorLogging, OneForOneStrategy, Props}

import scala.concurrent.duration._

/**
 * Created by kasonchan on 2/27/15.
 */
class SelfStopSupervisor extends Actor with ActorLogging {

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: ArithmeticException => Resume
      case _: NullPointerException => Restart
      case _: IllegalArgumentException => Stop
      case _: Exception => Escalate
    }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    log.info("Pre-restart")
  }

  override def postStop(): Unit = {
    log.info("Post-stop")
  }

  override def postRestart(reason: Throwable): Unit = {
    log.info("Post-restart")
  }

  override def preStart(): Unit = {
    log.info("Pre-start")
  }

  def receive: PartialFunction[Any, Unit] = {
    case i: Int =>
      val worker = context.actorOf(Props[Worker])
      worker ! i
    case s: String =>
      val worker = context.actorOf(Props[Worker])
      worker ! s
    case x =>
      log.warning(x.toString)
      val worker = context.actorOf(Props[Worker])
      worker ! x
  }
}
