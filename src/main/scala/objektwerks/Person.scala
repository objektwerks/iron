package objektwerks

import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.collection.MinLength
import io.github.iltotore.iron.constraint.numeric.Greater

import scala.collection.mutable

sealed abstract case class Person private(name: String, age: Int)

object Person:
  def validate(name: String, age: Int): Either[String, Person] =
    for
      n <- name.refineEither[MinLength[2]]
      a <- age.refineEither[Greater[0]]
    yield new Person(n, a) {}

  def validations(name: String, age: Int): Map[String, String] =
    val map = mutable.Map.empty[String, String]
    for
      _ <- name.refineEither[MinLength[2]].left.map(error => map += "name" -> error)
      _ <- age.refineEither[Greater[0]].left.map(error => map += "age" -> error)
    yield ()
    map.toMap