package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*

import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.collection.MinLength
import io.github.iltotore.iron.constraint.numeric.Greater

final case class Person private(name: String, age: Int)

object Person:
  given JsonValueCodec[Person] = JsonCodecMaker.make[Person]

  def validate(name: String, age: Int): Either[Invalidations, Person] =
    val invalidations = Invalidations()
    val either = for
      n <- name.refineEither[MinLength[2]].left.map(error => invalidations.add("name", error))
      a <- age.refineEither[Greater[0]].left.map(error => invalidations.add("age", error))
    yield Person(n, a)
    if invalidations.isEmpty && either.isRight then Right(either.right.get)
    else Left(invalidations)