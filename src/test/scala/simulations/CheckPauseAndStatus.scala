package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.scenario.Simulation

class CheckPauseAndStatus extends Simulation{

  val httpconfig = http.baseUrl("https://reqres.in/")

  val scn = scenario("Cascade on pause")
    .exec(http("first function")
    .get("/api/users?page")
    .check(status.is("200"))
  )
    .pause("5")
    .exec(http("second request for check")
    .get("/api/users/2")
    .check(status.in(201 to 210))
    )
    .pause(1,10)
    .exec(http("thrid request")
    .get("/api/users/23")
    .check(status.not("404"),status.not(200))
    )

  setUp(scn.inject(atOnceUsers(1))).protocols(httpconfig)
}