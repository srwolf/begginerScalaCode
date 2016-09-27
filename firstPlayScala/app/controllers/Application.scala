package controllers

import play.api._
import play.api.mvc._

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def greetings(name: String) = Action {
    Ok(views.html.index("Hi we are here.I'am "+ name))
  }

}
