package models

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Json, Reads}

case class User(id: UserId, email: Email)

object User{

//  implicit val reads: Reads[User] = (
//    (JsPath \ "id").read[UserId] and
//      (JsPath \ "email").read[Email]
//  )(User.apply _)

  implicit val reads = Json.reads[User]

}

case class Email(raw: String)

object Email{
  implicit val reads: Reads[Email] = Json.reads[Email]
}
