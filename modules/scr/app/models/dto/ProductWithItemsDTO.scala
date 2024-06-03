package models.dto

import play.api.libs.json.{Format, Json}

case class ProductWithItemsDTO(product: ProductDTO, productItems: List[ProductItemDTO])

object ProductWithItemsDTO {
  implicit val format: Format[ProductWithItemsDTO] = Json.format[ProductWithItemsDTO]
}
