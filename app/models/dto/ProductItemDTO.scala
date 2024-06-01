package models.dto

import play.api.libs.json.{Format, Json}

case class ProductItemDTO(id: String, price: Int, quantity: Int, inStock: Boolean)

object ProductItemDTO {

  implicit val productItemFormat: Format[ProductItemDTO] = Json.format[ProductItemDTO]

}
