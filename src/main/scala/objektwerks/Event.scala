package objektwerks

sealed trait Event

final case class PersonAdded(person: Either[Invalidations, Person]) extends Event