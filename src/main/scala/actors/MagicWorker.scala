package actors

import actors.MagicWorker.{MagicString, Undo}
import akka.actor.{Actor, ActorLogging, Props}

/**
 * Created by kasonchan on 2/26/15. *
 * References: *
 * http://doc.akka.io/docs/akka/snapshot/scala/actors.html Recommended Practices *
 * http://doc.akka.io/docs/akka/snapshot/scala/actors.html#actor-hotswap *
 */
object MagicWorker {

  def props(magicString: String): Props = Props(new MagicWorker(magicString))

  case class MagicString(s: String)

  case object Undo

}

class MagicWorker(magicString: String) extends Actor with ActorLogging {
  def active(ms: String): Receive = {
    case s: String =>
      log.info(ms + ": " + s)
    case MagicString(s) =>
      log.info("Became " + s)
      //      Replace instead of push on top
      context.become(active(s))
    case Undo =>
      log.info("Unbecame")
      //      Reset the latest become
      context.unbecome()
    case x =>
      log.warning(x.toString)
  }

  def receive = active(magicString)
}
