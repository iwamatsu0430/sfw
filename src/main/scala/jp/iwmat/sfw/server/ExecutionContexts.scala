package jp.iwmat.sfw.server

import java.util.concurrent.Executors

import scala.concurrent.{ ExecutionContext, ExecutionContextExecutorService }

import ciris._

case class ExecutionContexts(
  main: ExecutionContextExecutorService,
  client: ExecutionContextExecutorService
)

object ExecutionContexts {

  def initialize(): ExecutionContexts = {
    val main = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(Constants.defaults.threadPools.main))
    val clientThreadPools = loadConfig(prop[Int](Constants.properties.clientThreadPools))(identity)
      .right
      .getOrElse(Constants.defaults.threadPools.client)
    val client = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(clientThreadPools))
    ExecutionContexts(
      main,
      client
    )
  }
}
