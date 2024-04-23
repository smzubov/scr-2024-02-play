package models.dao.entities

import org.squeryl.KeyedEntity

case class PhoneRecord(id: String, phone: String, fio: String, addressId: String) extends KeyedEntity[String]

case class Address(id: String, zipCode: String, streetAddress: String)
