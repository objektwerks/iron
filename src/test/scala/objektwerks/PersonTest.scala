package objektwerks

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

final class PersonTest extends AnyFunSuite with Matchers:
  test("person"):
    val validatedPerson = Person.validate("1", "fred", 24)
    validatedPerson.isRight shouldBe true