package models.dao.schema

import models.dao.entities.{Address, PhoneRecord}
import org.squeryl.Schema


object PhoneBookSchema extends Schema {

  val phoneRecords = table[PhoneRecord]


  val addresses = table[Address]


}
