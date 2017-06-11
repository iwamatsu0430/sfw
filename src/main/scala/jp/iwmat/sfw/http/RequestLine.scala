package jp.iwmat.sfw.http

import scalaz.\/

import jp.iwmat.sfw.server.Err
import jp.iwmat.sfw.server.HttpErr.InvalidRequestLine

trait RequestLine {
  def method: Method
  def requestUri: String
  def httpVersion: HttpVersion
}

object RequestLine {
  def of(requestLine: String): Err \/ RequestLine = {
    val params = requestLine.split(" ")
    for {
      _ <- if (params.length == 3) \/.right(()) else \/.left(InvalidRequestLine(requestLine))
      _method <- Method.of(params(0))
      _httpVersion <- HttpVersion.of(params(2))
    } yield new RequestLine {
      val method = _method
      val requestUri = params(1)
      val httpVersion = _httpVersion
    }
  }
}
