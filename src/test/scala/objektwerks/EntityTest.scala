package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*

import io.github.iltotore.iron.*

import java.time.Instant
import java.util.UUID

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import Entity.given

final class EntityTest extends AnyFunSuite with Matchers:
  test("refine"):
    val account = Account(id = 1.refine,
                          license = UUID.randomUUID.toString.refine,
                          emailAddress = "emailaddress@email.com".refine,
                          pin = "1a2b3c!".refine,
                          activated = Instant.now.getEpochSecond.refine,
                          deactivated = 0.refine)

    val accountJson = writeToString[Account](account)
    account shouldBe readFromString[Account](accountJson)

    val pool = Pool(id = 1.refine,
                    accountId = account.id.refine,
                    name = "blue".refine, 
                    built = 2022.refine,
                    volume = 10000.refine,
                    unit = UnitOfMeasure.gl)

    val poolJson = writeToString[Pool](pool)
    pool shouldBe readFromString[Pool](poolJson)

    val cleaning = Cleaning(id = 1.refine,
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

    val measurement = Measurement(id = 1.refine,
                                  poolId = pool.id.refine,
                                  totalChlorine = 3.refine,
                                  freeChlorine = 3.refine,
                                  combinedChlorine = 0.3.refine,
                                  ph = 7.4.refine,
                                  calciumHardness = 300.refine,
                                  totalAlkalinity = 100.refine,
                                  cyanuricAcid = 65.refine,
                                  totalBromine = 6.refine,
                                  salt = 3100.refine,
                                  temperature = 80.refine,
                                  measured = Instant.now.getEpochSecond.refine)

    val measurementJson = writeToString[Measurement](measurement)
    measurement shouldBe readFromString[Measurement](measurementJson)

    val chemical = Chemical(id = 1.refine,
                            poolId = pool.id.refine,
                            typeof = TypeOfChemical.LiquidChlorine,
                            amount = 2.5.refine,
                            unit = UnitOfMeasure.gl,
                            added = Instant.now.getEpochSecond.refine)

    val chemicalJson = writeToString[Chemical](chemical)
    chemical shouldBe readFromString[Chemical](chemicalJson)
