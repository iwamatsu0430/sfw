package jp.iwmat.sfw.http

import scalaz.\/

import jp.iwmat.sfw.server.Err
import jp.iwmat.sfw.server.HttpErr.HttpVersion._

trait HttpVersion(val enumTag: Int) extends Enum
object HttpVersion {
  case object V0_9 extends HttpVersion(0)
  case object V1_0 extends HttpVersion(1)
  case object V1_1 extends HttpVersion(2)
  case object V2 extends HttpVersion(3)

  def of(value: String): Err \/ HttpVersion = {
    val params = value.split("/")
    for {
      versionNumber <- if (params.length == 2 && params(0) == "HTTP")
        \/.right(params(1))
      else
        \/.left(InvalidFormat(value))
      httpVersion <- versionNumber match {
        case "0.9" => \/.right(V0_9)
        case "1.0" => \/.right(V1_0)
        case "1.1" => \/.right(V1_1)
        case "2" => \/.right(V2)
        case _ => \/.left(VersionNotFound(versionNumber))
      }
    } yield httpVersion
  }
}
