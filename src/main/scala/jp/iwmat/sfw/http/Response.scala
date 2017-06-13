package jp.iwmat.sfw.http

trait Response(val status: Status) { self =>
  def body: Body
  def headers: Map[String, String]
  def setHeaders(headers: (String, String)*): Response = new Response(status) {
    val body = self.body
    val headers = headers.toMap
  }

  def bodyHandler[A <: Body](body: Body): A = {
    // body match {
    //   case value: String => Body.StringBody(value)
    //   case value: Array[Byte] => Body.BinaryBody(value)
    //   case value: JSON => Body.JSONBody(value)
    // }
    ???
  }
}
