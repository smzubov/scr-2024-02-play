package models.services

import models.dao.entities.{Product, ProductItem}
import models.dao.repositories.ProductRepositoryImpl

import javax.inject.{Inject, Singleton}

@Singleton
class ProductService @Inject()(productRepository: ProductRepositoryImpl) {

  def addProduct(product: Product, productItems: List[ProductItem]): Unit= {
    productRepository.addProduct(product, productItems)
  }

  def updateProduct(product: Product, productItems: List[ProductItem]): Unit = {
    productRepository.updateProduct(product, productItems)
  }

  def deleteProduct(id: String): Unit = {
    productRepository.deleteProduct(id)
  }

  def getProducts: List[(Product, List[ProductItem])] = {
    productRepository.getProducts
  }

  def filterProductsByTitle(title: String): List[(Product, List[ProductItem])] = {
    productRepository.filterProductsByTitle(title)
  }
}



