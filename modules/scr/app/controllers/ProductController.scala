package controllers

import com.google.inject.Inject
import models.Product
import models.services.LogService
import play.api.mvc.{Action, Controller}

class ProductController @Inject()(val logService: LogService) extends Authorization {

  def list = authorize{ rc =>
    logService.log("Hello from ProductController")
    Ok(views.html.products.list(List(
      Product("product1", 20),
      Product("product2", 30),
      Product("product3", 40),
      Product("product4", 50),
      Product("product4", 60)
    )))
  }
}
