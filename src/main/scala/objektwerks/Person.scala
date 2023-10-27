package objektwerks

import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.collection.MinLength
import io.github.iltotore.iron.constraint.numeric.Greater

sealed abstract case class Person private(name: String, age: Int)

object Person:
  def validate(name: String, age: Int): Either[String, Person] =
    for
      n <- name.refineEither[MinLength[2]]
      a <- age.refineEither[Greater[0]]
    yield new Person(n, a) {}