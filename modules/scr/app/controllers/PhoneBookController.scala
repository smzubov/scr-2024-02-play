package controllers

import com.google.inject.Inject
import models.dao.entities.{Address, PhoneRecord}
import models.dao.repositories.PhoneRecordRepository
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

class PhoneBookController @Inject()(val phoneRecordRepository: PhoneRecordRepository) extends Controller{
  implicit val writes = Json.writes[PhoneRecord]

  def list() = Action{
    val phoneRecord1 = PhoneRecord("1", "1234", "Ivanov Ivan", "1")
    val phoneRecord2 = PhoneRecord("2", "1235", "Petrov Ivan", "2")
    val address1 = Address("1", "230012", "street 1")
    val address2 = Address("2", "230013", "street 2")

    phoneRecordRepository.insert(address1)
    phoneRecordRepository.insert(address2)
    phoneRecordRepository.insert(phoneRecord1)
    phoneRecordRepository.insert(phoneRecord2)

    val result: List[PhoneRecord] = phoneRecordRepository.list()
    Ok(Json.toJson(result))
  }
}
