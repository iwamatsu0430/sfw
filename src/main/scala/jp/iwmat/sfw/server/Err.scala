package jp.iwmat.sfw.server

sealed trait Err[A] {
  def value: A
  def getMessage: String = ""
}

object Err {
  trait HttpErr[A] extends Err[A]
  object HttpErr {
    trait HttpVersion[A] extends HttpErr[A]
  }
  trait MVCErr[A] extends Err[A]
  object MVCErr {
    trait Routing[A] extends MVCErr[A]
  }
  trait ServerErr[A] extends Err[A]
}


package object err {

  object httpErr {
    case class InvalidRequestLine(value: String) extends Err.HttpErr[String]
    case class MethodNotFound(value: String) extends Err.HttpErr[String]

    object httpVersion {
      case class InvalidFormat(value: String) extends Err.HttpErr.HttpVersion[String]
      case class VersionNotFound(value: String) extends Err.HttpErr.HttpVersion[String]
    }
  }

  object mvcErr {
    object routing {
      case class CannotUseVariableAtHead(value: ActionMethod) extends Err.MVCErr.Routing[ActionMethod]
      case class EmptyRoute(value: ActionMethod) extends Err.MVCErr.Routing[ActionMethod]
    }
  }

  object serverErr {
    case class ThrowableWrapper(value: Throwable) extends Err.ServerErr[Throwable]
  }
}
