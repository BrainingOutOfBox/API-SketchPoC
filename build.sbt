name := """play-java-fileupload-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.8"

crossScalaVersions := Seq("2.11.12", "2.12.4")

libraryDependencies += guice

testOptions += Tests.Argument(TestFrameworks.JUnit, "-a", "-v")

// https://mvnrepository.com/artifact/org.mongodb/mongodb-driver-async
libraryDependencies += "org.mongodb" % "mongodb-driver-async" % "3.8.0"

// https://mvnrepository.com/artifact/org.modelmapper/modelmapper
libraryDependencies += "org.modelmapper" % "modelmapper" % "2.3.2"
