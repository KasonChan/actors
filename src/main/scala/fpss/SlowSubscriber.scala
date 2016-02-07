package fpss

import akka.actor.{Actor, ActorLogging}

/**
  * Created by kasonchan on 2/7/16.
  */
class SlowSubscriber extends Actor with ActorLogging {
  override def postStop() {
    log.info("SlowSubscriber#postStop")
  }

  def receive: Actor.Receive = {
    case msg: String =>
      log.info(s"Received: $msg")
      Thread.sleep(1000)
  }
}
