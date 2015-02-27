package actors.supervisor

import akka.actor.Actor

/**
 * Created by kasonchan on 2/26/15.
 */
class Worker extends Actor {
  var state = 0
  
  def receive = {
    case ex: Exception => throw ex
    case x: Int => state = x
    case "get" => sender() ! state
  }
}
