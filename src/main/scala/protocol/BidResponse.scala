package protocol

/**
  * @author A N M Bazlur Rahman
  * @since 2019-06-03
  */
case class BidResponse(id: String, bidRequestId: String, price: Double, adid: Option[String], banner: Option[Banner])

