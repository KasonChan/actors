name := "Actors"

version := "1.0"

scalaVersion := "2.11.7"

val akkaV = "2.4.1"
val akkaEV = "2.0.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaV,
  "com.typesafe.akka" %% "akka-testkit" % akkaV,
  "com.typesafe.akka" % "akka-stream-experimental_2.11" % akkaEV,
  "com.typesafe.akka" % "akka-http-core-experimental_2.11" % akkaEV,
  "com.typesafe.akka" % "akka-http-experimental_2.11" % akkaEV,
  "org.scalatest" %% "scalatest" % "2.1.6" % "test"
)
