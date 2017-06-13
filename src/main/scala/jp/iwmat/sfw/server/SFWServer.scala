package jp.iwmat.sfw.server

import java.net._
import java.util.Scanner
import java.io.{ BufferedReader, InputStream, InputStreamReader }

import scala.concurrent.{ ExecutionContext, Future }

import scalaz.{ \/, -\/, \/- }

import jp.iwmat.sfw.http.{ Request, RequestLine }
import jp.iwmat.sfw.syntax._

// TODO: Refactoring for override by user
trait SFWServer {

  val serverSocket: ServerSocket

  val configuration: Configuration

  def listenHttp(): Future[Unit] = {
    def _listenHttp(): Unit = {
      tryE { serverSocket.accept() } map { socket =>
        Future(xxxClient(socket))(configuration.listenerEC)
      } match {
        case \/-(_) => _listenHttp() // Loop
        case -\/(err) => err.value match {
          case e: SocketTimeoutException => _listenHttp() // Loop
          case e: SocketException => // Exit
          case e => e.printStackTrace()
        }
      }
    }
    Future(_listenHttp())(configuration.serverEC)
  }

  def listenConsole(): Unit = {
    val scanner = new Scanner(System.in)
    while (scanner.hasNext()) {
      scanner.next()
    }
    close()
  }

  def xxxClient(socket: Socket): Unit = {
    (for {
      is <- tryE { socket.getInputStream() }
      request <- readInput(is)
      // TODO write output
    } yield ()) match {
      case \/-(_) =>
        socket.close()
      case -\/(e) =>
        println(e.getMessage)
        socket.close()
    }
  }

  def readInput(is: InputStream): Err[_] \/ Request = {
    for {
      br <- tryE { new BufferedReader(new InputStreamReader(is)) }
      rawRequestLine <- tryE { br.readLine() }
      requestLine <- RequestLine.of(rawRequestLine)
      _ = println(s"method: ${requestLine.method}, uri: ${requestLine.requestUri}, httpVersion: ${requestLine.httpVersion}")
      // TODO read request header and body
    } yield ???
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
      case \/-(server) =>
        println(s"SFW Server Start at http://localhost:${server.configuration.port}/")
        server.listenHttp()
        server.listenConsole()
        println(s"SFW Server Stop")
      case -\/(err) => err.value match {
        case e: IllegalArgumentException =>
          println(s"Port has used. Use another port.")
        case e =>
          e.printStackTrace()
      }
    }
  }

  def initialize(): Err[Throwable] \/ SFWServer = {
    val _configuration = Configuration.create()
    for {
      _serverSocket <- tryE { new ServerSocket(_configuration.port) }
      _ <- tryE { _serverSocket.setSoTimeout(acceptTimeout) }
    } yield new SFWServer {
      val serverSocket = _serverSocket
      val configuration = _configuration
    }
  }
}
