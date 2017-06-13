package jp.iwmat.sfw.mvc

import scala.concurrent.Future
import scala.util.Try

import jp.iwmat.sfw.http._

trait Controller {

  type JSON = jp.iwmat.sfw.json.JSON
  type Form = jp.iwmat.sfw.form.Form

  object Action {
    def apply[A](f: implicit Request => Response) = ???
    def async[A](f: implicit Request => Future[Response]) = ???
  }

  def deserialize[A](implicit request: Request): Try[A] = {
    request.body match {
      case body: Body.StringBody => ???
      case body: Body.BinaryBody => ???
      case body: Body.JSONBody => ???
      case Body.EmptyBody => ???
    }
  }
}
