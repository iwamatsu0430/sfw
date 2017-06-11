package jp.iwmat.sfw.http

trait Request[A] {
  def headers: Map[String, String]
  def body: Body
}
