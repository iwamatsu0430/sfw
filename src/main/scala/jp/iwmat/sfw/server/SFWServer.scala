package jp.iwmat.sfw.server

import java.net._
import java.util.Scanner

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success, Try }

trait SFWServer {

  val serverSocket: ServerSocket

  val configuration: Configuration

  def listenHttp(): Future[Unit] = {
    Future {
      def _listenHttp(): Unit = {
        (for {
          socket <- Try { serverSocket.accept() }
          _ = Future {

            import java.io.{ BufferedReader, InputStreamReader }
            import scalaz.{ \/, -\/, \/- }
            import jp.iwmat.sfw.http.RequestLine

            def toErr(t: Throwable): Err = ???

            (for {
              is <- \/.fromTryCatchNonFatal { socket.getInputStream() } leftMap (toErr)
              br <- \/.fromTryCatchNonFatal { new BufferedReader(new InputStreamReader(is)) } leftMap (toErr)
              rawRequestLine <- \/.fromTryCatchNonFatal { br.readLine() } leftMap (toErr)
              requestLine <- RequestLine.of(rawRequestLine)
              _ = println(s"method: ${requestLine.method}, uri: ${requestLine.requestUri}, httpVersion: ${requestLine.httpVersion}")
            } yield ()) match {
              case \/-(_) =>
                socket.close()
              case -\/(e) =>
                println(e.getMessage)
                socket.close()
            }

          }(configuration.listenerEC)
        } yield ()) match {
          case Success(_) | Failure(e: SocketTimeoutException) => _listenHttp()
          case Failure(e: SocketException) => // Exit
          case Failure(e) => e.printStackTrace()
        }
      }
      _listenHttp()
    }(configuration.serverEC)
  }

  def listenConsole(): Unit = {
    val scanner = new Scanner(System.in)
    while (scanner.hasNext()) {
      scanner.next()
    }
    close()
  }

  def close(): Unit = {
    serverSocket.close()
    configuration.serverEC.shutdown()
    configuration.listenerEC.shutdown()
  }
}

object SFWServer {

  val acceptTimeout = 1000

  def start(): Unit = {
    initialize() match {
      case Success(server) =>
        println(s"SFW Server Start at http://localhost:${server.configuration.port}/")
        server.listenHttp()
        server.listenConsole()
        println(s"SFW Server Stop")
      case Failure(e: IllegalArgumentException) =>
        println(s"Port has used. Use another port.")
      case Failure(e) =>
        e.printStackTrace()
    }
  }

  def initialize(): Try[SFWServer] = {
    val _configuration = Configuration.create()
    for {
      _serverSocket <- Try { new ServerSocket(_configuration.port) }
      _ <- Try { _serverSocket.setSoTimeout(acceptTimeout) }
    } yield new SFWServer {
      val serverSocket = _serverSocket
      val configuration = _configuration
    }
  }
}
