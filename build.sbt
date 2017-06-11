val dottyVersion = "0.1.2-RC1"
val scala212Version = "2.12.2"

lazy val root = (project in file(".")).
  settings(
    name := "dotty-cross",
    version := "0.1",

    libraryDependencies ++= Seq(
      "org.scalaz"    % "scalaz-core_2.11"  % "7.2.13",
      "is.cir"        % "ciris-core_2.11"   % "0.4.0",
      "org.scalatest" % "scalatest_2.11"    % "3.0.3"   % "test"
    ),

    // To make the default compiler and REPL use Dotty
    scalaVersion := dottyVersion,

    // To cross compile with Dotty and Scala 2
    crossScalaVersions := Seq(dottyVersion, scala212Version)
  )
