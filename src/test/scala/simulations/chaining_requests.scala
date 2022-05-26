package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jsonpath.JsonPath

class chaining_requests extends Simulation{

  val httpconfig =   http.baseUrl("https://gorest.co.in")
    .header("content-type",value="application/json")
    .header("Authorization",value ="Bearer 69e382cf7692adf1af028da620addae1e8cb37b2834697ed00bcfeaf48454a56")


  val scn = scenario("fetch all user and get one")
            .exec(http("create user")
              .post("/public/v2/users")
              .body(RawFileBody("./src/test/resources/Payloads/Create.json"))
            .check(jsonPath("$.id").saveAs("ID"))
            )
    .pause("3")
    .exec(http("fetch created user")
    .get("/public/v2/users/${ID}")
    .check(jsonPath("$.name").is("Rama give Marar")))
      .exec{ session =>
        println(jsonPath("$.name").toString()+"=========123======================="+jsonPath("$.email").toString())
        session
      }

  setUp(scn.inject(atOnceUsers(1))).protocols(httpconfig)
}
