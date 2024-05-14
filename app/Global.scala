
import com.google.inject.{Guice, Module}
import db.{Liqui, SquerylConfig}
import org.squeryl.{Session, SessionFactory}
import org.squeryl.internals.DatabaseAdapter
import play.api.{Application, GlobalSettings, Logger}

object Global extends GlobalSettings{

  val appModules = List("module.ScrModule")

  lazy val injector = {
    val moduleClasses = appModules.map{cn =>
      play.api.Play.current.classloader.loadClass(cn)
    }
    val moduleInstances = moduleClasses.map{mc =>
      mc.newInstance().asInstanceOf[Module]
    }
    Guice.createInjector(moduleInstances :_*)
  }
  override def getControllerInstance[A](controllerClass: Class[A]): A =
    injector.getInstance(controllerClass)

  override def onStart(app: Application): Unit = {
    initializeSqueryl()
    performMigration()

  }

  private def initializeSqueryl() {
    SessionFactory.concreteFactory = Some(() =>
    {
      val s = getSession(SquerylConfig.dbDefaultAdapter)
      if(SquerylConfig.logSql)
        s.setLogger( s => Logger.warn(s))
      s
    })
  }
  private def getSession(adapter: DatabaseAdapter) =
    Session.create(db.hikariDataSource.getConnection, adapter)

  private def performMigration() = {
    val liqui = Liqui.mkLiquibase
    liqui.update("dev")
  }
}
