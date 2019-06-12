package protocol

/**
  * @author A N M Bazlur Rahman
  * @since 2019-06-03
  */

// BidRequest

case class BidRequest(id: String, imp: Option[List[Impression]], site: Site, user: Option[User], device: Option[Device])

case class Impression(id: String, wmin: Option[Int], wmax: Option[Int], w: Option[Int], hmin: Option[Int], hmax: Option[Int], h: Option[Int], bidFloor: Option[Double])

// at least one of w, wmin, wmax will be specified for width and at least one of h, hmin, hmax will be specified for height. bidFloor is the minimum amount that would be accepted as a valid bid.

case class Site(id: Int, domain: String)

case class User(id: String, geo: Option[Geo])

case class Device(id: String, geo: Option[Geo])

case class Geo(country: Option[String], city: Option[String], lat: Option[Double], lon: Option[Double])