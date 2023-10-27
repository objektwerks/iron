package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*

import io.github.iltotore.iron.*
import io.github.iltotore.iron.jsoniter.given
import io.github.iltotore.iron.constraint.collection.{FixedLength, MinLength}
import io.github.iltotore.iron.constraint.numeric.{Greater, GreaterEqual, Interval}

sealed trait Entity:
  val id: Long

object Entity:
  given JsonValueCodec[Entity] = JsonCodecMaker.make[Entity]
  given JsonValueCodec[Cleaning] = JsonCodecMaker.make[Cleaning]
  given JsonValueCodec[Measurement] = JsonCodecMaker.make[Measurement]
  given JsonValueCodec[Chemical] = JsonCodecMaker.make[Chemical]

final case class Account private (id: Long,
                                  license: String,
                                  emailAddress: String,
                                  pin: String,
                                  activated: Long,
                                  deactivated: Long) extends Entity

object Account:
  given JsonValueCodec[Account] = JsonCodecMaker.make[Account]

  def validate(id: Long,
               license: String,
               emailAddress: String,
               pin: String,
               activated: Long,
               deactivated: Long): Either[Invalidations, Account] =
    val invalidations = Invalidations()
    val either = for
      id           <- id.refineEither[GreaterEqual[0]].left.map(error => invalidations.add("id", error))
      license      <- license.refineEither[FixedLength[36]].left.map(error => invalidations.add("license", error))
      emailAddress <- emailAddress.refineEither[MinLength[3]].left.map(error => invalidations.add("emailAddress", error))
      pin          <- pin.refineEither[FixedLength[7]].left.map(error => invalidations.add("pin", error))
      activated    <- activated.refineEither[GreaterEqual[0]].left.map(error => invalidations.add("activated", error))
      deactivated  <- deactivated.refineEither[GreaterEqual[0]].left.map(error => invalidations.add("deactivated", error))
    yield Account(id, license, emailAddress, pin, activated, deactivated)
    if invalidations.isEmpty && either.isRight then Right(either.right.get)
    else Left(invalidations)

final case class Pool private (id: Long,
                               accountId: Long,
                               name: String, 
                               built: Int,
                               volume: Int,
                               unit: UnitOfMeasure) extends Entity

object Pool:
  given JsonValueCodec[Pool] = JsonCodecMaker.make[Pool]

  def validate(id: Long,
               accountId: Long,
               name: String,
               built: Int,
               volume: Int,
               unit: UnitOfMeasure): Either[Invalidations, Pool] =
    val invalidations = Invalidations()
    val either = for
      id        <- id.refineEither[GreaterEqual[0]].left.map(error => invalidations.add("id", error))
      accountId <- accountId.refineEither[GreaterEqual[0]].left.map(error => invalidations.add("accountId", error))
      name      <- name.refineEither[MinLength[3]].left.map(error => invalidations.add("name", error))
      built     <- built.refineEither[Greater[0]].left.map(error => invalidations.add("built", error))
      volume    <- volume.refineEither[GreaterEqual[100]].left.map(error => invalidations.add("volume", error))
    yield Pool(id, accountId, name, built, volume, unit)
    if invalidations.isEmpty && either.isRight then Right(either.right.get)
    else Left(invalidations)

final case class Cleaning(id: Long :| GreaterEqual[0],
                          poolId: Long :| Greater[0],
                          brush: Boolean,
                          net: Boolean,
                          skimmerBasket: Boolean,
                          pumpBasket: Boolean,
                          pumpFilter: Boolean,
                          vacuum: Boolean,
                          cleaned: Long :| Greater[0]) extends Entity // Make private!

object Cleaning:
  def validate(): Either[Invalidations, Cleaning] = ??? // Build!

final case class Measurement(id: Long :| GreaterEqual[0],
                             poolId: Long :| Greater[0],
                             totalChlorine: Int :| Interval.Closed[1, 5],
                             freeChlorine: Int :| Interval.Closed[1, 5],
                             combinedChlorine: Double :| Interval.Closed[0.0, 0.5],
                             ph: Double :| Interval.Closed[6.2, 8.4],
                             calciumHardness: Int :| Interval.Closed[250, 500],
                             totalAlkalinity: Int :| Interval.Closed[80, 120],
                             cyanuricAcid: Int :| Interval.Closed[30, 100],
                             totalBromine: Int :| Interval.Closed[2, 10],
                             salt: Int :| Interval.Closed[2700, 3400],
                             temperature: Int :| Interval.Closed[50, 100],
                             measured: Long :| Greater[0]) extends Entity // Make private!

object Measurement:
  def validate(): Either[Invalidations, Measurement] = ??? // Build!

final case class Chemical(id: Long :| GreaterEqual[0],
                          poolId: Long :| Greater[0],
                          typeof: TypeOfChemical,
                          amount: Double :| Greater[0.0],
                          unit: UnitOfMeasure,
                          added: Long :| Greater[0]) extends Entity // Make private!

object Chemical:
  def validate(): Either[Invalidations, Chemical] = ??? // Build!

enum UnitOfMeasure:
  case gl, l, lb, kg, tablet

enum TypeOfChemical(val display: String):
  case LiquidChlorine extends TypeOfChemical("Liquid Chlorine")
  case Trichlor extends TypeOfChemical("Trichlor")
  case Dichlor extends TypeOfChemical("Dichlor")
  case CalciumHypochlorite extends TypeOfChemical("Calcium Hypochlorite")
  case Stabilizer extends TypeOfChemical("Stabilizer")
  case Algaecide extends TypeOfChemical("Algaecide")
  case MuriaticAcid extends TypeOfChemical("Muriatic Acid")
  case Salt extends TypeOfChemical("Salt")
