package controllers

import controllers.Assets.Forbidden
import models.RequestContext
import models.services.{AuthorizationService, AuthorizationServiceImpl}
import play.api.mvc.{ActionBuilder, ActionFilter, ActionTransformer, Controller, Request}

import scala.concurrent.Future

object RCAction extends ActionBuilder[RequestContext]
  with ActionTransformer[Request, RequestContext] {
  override protected def transform[A](request: Request[A]): Future[RequestContext[A]] =
    Future.successful(RequestContext[A](request, request.session.get("userId")))
}

object PermissionCheckAction extends ActionFilter[RequestContext]{
  def authorizationService: AuthorizationService = new AuthorizationServiceImpl

  def filter[A](in: RequestContext[A]) = Future.successful{
    if(!authorizationService.check(in.userId)) Some(Forbidden)
    else None
  }
}

trait Authorization extends Controller{
  def authorize: ActionBuilder[RequestContext] = RCAction andThen PermissionCheckAction
}
