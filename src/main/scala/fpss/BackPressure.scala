package fpss

import akka.actor.{ActorSystem, DeadLetter, Props}
import com.typesafe.config.ConfigFactory

/**
  * Created by kasonchan on 2/7/16.
  */
object BackPressure {

  case object Ping

  val sys = ActorSystem("system", ConfigFactory.load("fpss"))
  val slow = sys.actorOf(Props[SlowSubscriber].withMailbox("bounded-mailbox"), "slow")
  val fast = sys.actorOf(Props(classOf[FastPublisher], slow), "fast")
  val watcher = sys.actorOf(Props(classOf[Watcher], slow), "watcher")
  sys.eventStream.subscribe(watcher, classOf[DeadLetter])

  def main(args: Array[String]) {
    fast ! Ping
  }

}
