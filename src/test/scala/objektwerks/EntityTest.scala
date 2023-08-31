package objektwerks

import io.github.iltotore.iron.*

import java.time.Instant
import java.util.UUID

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class EntityTest extends AnyFunSuite with Matchers:
  test("entity") {
    val account = Account(id = 1,
                          license = UUID.randomUUID.toString.refine,
                          emailAddress = "emailaddress@email.com",
                          pin = "1a2b3c!",
                          activated = Instant.now.getEpochSecond.refine,
                          deactivated = 0)

    val pool = Pool(id = 1,
                    accountId = account.id,
                    name = "blue", 
                    built = 2022,
                    volume = 10000,
                    unit = UnitOfMeasure.gl)

    val cleaning = Cleaning(id = 1,
                            poolId = pool.id,
                            brush = true,
                            net = true,
                            skimmerBasket = true,
                            pumpBasket = true,
                            pumpFilter = true,
                            vacuum = true,
                            cleaned = Instant.now.getEpochSecond.refine)

    
  }