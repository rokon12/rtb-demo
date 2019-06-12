package protocol

import akka.http.scaladsl.server.{Directive1, Directives}

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * @author A N M Bazlur Rahman
  * @since 2019-06-03
  */
trait BidRequestDirectives extends Directives {
  def handle[T]
  (f: Future[T])
  (e: Throwable => ApiError): Directive1[T] = onComplete(f) flatMap {
    case Success(t) =>
      provide(t)
    case Failure(error) =>
      val apiError = e(error)
      complete(apiError.statusCode, apiError.message)
  }

  def handleWithGeneric[T](f: Future[T]): Directive1[T] =
    handle[T](f)(_ => ApiError.generic)
}
