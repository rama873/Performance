package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.scenario.Simulation

class firstTestSimulation extends Simulation{

  //http conf - Base url and header
     val httpconfig =   http.baseUrl("https://reqres.in/")
           .header("content-type",value="application/json")

  //Scenario - body and method
   val scn =  scenario("Testing API Get").
          exec(http("First Get request")
          .get("/api/users?page")
          .check(status is 200))


  //set-up
         setUp(scn.inject(atOnceUsers(100))).protocols(httpconfig)
}