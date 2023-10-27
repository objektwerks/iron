package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*

import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.collection.{FixedLength, MinLength}
import io.github.iltotore.iron.constraint.numeric.Greater

import scala.collection.mutable

final case class Person private(id: String, name: String, age: Int)

object Person:
  given JsonValueCodec[Person] = JsonCodecMaker.make[Person]

  def validate(id: String, name: String, age: Int): Either[Map[String, String], Person] =
    val map = mutable.Map.empty[String, String]
    val either = for
      i <- id.refineEither[FixedLength[1]].left.map(error => map += "name" -> error)
      n <- name.refineEither[MinLength[2]].left.map(error => map += "name" -> error)
      a <- age.refineEither[Greater[0]].left.map(error => map += "age" -> error)
    yield Person(i, n, a)

    if map.isEmpty then Right(either.right.get)
    else Left(map.toMap)