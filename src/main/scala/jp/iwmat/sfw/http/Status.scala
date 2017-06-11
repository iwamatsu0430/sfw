package jp.iwmat.sfw.http

trait Status(val enumTag: Int, val text: String) extends Enum
object Status {
  // 2xx
  case object OK extends Status(200, "200 OK")
  case object Created extends Status(201, "201 Created")
  case object Accepted extends Status(202, "202 Accepted")
  case object NonAuthoritativeInformation extends Status(203, "203 Non-Authoritative Information")
  case object NoContent extends Status(204, "204 No Content")
  case object ResetContent extends Status(205, "205 Reset Content")
  case object PartialContent extends Status(206, "206 Partial Content")
  // TODO 3xx
  case object MultipleChoices extends Status(300, "300 Multiple Choices")
  case object MovedPermanently extends Status(301, "301 Moved Permanently")
  case object Found extends Status(302, "302 Found")
  case object SeeOther extends Status(303, "303 See Other")
  // TODO 4xx
  case object BadRequest extends Status(400, "400 Bad Request")
  case object Unauthorized extends Status(401, "401 Unauthorized")
  case object PaymentRequired extends Status(402, "402 Payment Required")
  case object Forbidden extends Status(403, "403 Forbidden")
  case object NotFound extends Status(404, "404 Not Found")
  // 5xx
  case object InternalServerError extends Status(500, "500 Internal Server Error")
  case object NotImplemented extends Status(501, "501 Not Implemented")
  case object BadGateway extends Status(502, "502 Bad Gateway")
  case object ServiceUnavailable extends Status(503, "503 Service Unavailable")
  case object GatewayTimeout extends Status(504, "504 Gateway Timeout")
  case object HTTPVersionNotSupported extends Status(505, "505 HTTP Version Not Supported")
}
