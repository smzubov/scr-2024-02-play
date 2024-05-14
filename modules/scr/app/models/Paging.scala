package models

import play.api.mvc.QueryStringBindable

case class Paging(page: Int, size: Int)

object Paging{
  implicit def queryBinder(implicit binder: QueryStringBindable[Int]): QueryStringBindable[Option[Paging]] =
    new QueryStringBindable[Option[Paging]] {
      override def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, Option[Paging]]] =
        for{
          pNE <- binder.bind(key + ".page", params)
          pSE <- binder.bind(key + ".size", params)
        } yield (pNE, pSE) match {
          case (Right(pN), Right(pS)) => Right(Some(Paging(pN, pS)))
          case _ => Left("Unable to bind Paging")
        }

      override def unbind(key: String, value: Option[Paging]): String =
        binder.unbind(key + ".page", value.map(_.page).getOrElse(1)) + "&" +
          binder.unbind(key + ".size", value.map(_.size).getOrElse(10))
    }
}
