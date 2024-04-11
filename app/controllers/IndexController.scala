package controllers

import models.{Paging, User, UserId}
import play.api.libs.json.{JsValue, Json, Writes}
import play.api.mvc.{Action, AnyContent, Controller, Cookie, DiscardingCookie}

case class Foo(f: String)

object Foo{
  implicit val writer: Writes[Foo] = Json.writes[Foo]
}

object IndexController extends Controller{

  def action1 = Action{
    Ok
  }

  def action2 = Action{
    NotFound
  }

  def action3 = Action{
    BadRequest
  }

  def action4 = Action{
    InternalServerError("Ooops")
  }

  def action5 = Action{
    Status(488)("Hello 488")
  }

  def action6 = Action{
    Ok(Json.toJson(Foo("foo")))
  }

  def action7(number: Int) = Action{
    Ok(s"The number is $number")
  }

  def action8(number: Int) = Action{
    Ok(s"The number is $number")
  }

  def action9 = Action{
    Ok("<h1>Hello world</h1>").as(HTML)
  }

  def action10 = Action{
    Ok("<h1>Hello world</h1>")
      .as(HTML).withHeaders(CACHE_CONTROL -> "max-age=3600", ETAG -> "xxx")
  }

  def action11 = Action{
    Ok("<h1>Hello world</h1>")
      .as(HTML).withCookies(Cookie("user", "user@mail.com"))
  }

  def action12 = Action{ rc =>
    Ok("Welcome!")
      .withSession(rc.session - "connected")
  }

  def action13 = Action{ rc =>
    rc.session.get("connected").map{ user =>
      Ok("Hello " + user)
    }.getOrElse(
      Unauthorized("You are not connected")
    )
  }

  def action14(userId: UserId) = Action{
    Ok(userId.toString)
  }

  def action15(paging: Option[Paging]) = Action{
    Ok(paging.toString)
  }

  // body

  def action16 = Action{ rc =>
    val body: AnyContent = rc.body
    val textBody: Option[String] = body.asText
    textBody.map{ str =>
      Ok("Got " + str)
    }.getOrElse{
      BadRequest("Expecting text/plain request")
    }
  }

  def action17 = Action(parse.text){ rc =>
    val body: String = rc.body
    Ok("Got " + body)
  }

  def action18 = Action(parse.json[User]){ rc =>
    val body: User = rc.body
    Ok(body.toString)
  }

}

