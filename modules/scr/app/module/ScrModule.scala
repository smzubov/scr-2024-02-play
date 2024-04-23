package module

import di.AppModule
import models.dao.repositories.{PhoneRecordRepository, PhoneRecordRepositoryImpl}
import models.services.{LogService, LogServiceImpl}

class ScrModule extends AppModule{
  override def configure(): Unit = {
    bindSingleton[LogService, LogServiceImpl]
    bindSingleton[PhoneRecordRepository, PhoneRecordRepositoryImpl]
  }
}
