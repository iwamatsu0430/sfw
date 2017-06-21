package jp.iwmat.sfw.http

import scalaz.\/

import jp.iwmat.sfw.server.Err
import jp.iwmat.sfw.server.err.httpErr.InvalidRequestLine

trait RequestLine {
  def method: HttpMethod
  def requestUri: String
  def httpVersion: HttpVersion
}

object RequestLine {
  def of(requestLine: String): Err[String] \/ RequestLine = {
    val params = requestLine.split(" ")
    for {
      _ <- if (params.length == 3) \/.right(()) else \/.left(InvalidRequestLine(requestLine))
      _method <- HttpMethod.of(params(0))
      _httpVersion <- HttpVersion.of(params(2))
    } yield new RequestLine {
      val method = _method
      val requestUri = params(1)
      val httpVersion = _httpVersion
    }
  }
}
