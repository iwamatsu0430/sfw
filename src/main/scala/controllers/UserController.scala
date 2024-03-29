package controllers

import scala.concurrent.Future

import jp.iwmat.sfw.mvc._

class UserController extends Controller {

  def hello = "aaa"

  @GET("/users")
  def list() = action {
    OK
  }

  @GET("/users/:id")
  def detail(id: Long) = async {
    Future.successful(OK)
  }

  @POST("/users")
  def add() = action[JSON] {
    OK("hogehoge")
  }
}
