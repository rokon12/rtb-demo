import agent.RBTRouter
import akka.http.scaladsl.model.{HttpEntity, HttpMethods, HttpRequest, MediaTypes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}

/**
  * @author A N M Bazlur Rahman
  * @since 2019-06-12
  */
class BidRequestSpec extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest{
  val route = new RBTRouter route

  val jsonRequest = ByteString(
    s"""
       |{
       |  "user": {
       |    "id": "R798U54QA3V0l3F9pmWo695f9e7",
       |    "domain": "Hello World"
       |  },
       |  "imp": [
       |    {
       |      "bidfloor": 0.13,
       |      "id": "1",
       |       "w": 10,
       |       "h": 6
       |    }
       |  ],
       |  "device": {
       |    "id": "1adfasdf",
       |    "geo": {
       |      "lon": 121.05069732666016,
       |      "lat": 0.0,
       |      "country": "Bangladesh",
       |      "city": "Dhaka"
       |    }
       |  },
       |  "site": {
       |      "id": 122429,
       |      "domain":  "Hello world"
       |  },
       |  "id": "123445"
       |}
    """.stripMargin)

  val postRequest = HttpRequest(
    HttpMethods.POST, uri = "/",
    entity = HttpEntity(MediaTypes.`application/json`, jsonRequest)
  )

  "The bid request " should {
    " accept and return bid response " in {
      //Test
      postRequest ~> route ~> check {
        status.isSuccess() shouldEqual true
      }
    }
  }
}
