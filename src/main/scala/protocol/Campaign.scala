package protocol

/**
  * @author A N M Bazlur Rahman
  * @since 2019-06-03
  */
case class Campaign(id: Int, userId: Int, country: String, runningTimes: Set[TimeRange], targeting: Targeting, banners: List[Banner], bid: Double)

case class TimeRange(timeStart: Long, timeEnd: Long)

case class Targeting(cities: List[String], targetedSiteIds: Seq[Int])

// targeted sites could be potentially a very long list, so choose a data type that suits well in such case
case class Banner(id: Int, src: String, width: Int, height: Int)

object CampaignRepository {
  def findAll(): Seq[Campaign] = {
    List(
      Campaign(
        123,
        213,
        "Bangladesh",
        Set(TimeRange(1600, 1700), TimeRange(2030, 2230)),
        Targeting(List("Dhaka", "Chitagong"), Seq(122429, 104797)),
        List(Banner(321, "Random X", 10, 6), Banner(32, "Random Bangladeshi Banner", 15, 14)),
        10.34
      ),
      Campaign(
        124,
        214,
        "Random Country",
        Set(TimeRange(1300, 1415), TimeRange(1840, 2000)),
        Targeting(List("City A", "City B"), Seq(31, 104798)),
        List(Banner(321, "Random Y", 10, 6), Banner(32, "Random Banner", 15, 14)),
        1.6
      ),
      Campaign(
        125,
        215,
        "Some More Random Country",
        Set(TimeRange(1300, 1415), TimeRange(1840, 2000)),
        Targeting(List("City A", "City B"), Seq(122430, 104798)),
        List(Banner(321, "Random Z", 10, 6), Banner(32, "Some more Random Banner", 15, 14)),
        1.6
      )
    )
  }
}