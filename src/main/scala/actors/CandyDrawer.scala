package actors

import akka.actor.Actor
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Subscribe
import sweets.Sweets

/**
  * Created by lange on 28.07.16.
  */
class CandyDrawer extends Actor {

  val mediator = DistributedPubSub(context.system).mediator
  mediator ! Subscribe("candy!", self)


  override def receive: Receive = {
    case sweets: Sweets => {
      context.system.eventStream.publish(Log(s"Customer took" +
        s"${sweets.amount} ${sweets.name} out of the drawer."))
    }
  }
}
