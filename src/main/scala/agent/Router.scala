package agent

import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.server.{Directives, Route}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import org.slf4j.LoggerFactory
import protocol.{BidRequest, BidRequestDirectives}

/**
  * @author A N M Bazlur Rahman
  * @since 2019-06-03
  */
trait Router {
  def route: Route
}

class RBTRouter extends Router with Directives with BidRequestDirectives {
  val logger = LoggerFactory.getLogger(getClass)

  override def route: Route = pathPrefix("") {
    pathEndOrSingleSlash {
      get {
        complete("Ok")
      } ~ post {
        entity(as[BidRequest]) { bidRequest =>
          logger.info("bid request received {}", bidRequest)

          handleWithGeneric(RTBEngine.processBidRequest(bidRequest)) { bidResponses =>
            if (bidResponses.isEmpty) {
              complete(StatusCode.int2StatusCode(204))
            } else {
              complete(bidResponses)
            }
          }
        }
      }
    }
  }
}
