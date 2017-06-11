package jp.iwmat.sfw.mvc

import jp.iwmat.sfw.http._
import jp.iwmat.sfw.json.JSON

case class OK(body: Body, headers: Map[String, String]) extends Response(Status.OK)
case object OK extends Response(Status.OK) {
  val body: Body = EmptyBody
  val headers: Map[String, String] = Map()
  def apply[A <: String | Array[Byte] | JSON](body: A): Response = body match {
    case value: String => OK(StringBody(value), headers)
    case value: Array[Byte] => OK(BinaryBody(value), headers)
    case value: JSON => OK(JSONBody(value), headers)
  }
}
