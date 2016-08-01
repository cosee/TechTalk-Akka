package actors

import akka.actor.Actor

/**
  * Created by lange on 28.07.16.
  */
class Logger extends Actor {

  context.system.eventStream.subscribe(self, classOf[Log])

  override def receive: Receive = {
    case log: Log => println(log.text)
  }
}

case class Log(text: String)
