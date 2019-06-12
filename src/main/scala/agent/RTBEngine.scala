package agent

import akka.actor.{Actor, ActorSystem, Props}
import akka.util.Timeout
import protocol.{Banner, BidRequest, BidResponse, Campaign, CampaignRepository, Impression}

import scala.concurrent.duration._
import akka.pattern.ask

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
  * @author A N M Bazlur Rahman
  * @since 2019-06-04
  */
object RTBEngine {
  private implicit val system: ActorSystem = ActorSystem("my-system")
  private implicit val ec: ExecutionContextExecutor = system.dispatcher
  implicit val timeout = Timeout(25 seconds)

  def processBidRequest(bidRequest: BidRequest): Future[Option[BidResponse]] = {
    val system = ActorSystem("bidding-system")
    val actor = system.actorOf(Props(new Bidder()))

    val future: Future[Any] = actor ? bidRequest
    future.asInstanceOf[Future[Option[BidResponse]]]
  }
}

class Bidder extends Actor {
  val rendom = scala.util.Random

  def findBanner(campaign: Campaign, imp: Impression): Option[Banner] = {
    val wmin = imp.wmin.getOrElse(0)
    val wmax = imp.wmax.getOrElse(1000) //let's assume wmax can1000 units
    val hmin = imp.hmin.getOrElse(0)
    val hmax = imp.hmax.getOrElse(1000) // let's assume hmax can be 1000 units

    campaign.banners.find(banner => {
      var flag = true
      if (imp.w.isDefined && imp.w.get != banner.width) flag = false
      else if (imp.h.isDefined && imp.h.get != banner.height) flag = false
      else if (banner.width > wmax) flag = false
      else if (banner.width < wmin) flag = false
      else if (banner.height > hmax) flag = false
      else if (banner.height < hmin) flag = false
      flag
    })
  }

  override def receive: Receive = {
    case bidRequest: BidRequest =>
      val response: Option[Some[BidResponse]] = CampaignRepository.findAll()
        .filter(campaign => campaign.targeting.targetedSiteIds.contains(bidRequest.site.id))
        .map(campaign => {
          if (bidRequest.imp.isDefined) {
            val banner: Option[Banner] = bidRequest.imp.get.map(imp => {
              findBanner(campaign, imp)
            }).head
            Some(BidResponse(rendom.nextInt(1000).toString, bidRequest.id, campaign.bid, None, banner))
          } else {
            Some(BidResponse(rendom.nextInt(1000).toString, bidRequest.id, campaign.bid, None, None))
          }
        }).headOption

      if (response.isDefined) {
        sender ! response.get
      } else {
        sender ! None
      }

    case _ => sender ! None
  }
}

