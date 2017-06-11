package jp.iwmat.sfw.server

sealed trait Err {
  def getMessage: String = ""
}

object HttpErr {
  case class InvalidRequestLine(value: String) extends Err
  case class MethodNotFound(value: String) extends Err

  object HttpVersion {
    case class InvalidFormat(value: String) extends Err
    case class VersionNotFound(version: String) extends Err
  }
}

case class Errs(errs: Seq[Err] = Seq()) {
  def add(err: Err): Errs = copy(errs = errs :+ err)
}
