lazy val commonSettings = Seq(
  organization := "com.mojitoverde",
  version := "1.0-SNAPSHOT",
  scalaVersion := "2.11.7"
)
//ref: http://www.scala-sbt.org/0.13/docs/Library-Dependencies.html
//ref datastax sbt repo:https://mvnrepository.com/artifact/org.apache.cassandra/apache-cassandra/3.7
lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "sport-poc",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "2.2.4" % "test",
      "com.typesafe.play" %% "play" % "2.3.10"
    )
  )
