package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.scenario.Simulation

class PostRequest extends Simulation{
  //http conf - Base url and header
  val httpconfig =   http.baseUrl("https://reqres.in")
    .header("content-type",value="application/json")

  //Scenario - body and method
  val scn =  scenario("Testing API Post Request").
    exec(http("post request to create user")
      .post("/api/users")
      .body(RawFileBody("./src/test/resources/Payloads/Create.json")).asJson
      .check(status is 201))
    .pause("3")
      .exec(http("get the user create")
      .get("/api/users/2")
      .check(status is 200))


  //set-up
  setUp(scn.inject(atOnceUsers(1))).protocols(httpconfig)
}