package simulations

import com.sun.tools.jdeprscan.CSV
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jsonpath.JsonPath
import scala.language.postfixOps

import scala.concurrent.duration.DurationInt

class post_csv_reader extends Simulation{

  val httpconfig =   http.proxy(Proxy("localhost",8866))
    .baseUrl("https://gorest.co.in")
    .header("content-type",value="application/json")
    .header("Authorization",value ="Bearer 69e382cf7692adf1af028da620addae1e8cb37b2834697ed00bcfeaf48454a56")

  val csvfeeder= csv("./src/test/resources/Data/InputData.csv").circular
  //println(csvfeeder)

  def getUser() = {
    repeat("1"){
      feed(csvfeeder)
       .exec(http("get the userid")
      .get("/public/v2/users/${userID}")
      .check(status.in(200,304)))
      //.check(jsonPath("$.name").in("Kartik Ganaka"))
    }
  }

    def printfeeder()={
      exec{ session =>
        feed(csvfeeder)
        println("Test ---> "+csvfeeder)
        session
      }
    }

  val scn = scenario("csv reader test one")
    .forever(){
    exec(getUser())
  }

  /*setUp(scn.inject(atOnceUsers(2),
    rampUsers(10)
    during(10 seconds))).protocols(httpconfig)

   */

  /*
  setUp(scn.inject(
    nothingFor(5),
     constantUsersPerSec(10) during (20)
      )).protocols(httpconfig)

   */
  setUp(scn.inject(
    nothingFor(5),
    atOnceUsers(10),
    rampUsers(10) during (20 seconds)
  )).protocols(httpconfig).maxDuration(1 minute)

}