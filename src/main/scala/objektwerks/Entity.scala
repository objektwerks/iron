package objektwerks

import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.numeric.GreaterEqual

sealed trait Entity:
  val id: Long :| GreaterEqual[0]