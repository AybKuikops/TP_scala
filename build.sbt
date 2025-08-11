name := "TP_scala"

version := "0.1"

scalaVersion := "2.13.16"


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "3.4.1"   
)
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.19"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test"

// Run compile/run tasks in a separate process to isolate from sbt
Compile / run / fork := true

// Allow access to internal Java APIs (sun.nio.ch) blocked by Java modules system
Compile / run / javaOptions ++= Seq(
  "--add-exports=java.base/sun.nio.ch=ALL-UNNAMED",
  "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED"
)

// Run tests in a separate process for isolation
Test / fork := true

// Allow access to internal Java APIs during tests
Test / javaOptions ++= Seq(
  "--add-exports", "java.base/sun.nio.ch=ALL-UNNAMED"
)

// libraryDependencies ++= Seq(
//   "com.nrinaudo" %% "kantan.csv" % "0.6.1",
//   "com.nrinaudo" %% "kantan.csv-generic" % "0.6.1", // pour mapper dans des case classes
//   "org.scalatest" %% "scalatest" % "3.2.15" % Test
// )
