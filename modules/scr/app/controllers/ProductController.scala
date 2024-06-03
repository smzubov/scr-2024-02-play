package controllers

import com.google.inject.Inject
import models.dao.entities.{Product, ProductItem}
import models.dto.{ProductDTO, ProductItemDTO, ProductWithItemsDTO}
import models.services.ProductService
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, AnyContent, Controller}



class ProductController @Inject()(val productService: ProductService) extends Controller {


  def addProduct(): Action[JsValue] = Action(parse.json) { implicit request =>
    request.body.validate[ProductDTO].fold(
      errors => BadRequest(errors.toString),
      productDTO => {
        val product = Product(productDTO.id, productDTO.title, productDTO.description)
        val productItems = (request.body \ "productItems").as[List[ProductItemDTO]].map(item =>
          ProductItem(item.id, item.price, item.quantity, item.inStock, item.productId))
        productService.addProduct(product, productItems)
        Ok("Product added successfully")
      }
    )
  }

  def updateProduct(): Action[JsValue] = Action(parse.json) { implicit request =>
    request.body.validate[ProductDTO].fold(
      errors => BadRequest(errors.toString),
      productDTO => {
        val product = Product(productDTO.id, productDTO.title, productDTO.description)
        val productItems = (request.body \ "productItems").as[List[ProductItemDTO]].map(item =>
          ProductItem(item.id, item.price, item.quantity, item.inStock, item.productId))
        productService.updateProduct(product, productItems)
        Ok("Product updated successfully")
      }
    )
  }

  def deleteProduct(id: String): Action[AnyContent] = Action {
    productService.deleteProduct(id)
    Ok(s"Product $id deleted successfully")
  }

  def getProducts: Action[AnyContent] = Action {
    val products = productService.getProducts
    val productsDTO = products.map { case (product, productItems) =>
      val productItemDTOs = productItems.map(item =>
        ProductItemDTO(item.id, item.price, item.quantity, item.inStock, item.productId))
      val productDTO = ProductDTO(product.id, product.title, product.description)
      ProductWithItemsDTO(productDTO, productItemDTOs)
    }
    Ok(Json.toJson(productsDTO))
  }


  def filterProductsByTitle(title: String): Action[AnyContent] = Action {
    val products = productService.filterProductsByTitle(title)
    val productsDTO = products.map { case (product, productItems) =>
      val productItemDTOs = productItems.map(item =>
        ProductItemDTO(item.id, item.price, item.quantity, item.inStock, item.productId))
      val productDTO = ProductDTO(product.id, product.title, product.description)
      ProductWithItemsDTO(productDTO, productItemDTOs)
    }
    Ok(Json.toJson(productsDTO))
  }
}


