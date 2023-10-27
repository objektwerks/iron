package objektwerks

import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.collection.{FixedLength, MinLength}
import io.github.iltotore.iron.constraint.numeric.{Greater, GreaterEqual}

import scala.collection.mutable

object Validator:
  extension(account: Account)
    def validate(): Either[String, Account] =
      for
        id           <- account.id.refineEither[GreaterEqual[0]]
        license      <- account.license.refineEither[FixedLength[36]]
        emailAddress <- account.emailAddress.refineEither[MinLength[3]]
        pin          <- account.pin.refineEither[FixedLength[7]]
        activated    <- account.activated.refineEither[GreaterEqual[0]]
        deactivated  <- account.deactivated.refineEither[GreaterEqual[0]]
      yield Account(id, license, emailAddress, pin, activated, deactivated)

    def validations(): Map[String, String] =
      val map = mutable.Map.empty[String, String]
      for
        _ <- account.id.refineEither[GreaterEqual[0]].left.map(error => map += "id" -> error)
        _ <- account.license.refineEither[FixedLength[36]].left.map(error => map += "license" -> error)
        _ <- account.emailAddress.refineEither[MinLength[3]].left.map(error => map += "emailAddress" -> error)
        _ <- account.pin.refineEither[FixedLength[7]].left.map(error => map += "pin" -> error)
        _ <- account.activated.refineEither[GreaterEqual[0]].left.map(error => map += "activated" -> error)
        _ <- account.deactivated.refineEither[GreaterEqual[0]].left.map(error => map += "deactivated" -> error)
      yield ()
      map.toMap