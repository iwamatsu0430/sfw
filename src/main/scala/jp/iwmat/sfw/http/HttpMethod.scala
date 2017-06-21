package jp.iwmat.sfw.http

import scalaz.\/

import jp.iwmat.sfw.server.Err
import jp.iwmat.sfw.server.err.httpErr.MethodNotFound

trait HttpMethod(val enumTag: Int, val name: String) extends Enum
object HttpMethod {
  case object OPTIONS extends HttpMethod(0, "OPTIONS")
  case object HEAD extends HttpMethod(1, "HEAD")
  case object GET extends HttpMethod(2, "GET")
  case object POST extends HttpMethod(3, "POST")
  case object PUT extends HttpMethod(4, "PUT")
  case object DELETE extends HttpMethod(5, "DELETE")
  case object TRACE extends HttpMethod(6, "TRACE")
  case object CONNECT extends HttpMethod(7, "CONNECT")
  case object PATCH extends HttpMethod(8, "PATCH")

  def list: Set[HttpMethod] = Set(OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT)

  def of(value: String): Err[String] \/ HttpMethod = {
    list.find(_.name == value)
      .map(\/.right)
      .getOrElse(\/.left(MethodNotFound(value)))
  }
}
