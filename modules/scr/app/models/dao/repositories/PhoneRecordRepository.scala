package models.dao.repositories

import models.dao.entities.{Address, PhoneRecord}
import models.dao.schema.PhoneBookSchema


trait PhoneRecordRepository {
  def find(phone: String): Option[PhoneRecord]
  def findWithAddress(phone: String): Option[(PhoneRecord, Address)]
  def findWithAddress2(phone: String): Option[(PhoneRecord, Option[Address])]
  def list(): List[PhoneRecord]
  def listWithAddressCount(): List[(String, Long)]
  def insert(phoneRecord: PhoneRecord): Unit
  def insert(address: Address): Unit
  def update(phoneRecord: PhoneRecord): Unit
  def delete(id: String): Unit
}

class PhoneRecordRepositoryImpl extends PhoneRecordRepository{
  val phoneRecords = PhoneBookSchema.phoneRecords
  val addresses = PhoneBookSchema.addresses

  import org.squeryl.PrimitiveTypeMode._

  override def find(phone: String): Option[PhoneRecord] =
    transaction(from(phoneRecords)(r => where(r.phone === phone) select(r) ).headOption)

  override def findWithAddress(phone: String): Option[(PhoneRecord, Address)] = transaction(
    from(phoneRecords, addresses)((r, a) =>
      where(r.phone === phone and a.id === r.addressId)
      select((r, a))
    ).headOption)

  override def findWithAddress2(phone: String): Option[(PhoneRecord, Option[Address])] = transaction {
    join(phoneRecords, addresses.leftOuter)((r, a) =>
      where(r.phone === phone)
        select(r, a)
        on (Some(r.addressId) === a.map(_.id))
    ).headOption
  }

  override def list(): List[PhoneRecord] = transaction(from(phoneRecords)(r => select(r)).toList)

  override def listWithAddressCount(): List[(String, Long)] = transaction(
    join(phoneRecords, addresses)((r, a) =>
      groupBy(r.id)
        compute(r.phone, count(a.id))
        on(r.addressId === a.id)
    ).map(v => (v.measures._1, v.measures._2)).toList)

  override def insert(phoneRecord: PhoneRecord): Unit =
    transaction(phoneRecords.insert(phoneRecord))

  override def insert(address: Address): Unit =
    transaction(addresses.insert(address))

  override def update(phoneRecord: PhoneRecord): Unit =
    transaction(phoneRecords.update(phoneRecord))

  override def delete(id: String): Unit =
    transaction(phoneRecords.deleteWhere(_.id === id))
}