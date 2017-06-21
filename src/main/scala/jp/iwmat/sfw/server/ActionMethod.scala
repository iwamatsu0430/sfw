package jp.iwmat.sfw.server

import java.lang.reflect.Method

import scala.util.matching.Regex

import scalaz.{ \/, -\/, \/- }

import jp.iwmat.sfw.mvc.Action
import jp.iwmat.sfw.http.HttpMethod
import jp.iwmat.sfw.server.Err
import jp.iwmat.sfw.server.err.mvcErr.routing._
import jp.iwmat.sfw.syntax._

case class ActionMethod(
  parentClass: Class[_],
  parentInstance: Any,
  underlying: Method,
  httpMethod: HttpMethod,
  path: String
) {

  class InvalidTypeRoute extends Throwable

  val pathRegex: Err[_] \/ Regex = {
    for {
      _ <- ActionMethod.validate(path)
      regexPath = ActionMethod.toRegexString(path)
      paths = regexPath.split("/")
      variables = paths.filter(_.startsWith(":"))
      paramTypes = underlying.getParameterTypes
      result <- tryE {
        paths
          .map {
            case p if p.startsWith(":") =>
              val index = variables.indexOf(p)
              paramTypes(index).getName match {
                case "int" => "(-?\\d+)"
                case "java.lang.String" => "(\\w+)"
                case _ => throw new InvalidTypeRoute
              }
            case p => p
          }
          .mkString("^", "/", "$")
      } match {
        case \/-(p) => \/.right(p.r)
        case -\/(e) => e.value match {
          case _: ArrayIndexOutOfBoundsException => -\/(EmptyRoute(this)) // FIXME
          case _: InstantiationException => -\/(EmptyRoute(this)) // FIXME
          case _: InvalidTypeRoute => -\/(EmptyRoute(this)) // FIXME
          case _ => -\/(EmptyRoute(this)) // FIXME
        }
      }
    } yield result
  }

  def invoke(params: Object*): Action = {
    underlying.invoke(parentInstance, params:_*).asInstanceOf[Action]
  }

  override def toString = s"${path}\t${parentClass.getName}::${underlying.getName}"
}

object ActionMethod {

  def of(
    parentClass: Class[_],
    underlying: Method,
    httpMethod: HttpMethod,
    path: String
  ): Err[_] \/ ActionMethod = {
    ???
  }

  def validate(path: String): Err[_] \/ String = {
    ???
  }

  def toRegexString(path: String): String = {
    path
      .replaceAll(".", "\\.")
      .replaceAll("$", "\\$")
      .replaceAll("!", "\\!")
      .replaceAll("?", "\\?")
      .replaceAll("+", "\\+")
      .replaceAll("(", "\\(")
      .replaceAll(")", "\\)")
  }
}
