package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*

sealed trait Event

final case class PersonAdded(person: Either[Invalidations, Person]) extends Event

final case class AccountAdded(account: Either[Invalidations, Account]) extends Event
final case class PoolAdded(pool: Either[Invalidations, Pool]) extends Event
final case class CleaningAdded(cleaning: Either[Invalidations, Cleaning]) extends Event
final case class MeasurementAdded(measurement: Either[Invalidations, Measurement]) extends Event
final case class ChemicalAdded(chemical: Either[Invalidations, Chemical]) extends Event

object Event:
  given JsonValueCodec[Event] = JsonCodecMaker.make[Event]

  given JsonValueCodec[PersonAdded] = JsonCodecMaker.make[PersonAdded]

  given JsonValueCodec[AccountAdded] = JsonCodecMaker.make[AccountAdded]
  given JsonValueCodec[PoolAdded] = JsonCodecMaker.make[PoolAdded]
  given JsonValueCodec[CleaningAdded] = JsonCodecMaker.make[CleaningAdded]
  given JsonValueCodec[MeasurementAdded] = JsonCodecMaker.make[MeasurementAdded]
  given JsonValueCodec[ChemicalAdded] = JsonCodecMaker.make[ChemicalAdded]