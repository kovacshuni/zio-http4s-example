# ZIO HTTP4s Simple Example

Here's how to put toghether almost the simplest [http4s](https://http4s.org/) server with [ZIO](https://zio.dev/).

### Run: 

`sbt run`

### Try: 

`curl localhost:8080/hello`

### Dependencies:

```scala
"org.http4s"      %% "http4s-blaze-server" % "1.0.1",
"org.http4s"      %% "http4s-dsl"          % "1.0.1",
"dev.zio"         %% "zio"                 % "1.0.1",
"dev.zio"         %% "zio-interop-cats"    % "2.1.4.0"
```

### Code:

```scala
package com.hunorkovacs.ziohttp4stry

import zio._
import zio.console._
import zio.interop.catz._
import zio.interop.catz.implicits._

import org.http4s._
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends zio.App {

  private val dsl = Http4sDsl[Task]
  import dsl._

  private val helloWorldService = HttpRoutes
    .of[Task] {
      case GET -> Root / "hello" => Ok("Hello, Joe")
    }
    .orNotFound

  def run(args: List[String]): zio.URIO[zio.ZEnv, ExitCode] =
    ZIO
      .runtime[ZEnv]
      .flatMap { implicit runtime =>
        BlazeServerBuilder[Task](global)
          .bindHttp(8080, "localhost")
          .withHttpApp(helloWorldService)
          .resource
          .toManagedZIO
          .useForever
          .foldCauseM(            
            err => putStrLn(err.prettyPrint).as(ExitCode.failure),
            _ => ZIO.succeed(ExitCode.success)
          )
      }

}

```

### random keywords:
zio, instead of cats, cats-effect, final tagless, managed, resource, http server, http4s, effect tracking, typesafe,
strongly typed, functional, monad
