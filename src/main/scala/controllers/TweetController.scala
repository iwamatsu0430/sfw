package controllers

import scala.concurrent.Future

import jp.iwmat.sfw.mvc._

class TweetController extends Controller {

  @GET("/tweet")
  def list() = action {
    OK
  }

  @GET("/tweet/:id")
  def detail(id: Long) = async {
    Future.successful(OK)
  }

  @POST("/tweet")
  def add() = action[JSON] {
    OK("hogehoge")
  }
}
