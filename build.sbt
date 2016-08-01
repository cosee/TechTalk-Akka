name := """CandyDrawer"""

organization := """cosee"""

version := "0.0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= {
  val version = "2.4.8"

  Seq(
    "com.typesafe.akka" %% "akka-actor" % version,
    "com.typesafe.akka" %% "akka-cluster" % version,
    "com.typesafe.akka" %% "akka-cluster-tools" % version,

    "io.spray" %% "spray-can" % "1.3.3",
    "io.spray" %% "spray-routing" % "1.3.3"
  )
}