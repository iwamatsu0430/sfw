package controllers

import jp.iwmat.sfw.mvc._

class UserController extends Controller {

  @GET("/users")
  def list() = Action {
    OK
  }

  @GET("/users/:id")
  def detail(id: Long) = Action {
    OK
  }

  @POST("/users")
  def add() = Action[JSON] {
    OK("hogehoge")
  }
}
