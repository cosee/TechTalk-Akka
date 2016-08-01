package sweets

/**
  * Created by lange on 28.07.16.
  */
trait Sweets {
  val name: String
  val price: Double
  val amount: Int
}

case class Snickers(amount: Int = 1) extends Sweets {
  val name: String = "Snickers"
  val price = 0.7
}

case class Twix(amount: Int = 1) extends Sweets {
  val name = "Twix"
  val price = 1.0
}