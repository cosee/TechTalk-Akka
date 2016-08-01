package actors

import akka.actor.Actor
import akka.actor.Actor.Receive
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Publish
import sweets.Sweets

/**
  * Created by lange on 28.07.16.
  */
class Customer extends Actor {

  val mediator = DistributedPubSub(context.system).mediator

  override def receive: Receive = {
    case sweets: Sweets =>
      sender ! s"I want ${sweets.amount} ${sweets.name}!"
      mediator ! Publish("candy!", sweets)
  }
}
