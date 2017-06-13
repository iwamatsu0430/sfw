package jp.iwmat.sfw.server

sealed trait Err[A] {
  def value: A
  def getMessage: String = ""
}

object HttpErr {
  case class InvalidRequestLine(value: String) extends Err[String]
  case class MethodNotFound(value: String) extends Err[String]

  object HttpVersion {
    case class InvalidFormat(value: String) extends Err[String]
    case class VersionNotFound(value: String) extends Err[String]
  }
}

object ServerErr {
  case class ThrowableWrapper(value: Throwable) extends Err[Throwable]
}
