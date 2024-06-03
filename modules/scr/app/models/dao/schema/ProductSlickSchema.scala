package models.dao.schema

import models.dao.entities.{Product, ProductItem}
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag


class ProductSlickSchema (tag: Tag) extends Table[Product](tag, "Product") {
    def id = column[String]("id", O.PrimaryKey)

    def title = column[String]("title")

    def description = column[String]("description")

    def * = (id, title, description) <> (Product.tupled, Product.unapply)
}

private class ProductItemTable(tag: Tag) extends Table[ProductItem](tag, "ProductItem") {
  def id = column[String]("id", O.PrimaryKey)

  def price = column[Int]("price")

  def quantity = column[Int]("quantity")

  def inStock = column[Boolean]("inStock")

  def productId = column[String]("product_id")

  def * = (id, price, quantity, inStock, productId) <> (ProductItem.tupled, ProductItem.unapply)

  def productFK = foreignKey("product_fk", productId, TableQuery[ProductSlickSchema])(_.id, onDelete = ForeignKeyAction.Cascade)
}


