package objektwerks

import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.all.*

case class User(name: String :| MinLength[3], age: Int :| Greater[0])

def createUser(name: String, age: Int): Either[String, User] =
  for
    n <- name.refineEither[MinLength[3]]
    a <- age.refineEither[Greater[0]]
  yield User(n, a)