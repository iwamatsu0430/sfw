val dottyVersion = "0.1.2-RC1"
val scala212Version = "2.12.2"

lazy val root = (project in file("."))
  .settings(
    name := "sfw",
    version := "0.1",

    libraryDependencies ++= Seq(
      ("org.scalaz"     %% "scalaz-core"  % "7.2.13").withDottyCompat(),
      ("is.cir"         %% "ciris-core"   % "0.4.0").withDottyCompat(),
      ("org.scalatest"  %% "scalatest"    % "3.0.3"   % "test").withDottyCompat()
    ),

    scalaVersion := dottyVersion,
    crossScalaVersions := Seq(dottyVersion, scala212Version)
  )
