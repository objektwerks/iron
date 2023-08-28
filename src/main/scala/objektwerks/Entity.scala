package objektwerks

import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.numeric.{Greater, GreaterEqual}
import io.github.iltotore.iron.constraint.string.Alphanumeric

sealed trait Entity:
  val id: Long :| GreaterEqual[0]

final case class Pool(id: Long :| GreaterEqual[0],
                      license: String :| Alphanumeric,
                      name: String :| Alphanumeric, 
                      volume: Int :| Greater[100],
                      unit: String :| Alphanumeric) extends Entity