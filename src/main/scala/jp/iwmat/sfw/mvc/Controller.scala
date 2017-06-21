package jp.iwmat.sfw.mvc

import scala.concurrent.Future
import scala.util.Try

import jp.iwmat.sfw.http._

trait Controller {

  type JSON = jp.iwmat.sfw.json.JSON
  type Form = jp.iwmat.sfw.form.Form

  def action[A](f: implicit Request => Response): Action = ???

  def async[A](f: implicit Request => Future[Response]): Action = ???

  def deserialize[A](implicit request: Request): Try[A] = {
    request.body match {
      case body: Body.StringBody => ???
      case body: Body.BinaryBody => ???
      case body: Body.JSONBody => ???
      case Body.EmptyBody => ???
    }
  }
}
