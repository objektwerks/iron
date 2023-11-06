package objektwerks

package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*

import java.time.Instant
import java.util.UUID

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

final class EventTest extends AnyFunSuite with Matchers:
  test("event"):
    val account = Account.validate(id = 1,
                                   license = UUID.randomUUID.toString,
                                   emailAddress = "emailaddress@email.com",
                                   pin = "1a2b3c!",
                                   activated = Instant.now.getEpochSecond,
                                   deactivated = 0)
    val accountId = account.right.get.id

    val accountAdded = AccountAdded(account)
    val accountAddJson = writeToString[AccountAdded](accountAdded)
    accountAdded shouldBe readFromString[AccountAdded](accountAddJson)

    val pool = Pool.validate(id = 1,
                             accountId = accountId,
                             name = "blue", 
                             built = 2022,
                             volume = 10000,
                             unit = UnitOfMeasure.gl)
    val poolId = pool.right.get.id

    val poolAdded = PoolAdded(pool)
    val poolAddedJson = writeToString[PoolAdded](poolAdded)
    poolAdded shouldBe readFromString[PoolAdded](poolAddedJson)

    val cleaning = Cleaning.validate(id = 1,
                                     poolId = poolId,
                                     brush = true,
                                     net = true,
                                     skimmerBasket = true,
                                     pumpBasket = true,
                                     pumpFilter = true,
                                     vacuum = true,
                                     cleaned = Instant.now.getEpochSecond)

    val validCleaning = cleaning.right.get
    val cleaningJson = writeToString[Cleaning](validCleaning)
    validCleaning shouldBe readFromString[Cleaning](cleaningJson)

    val measurement = Measurement.validate(id = 1,
                                           poolId = poolId,
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
                                           measured = Instant.now.getEpochSecond)

    val validMeasurement = measurement.right.get
    val measurementJson = writeToString[Measurement](validMeasurement)
    validMeasurement shouldBe readFromString[Measurement](measurementJson)

    val chemical = Chemical.validate(id = 1,
                                     poolId = poolId,
                                     typeof = TypeOfChemical.LiquidChlorine,
                                     amount = 2.5,
                                     unit = UnitOfMeasure.gl,
                                     added = Instant.now.getEpochSecond)

    val validChemical = chemical.right.get
    val chemicalJson = writeToString[Chemical](validChemical)
    validChemical shouldBe readFromString[Chemical](chemicalJson)