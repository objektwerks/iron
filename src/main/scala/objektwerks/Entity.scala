package objektwerks

import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.numeric.Greater

sealed trait Entity:
  val id: Long :| Greater[-1]