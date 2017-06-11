package jp.iwmat.sfw.http

import jp.iwmat.sfw.json.JSON

trait Body
case class StringBody(value: String) extends Body
case class BinaryBody(value: Array[Byte]) extends Body
case class JSONBody(value: JSON) extends Body
case object EmptyBody extends Body
