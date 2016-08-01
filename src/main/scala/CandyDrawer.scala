import actors.{CandyDrawer, Customer, Logger, Register}
import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import spray.http.HttpResponse
import spray.routing.SimpleRoutingApp
import sweets.{Snickers, Sweets, Twix}
import akka.pattern.ask

import scala.concurrent.duration._

object CandyDrawer extends App with SimpleRoutingApp {

  val config = ConfigFactory.load()

  implicit val system = ActorSystem(config.getString("akka.system"), config)
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(5 seconds)

  val candyDrawer = system.actorOf(Props[CandyDrawer])

  val register = system.actorOf(Props[Register])

  val logger = system.actorOf(Props[Logger])

  def main() = {}

  startServer(interface = config.getString("http.interface"),
    port = config.getInt("http.port")) {
    path("candy") {
      post {
        formField('name, 'amount ?).as(SweetsIn) { in =>
          complete {
            in.name match {
              case "Snicker" => sendToCustomer(Snickers(in.amount.getOrElse(1)))
              case "Twix" => sendToCustomer(Twix(in.amount.getOrElse(1)))
              case _ => HttpResponse(400)
            }
          }
        }
      }
    }
  }

  def sendToCustomer(sweets: Sweets) = {
    (system.actorOf(Props[Customer]) ? sweets).map{
      case resp: String => HttpResponse(200, resp)
      case _ => HttpResponse(500)
    }
  }

}

case class SweetsIn(name: String, amount: Option[Int])