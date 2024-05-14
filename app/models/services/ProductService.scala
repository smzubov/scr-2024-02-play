package models.services

import models.entities.{Product,ProductItem}

import scala.collection.mutable



class ProductService {
  private val products: mutable.Map[String, (Product, List[ProductItem])] = mutable.Map()

  def addProduct(product: Product, productItems: List[ProductItem]): Unit = {
    products += (product.id -> (product, productItems))
  }

  def updateProduct(product: Product, productItem: List[ProductItem]): Unit = {
    products.get(product.id) match {
      case Some(_) => products.update(product.id, (product, productItem))
      case None => throw new IllegalArgumentException("Product not found")
    }
  }

  def deleteProduct(id: String): Unit = {
    products -= id
  }

  def getProducts: List[(Product, List[ProductItem])] = {
    products.values.toList
  }

  def filterProductsByTitle(title: String): List[(Product, List[ProductItem])] = {
    products.values.filter { case (product, _) =>
      product.title == title
    }.toList
  }


}


