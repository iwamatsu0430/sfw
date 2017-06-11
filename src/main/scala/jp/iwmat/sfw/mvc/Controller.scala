package jp.iwmat.sfw.mvc

import jp.iwmat.sfw.http._

trait Controller {

  type JSON = jp.iwmat.sfw.json.JSON
  type Form = jp.iwmat.sfw.form.Form

  object Action {
    def apply[A](f: implicit Request[A] => Response) = ???
  }
}
