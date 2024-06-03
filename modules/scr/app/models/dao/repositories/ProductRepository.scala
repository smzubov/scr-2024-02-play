package models.dao.repositories

import models.dao.entities.{Product, ProductItem}
import models.dao.schema.ProductSchema





trait ProductRepository {

  def addProduct(product: Product, productItems: List[ProductItem]): Unit

  def updateProduct(product: Product, productItems: List[ProductItem]): Unit

  def deleteProduct(id: String): Unit

  def getProducts: List[(Product, List[ProductItem])]

  def filterProductsByTitle(title: String): List[(Product, List[ProductItem])]
}

class ProductRepositoryImpl extends ProductRepository{

  val products = ProductSchema.products
  val productItems = ProductSchema.productItems

  import org.squeryl.PrimitiveTypeMode._

  override def addProduct(product: Product, items: List[ProductItem]): Unit = transaction {
    products.insert(product)
    productItems.insert(items)
  }

  override def updateProduct(product: Product, items: List[ProductItem]): Unit = transaction {
    products.update(product)
    productItems.deleteWhere(_.productId === product.id)
    productItems.insert(items)
  }

  override def deleteProduct(id: String): Unit = transaction {
    productItems.deleteWhere(_.productId === id)
    products.deleteWhere(_.id === id)
  }

  override def getProducts: List[(Product, List[ProductItem])] = transaction {
    from(products, productItems)((p, pi) =>
      where(p.id === pi.productId)
        select (p, pi)
    ).toList.groupBy(_._1).map {
      case (product, items) => (product, items.map(_._2))
    }.toList
  }

  override def filterProductsByTitle(title: String): List[(Product, List[ProductItem])] = transaction {
    from(products, productItems)((p, pi) =>
      where(p.title === title and p.id === pi.productId)
        select (p, pi)
    ).toList.groupBy(_._1).map {
      case (product, items) => (product, items.map(_._2))
    }.toList
  }
}



