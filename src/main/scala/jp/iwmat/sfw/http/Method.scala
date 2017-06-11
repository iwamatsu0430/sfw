package jp.iwmat.sfw.http

import scalaz.\/

import jp.iwmat.sfw.server.Err
import jp.iwmat.sfw.server.HttpErr.MethodNotFound

trait Method(val enumTag: Int, val name: String) extends Enum
object Method {
  case object OPTIONS extends Method(0, "OPTIONS")
  case object GET extends Method(1, "GET")
  case object HEAD extends Method(2, "HEAD")
  case object POST extends Method(3, "POST")
  case object PUT extends Method(4, "PUT")
  case object DELETE extends Method(5, "DELETE")
  case object TRACE extends Method(6, "TRACE")
  case object CONNECT extends Method(7, "CONNECT")

  def list: Set[Method] = Set(OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT)

  def of(value: String): Err \/ Method = {
    list.find(_.name == value) match {
      case Some(method) => \/.right(method)
      case None => \/.left(MethodNotFound(value))
    }
  }
}
