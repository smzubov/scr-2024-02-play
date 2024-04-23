package models.dao.schema

import models.dao.entities.{Address, PhoneRecord}
import slick.lifted.{ProvenShape, Tag}
import slick.jdbc.PostgresProfile.api._

trait IdEntity {
  val id: String
}

abstract class IdEntityTable[T <: IdEntity](_tableTag: Tag, _tableName: String) extends Table[T](_tableTag, _tableName){
  def id = column[String]("id", O.PrimaryKey)
}

class PhoneRecordSlickSchema(tag: Tag) extends Table[PhoneRecord](tag, "PhoneRecord"){
  def id = column[String]("id", O.PrimaryKey)
  def phone = column[String]("phone")
  def fio = column[String]("fio")
  def addressId = column[String]("addressId")
  override def * = (id, phone, fio, addressId) <> (PhoneRecord.tupled, PhoneRecord.unapply)
}

class AddressSlickSchema(tag: Tag) extends Table[Address](tag, "Address"){
  def id = column[String]("id", O.PrimaryKey)
  def zipCode = column[String]("zipCode")
  def streetAddress = column[String]("streetAddress")

  override def * = (id, zipCode, streetAddress) <> (Address.tupled, Address.unapply)
}
