val Http4sVersion  = "1.0.0-M32"

lazy val root = (project in file("."))
  .settings(
    organization := "com.hunorkovacs",
    name := "zio-http4s-example",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.8",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s" %% "http4s-dsl"          % Http4sVersion,
      "dev.zio"    %% "zio"                 % "1.0.13",
      "dev.zio"    %% "zio-interop-cats"    % "3.2.9.1",
      "org.slf4j"  %  "slf4j-simple"        % "1.7.36"
    )
  )

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Xfatal-warnings"
)
