package objektwerks

import io.github.iltotore.iron.*

import java.time.Instant
import java.util.UUID

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class EntityTest extends AnyFunSuite with Matchers:
  test("entity") {
    val account = Account(id = 1,
                         license = UUID.fromString.toString.refine,
                         emailAddress = "emailaddress@email.com",
                         pin = "1a2b3c4",
                         activated = Instant.now.getEpochSecond.refine,
                         deactivated = 0)

    account.id shouldBe 1
  }