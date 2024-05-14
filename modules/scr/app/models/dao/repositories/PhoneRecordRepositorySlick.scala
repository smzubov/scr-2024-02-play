package models.dao.repositories

import db.SlickDatabase
import models.dao.entities.{Address, PhoneRecord}
import models.dao.schema.{AddressSlickSchema, PhoneRecordSlickSchema}
import slick.dbio.Effect
import slick.lifted.TableQuery

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._
import slick.sql.{FixedSqlStreamingAction, SqlAction}

trait PhoneRecordRepositorySlick {
  def find(phone: String): Future[Option[PhoneRecord]]
  def findWithAddress(phone: String): Future[Option[(PhoneRecord, Address)]]
}

class PhoneRecordRepositorySlickImpl extends PhoneRecordRepositorySlick with SlickDatabase{
  val phoneRecords = TableQuery[PhoneRecordSlickSchema]
  val addresses = TableQuery[AddressSlickSchema]

  override def find(phone: String): Future[Option[PhoneRecord]] = {
    val q: SqlAction[Option[PhoneRecord], NoStream, Effect.Read] = phoneRecords.filter(_.phone === phone)
      .result.headOption
    db.run(q)
  }

  override def findWithAddress(phone: String): Future[Option[(PhoneRecord, Address)]] = {
    val q = for{
      r <- phoneRecords if r.phone === phone
      a <- addresses if a.id === r.addressId
    } yield (r, a)

    val q2 = q.sortBy(_._1.phone.asc.nullsLast)
    val q3 = q2.take(1)
    db.run(q3.result.headOption)
  }
}