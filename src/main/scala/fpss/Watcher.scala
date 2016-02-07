package fpss

import akka.actor._

/**
  * Created by kasonchan on 2/7/16.
  */
class Watcher(target: ActorRef) extends Actor with ActorLogging {
  private val targetPath = target.path

  override def preStart() {
    context.watch(target)
  }

  def receive: Actor.Receive = {
    case d: DeadLetter =>
      if (d.recipient.path.equals(targetPath)) {
        log.info(s"Timed out message: ${d.message.toString}")
      }

    case Terminated(`target`) =>
      context.system.terminate()
  }
}
