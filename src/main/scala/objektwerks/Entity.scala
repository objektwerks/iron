package objektwerks

import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.all.*

sealed trait Entity:
  val id: Long :| Positive