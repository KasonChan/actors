package actors.supervisor

import akka.actor.{Actor, ActorLogging}

import scala.util.control.NonFatal

/**
 * Created by kasonchan on 2/27/15.
 */
class SelfStopWorker extends Actor with ActorLogging {
  var state = 0

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    log.info("Pre-restart " + reason.toString)
  }

  override def postStop(): Unit = {
    log.info("Post-stop")
  }

  override def postRestart(reason: Throwable): Unit = {
    log.info("Post-restart " + reason.toString)
  }

  override def preStart(): Unit = {
    log.info("Pre-start")
  }

  def receive: PartialFunction[Any, Unit] = {
    case ex: Exception =>
      try {
        throw ex
      } catch {
        case NonFatal(ex) =>
          log.warning(ex.toString)
          context.stop(self)
      }
    case "How are you?" =>
      log.info("I am good!")
      context.stop(self)
    case x: Int =>
      log.info(x.toString)
      state = x
      context.stop(self)
    case "get" =>
      log.info("Get " + state.toString)
      context.stop(self)
  }
}
