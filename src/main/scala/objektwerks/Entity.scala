package objektwerks

import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.collection.{Length, MinLength}
import io.github.iltotore.iron.constraint.numeric.{Greater, GreaterEqual}

sealed trait Entity:
  val id: Long :| GreaterEqual[0]

final case class Pool(id: Long :| GreaterEqual[0],
                      license: String :| Length[36],
                      name: String :| MinLength[3], 
                      volume: Int :| Greater[100],
                      unit: String :| MinLength[1]) extends Entity