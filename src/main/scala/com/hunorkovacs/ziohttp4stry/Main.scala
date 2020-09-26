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
