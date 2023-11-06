package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*

sealed trait Event

final case class PersonAdded(person: Either[Invalidations, Person]) extends Event

object Event:
  given JsonValueCodec[PersonAdded] = JsonCodecMaker.make[PersonAdded]