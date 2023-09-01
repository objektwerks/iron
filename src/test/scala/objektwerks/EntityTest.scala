package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*

import io.github.iltotore.iron.*

import java.time.Instant
import java.util.UUID

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import Entity.given

class EntityTest extends AnyFunSuite with Matchers:
  test("refine") {
    val account = Account(id = 1,
                          license = UUID.randomUUID.toString.refine,
                          emailAddress = "emailaddress@email.com",
                          pin = "1a2b3c!",
                          activated = Instant.now.getEpochSecond.refine,
                          deactivated = 0)

    val accountJson = writeToString[Account](account)
    account shouldBe readFromString[Account](accountJson)

    val pool = Pool(id = 1,
                    accountId = account.id.refine,
                    name = "blue", 
                    built = 2022,
                    volume = 10000,
                    unit = UnitOfMeasure.gl)

    val poolJson = writeToString[Pool](pool)
    pool shouldBe readFromString[Pool](poolJson)

    val cleaning = Cleaning(id = 1,
                            poolId = pool.id.refine,
                            brush = true,
                            net = true,
                            skimmerBasket = true,
                            pumpBasket = true,
                            pumpFilter = true,
                            vacuum = true,
                            cleaned = Instant.now.getEpochSecond.refine)

    val cleaningJson = writeToString[Cleaning](cleaning)
    cleaning shouldBe readFromString[Cleaning](cleaningJson)

    val measurement = Measurement(id = 1,
                                  poolId = pool.id.refine,
                                  totalChlorine = 3,
                                  freeChlorine = 3,
                                  combinedChlorine = 0.3,
                                  ph = 7.4,
                                  calciumHardness = 300,
                                  totalAlkalinity = 100,
                                  cyanuricAcid = 65,
                                  totalBromine = 6,
                                  salt = 3100,
                                  temperature = 80,
                                  measured = Instant.now.getEpochSecond.refine)

    val measurementJson = writeToString[Measurement](measurement)
    measurement shouldBe readFromString[Measurement](measurementJson)

    val chemical = Chemical(id = 1,
                            poolId = pool.id.refine,
                            typeof = TypeOfChemical.LiquidChlorine,
                            amount = 2.5,
                            unit = UnitOfMeasure.gl,
                            added = Instant.now.getEpochSecond.refine)

    val chemicalJson = writeToString[Chemical](chemical)
    chemical shouldBe readFromString[Chemical](chemicalJson)
  }