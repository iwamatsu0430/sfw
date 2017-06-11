package jp.iwmat.sfw.server

import java.util.concurrent.Executors

import scala.concurrent.{ ExecutionContext, ExecutionContextExecutorService }

import ciris._

trait Configuration {

  val port: Int

  val serverEC: ExecutionContextExecutorService

  val listenerEC: ExecutionContextExecutorService
}

object Configuration {

  val portProp = "sfw.port"

  val threadsProp = "sfw.threads"

  val defaultPort = 9000

  val defaultThreads = 4

  def create(): Configuration = {
    new Configuration {
      val port = loadConfig(prop[Int](portProp))(identity)
        .right
        .getOrElse(defaultPort)
      val serverEC = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(1))
      val listenerEC = {
        val threads = loadConfig(prop[Int](threadsProp))(identity)
          .right
          .getOrElse(defaultThreads)
        ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(threads))
      }
    }
  }
}
