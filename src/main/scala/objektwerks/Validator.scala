package objektwerks

import io.github.iltotore.iron.*
//import io.github.iltotore.iron.constraint.collection.{FixedLength, MinLength}
import io.github.iltotore.iron.constraint.numeric.{Greater, GreaterEqual}

import scala.collection.mutable

object Validator:
  extension(account: Account)
    def validate(): Map[String, String] =
      val map = mutable.Map.empty[String, String]
      account.id.refineEither[GreaterEqual[0]].left.map(error => map += "id" -> error)
      //account.license.refineEither[FixedLength[36]].left.map(error => map += "license" -> error)
      //account.emailAddress.refineEither[MinLength[3]].left.map(error => map += "emailAddress" -> error)
      //account.pin.refineEither[FixedLength[7]].left.map(error => map += "pin" -> error)
      account.activated.refineEither[GreaterEqual[0]].left.map(error => map += "activated" -> error)
      account.deactivated.refineEither[GreaterEqual[0]].left.map(error => map += "deactivated" -> error)
      map.toMap