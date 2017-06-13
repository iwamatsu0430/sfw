package jp.iwmat.sfw.http

trait Request {
  def requestLine: RequestLine
  def headers: Map[String, String]
  def body: Body
}
