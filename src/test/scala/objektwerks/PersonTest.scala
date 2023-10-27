package objektwerks

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

final class PersonTest extends AnyFunSuite with Matchers:
  test("person"):
    val validPerson = Person.validate("1", "fred", 24)
    validPerson.isRight shouldBe true

    val invalidPerson = Person.validate("12", "fred", 24)
    invalidPerson.isLeft shouldBe true