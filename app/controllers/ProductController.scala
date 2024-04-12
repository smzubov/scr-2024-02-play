package controllers

import models.Product
import play.api.mvc.{Action, Controller}

object ProductController extends Controller{

  def list = Action{
    Ok(views.html.products.list(List(
      Product("product1", 20),
      Product("product2", 30),
      Product("product3", 40),
      Product("product4", 50),
      Product("product4", 60)
    )))
  }
}
