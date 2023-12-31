package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

final class PersonTest extends AnyFunSuite with Matchers:
  test("validate"):
    val validPerson = Person.validate("fred", 24)
    validPerson.isRight shouldBe true

    val invalidPerson = Person.validate("fred", 0)
    invalidPerson.isLeft shouldBe true

    val person = validPerson.right.get
    val personJson = writeToString[Person](person)
    person shouldBe readFromString[Person](personJson)

    // Person("fred", 21) - Invalid call! No public Person.apply(...) available!
    // person.copy(name = "barney") - Invalid call! No public person.copy(...) available!
    // Must construct ( and copy ) a Person with Person.validate(...)!