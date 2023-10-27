package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*

import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.collection.{FixedLength, MinLength}
import io.github.iltotore.iron.constraint.numeric.Greater

final case class Person private(id: String, name: String, age: Int)

object Person:
  given JsonValueCodec[Person] = JsonCodecMaker.make[Person]

  def validate(id: String, name: String, age: Int): Validated[Person] =
    val validations = Validations()
    val either = for
      i <- id.refineEither[FixedLength[1]].left.map(error => validations.add("id", error))
      n <- name.refineEither[MinLength[2]].left.map(error => validations.add("name", error))
      a <- age.refineEither[Greater[0]].left.map(error => validations.add("age", error))
    yield Person(i, n, a)
    Validated(validations, either.right.get)