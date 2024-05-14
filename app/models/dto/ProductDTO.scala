package models.dto

import play.api.libs.json.{Format, Json}

case class ProductDTO(id: String, title: String, description: String)


object ProductDTO {

  implicit val productFormat: Format[ProductDTO] = Json.format[ProductDTO]

}



