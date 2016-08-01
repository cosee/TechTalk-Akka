package actors

import akka.actor.Actor
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Subscribe
import sweets.Sweets

/**
  * Created by lange on 28.07.16.
  */
class Register extends Actor {

  val mediator = DistributedPubSub(context.system).mediator
  mediator ! Subscribe("candy!", self)

  var money = 0.0

  override def receive: Receive = {
    case sweets: Sweets =>
      val payedAmount = sweets.amount * sweets.price
      money += payedAmount
      context.system.eventStream.publish(Log(s"Customer payed ${payedAmount}."))
      context.system.eventStream.publish(Log(s"I contain ${money}."))
  }
}
