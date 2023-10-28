package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*

import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.collection.{FixedLength, MinLength}
import io.github.iltotore.iron.constraint.numeric.{Greater, GreaterEqual, Interval}

sealed trait Entity:
  val id: Long

object Entity:
  given JsonValueCodec[Entity] = JsonCodecMaker.make[Entity]

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
      accountId <- accountId.refineEither[Greater[0]].left.map(error => invalidations.add("accountId", error))
      name      <- name.refineEither[MinLength[3]].left.map(error => invalidations.add("name", error))
      built     <- built.refineEither[Greater[0]].left.map(error => invalidations.add("built", error))
      volume    <- volume.refineEither[GreaterEqual[100]].left.map(error => invalidations.add("volume", error))
    yield Pool(id, accountId, name, built, volume, unit)
    if invalidations.isEmpty && either.isRight then Right(either.right.get)
    else Left(invalidations)

final case class Cleaning private (id: Long,
                                   poolId: Long,
                                   brush: Boolean,
                                   net: Boolean,
                                   skimmerBasket: Boolean,
                                   pumpBasket: Boolean,
                                   pumpFilter: Boolean,
                                   vacuum: Boolean,
                                   cleaned: Long) extends Entity

object Cleaning:
  given JsonValueCodec[Cleaning] = JsonCodecMaker.make[Cleaning]

  def validate(id: Long,
               poolId: Long,
               brush: Boolean,
               net: Boolean,
               skimmerBasket: Boolean,
               pumpBasket: Boolean,
               pumpFilter: Boolean,
               vacuum: Boolean,
               cleaned: Long): Either[Invalidations, Cleaning] =
    val invalidations = Invalidations()
    val either = for
      id      <- id.refineEither[GreaterEqual[0]].left.map(error => invalidations.add("id", error))
      poolId  <- poolId.refineEither[Greater[0]].left.map(error => invalidations.add("poolId", error))
      cleaned <- poolId.refineEither[Greater[0]].left.map(error => invalidations.add("cleaned", error))
    yield Cleaning(id, poolId, brush, net, skimmerBasket, pumpBasket, pumpFilter, vacuum, cleaned)
    if invalidations.isEmpty && either.isRight then Right(either.right.get)
    else Left(invalidations)

final case class Measurement private (id: Long,
                                      poolId: Long,
                                      totalChlorine: Int,
                                      freeChlorine: Int,
                                      combinedChlorine: Double,
                                      ph: Double,
                                      calciumHardness: Int,
                                      totalAlkalinity: Int,
                                      cyanuricAcid: Int,
                                      totalBromine: Int,
                                      salt: Int,
                                      temperature: Int,
                                      measured: Long) extends Entity

object Measurement:
  given JsonValueCodec[Measurement] = JsonCodecMaker.make[Measurement]

  def validate(id: Long,
               poolId: Long,
               totalChlorine: Int,
               freeChlorine: Int,
               combinedChlorine: Double,
               ph: Double,
               calciumHardness: Int,
               totalAlkalinity: Int,
               cyanuricAcid: Int,
               totalBromine: Int,
               salt: Int,
               temperature: Int,
               measured: Long): Either[Invalidations, Measurement] =
    val invalidations = Invalidations()
    val either = for
      id                <- id.refineEither[GreaterEqual[0]].left.map(error => invalidations.add("id", error))
      poolId            <- poolId.refineEither[Greater[0]].left.map(error => invalidations.add("poolId", error))
      totalChlorine     <- totalChlorine.refineEither[Interval.Closed[1, 5]].left.map(error => invalidations.add("totalChlorine", error))
      freeChlorine      <- freeChlorine.refineEither[Interval.Closed[1, 5]].left.map(error => invalidations.add("freeChlorine", error))
      combinedChlorine  <- combinedChlorine.refineEither[Interval.Closed[0.0, 0.5]].left.map(error => invalidations.add("combinedChlorine", error))
      ph                <- ph.refineEither[Interval.Closed[6.2, 8.4]].left.map(error => invalidations.add("ph", error))
      calciumHardness   <- calciumHardness.refineEither[Interval.Closed[250, 500]].left.map(error => invalidations.add("calciumHardness", error))
      totalAlkalinity   <- totalAlkalinity.refineEither[Interval.Closed[80, 120]].left.map(error => invalidations.add("totalAlkalinity", error))
      cyanuricAcid      <- cyanuricAcid.refineEither[Interval.Closed[30, 100]].left.map(error => invalidations.add("cyanuricAcid", error))
      totalBromine      <- totalBromine.refineEither[Interval.Closed[2, 10]].left.map(error => invalidations.add("totalBromine", error))
      salt              <- salt.refineEither[Interval.Closed[2700, 3400]].left.map(error => invalidations.add("salt", error))
      temperature       <- temperature.refineEither[Interval.Closed[50, 100]].left.map(error => invalidations.add("temperature", error))

      measured <- poolId.refineEither[Greater[0]].left.map(error => invalidations.add("measured", error))
    yield Measurement(id, poolId, totalChlorine, freeChlorine, combinedChlorine, ph, calciumHardness, totalAlkalinity, cyanuricAcid, totalBromine, salt, temperature, measured)
    if invalidations.isEmpty && either.isRight then Right(either.right.get)
    else Left(invalidations)

final case class Chemical(id: Long,
                          poolId: Long,
                          typeof: TypeOfChemical,
                          amount: Double,
                          unit: UnitOfMeasure,
                          added: Long) extends Entity

object Chemical:
  given JsonValueCodec[Chemical] = JsonCodecMaker.make[Chemical]

  def validate(id: Long,
               poolId: Long,
               typeof: TypeOfChemical,
               amount: Double,
               unit: UnitOfMeasure,
               added: Long): Either[Invalidations, Chemical] =
    val invalidations = Invalidations()
    val either = for
      id     <- id.refineEither[GreaterEqual[0]].left.map(error => invalidations.add("id", error))
      poolId <- poolId.refineEither[Greater[0]].left.map(error => invalidations.add("poolId", error))
      amount <- amount.refineEither[Greater[0.0]].left.map(error => invalidations.add("amount", error))
      added  <- added.refineEither[Greater[0]].left.map(error => invalidations.add("added", error))
    yield Chemical(id, poolId, typeof, amount, unit, added)
    if invalidations.isEmpty && either.isRight then Right(either.right.get)
    else Left(invalidations)

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