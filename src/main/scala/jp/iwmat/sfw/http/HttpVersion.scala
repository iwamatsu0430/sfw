package jp.iwmat.sfw.http

import scalaz.\/

import jp.iwmat.sfw.server.Err
import jp.iwmat.sfw.server.err.httpErr.httpVersion._

trait HttpVersion(val enumTag: Int) extends Enum
object HttpVersion {
  case object V0_9 extends HttpVersion(0)
  case object V1_0 extends HttpVersion(1)
  case object V1_1 extends HttpVersion(2)
  case object V2 extends HttpVersion(3)

  def of(value: String): Err[String] \/ HttpVersion = {
    val params = value.split("/")

    def isValidFormat = if (params.length == 2 && params(0) == "HTTP")
      \/.right(())
    else
      \/.left(InvalidFormat(value))

    def findVersion = params(1) match {
      case "0.9" => \/.right(V0_9)
      case "1.0" => \/.right(V1_0)
      case "1.1" => \/.right(V1_1)
      case "2" => \/.right(V2)
      case v => \/.left(VersionNotFound(v))
    }

    for {
      _ <- isValidFormat
      httpVersion <- findVersion
    } yield httpVersion
  }
}
