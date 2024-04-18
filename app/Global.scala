import com.google.inject.{Guice, Module}
import play.api.GlobalSettings

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
}
