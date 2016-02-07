package fpss

import akka.actor.{Actor, ActorLogging, ActorRef, PoisonPill}

import scala.concurrent.duration._

/**
  * Created by kasonchan on 2/7/16.
  */
class FastPublisher(slow: ActorRef) extends Actor with ActorLogging {
  override def postStop() {
    log.info("FastSender#postStop")
    context.system.scheduler.scheduleOnce(2.seconds, slow, PoisonPill)(context.dispatcher)
  }

  def receive: Actor.Receive = {
    case _ =>
      for (i <- 1 to 15) {
        slow ! s"[$i]"
      }
      log.info("Done sending all")
      self ! PoisonPill
  }
}
