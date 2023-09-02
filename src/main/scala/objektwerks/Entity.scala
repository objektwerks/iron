package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*

import io.github.iltotore.iron.*
import io.github.iltotore.iron.jsoniter.given
import io.github.iltotore.iron.constraint.collection.{FixedLength, MinLength}
import io.github.iltotore.iron.constraint.numeric.{Greater, GreaterEqual, Interval}
import io.github.iltotore.iron.constraint.string.ValidUUID

final case class Valid(map: Map[String, String]):
  def isValid: Boolean = map.isEmpty

sealed trait Entity:
  val id: Long

object Entity:
  given JsonValueCodec[Entity] = JsonCodecMaker.make[Entity]
  given JsonValueCodec[Account] = JsonCodecMaker.make[Account]
  given JsonValueCodec[Pool] = JsonCodecMaker.make[Pool]
  given JsonValueCodec[Cleaning] = JsonCodecMaker.make[Cleaning]
  given JsonValueCodec[Measurement] = JsonCodecMaker.make[Measurement]
  given JsonValueCodec[Chemical] = JsonCodecMaker.make[Chemical]

final case class Account(id: Long :| GreaterEqual[0],
                         license: String :| ValidUUID,
                         emailAddress: String :| MinLength[3],
                         pin: String :| FixedLength[7],
                         activated: Long :| GreaterEqual[0],
                         deactivated: Long :| GreaterEqual[0]) extends Entity

/* This code blows up the Scala3 compiler!!!
import scala.collection.mutable
extension(account: Account)
  def validate: Valid =
    val map = mutable.Map.empty[String, String]
    account.id.refineEither[GreaterEqual[0]].fold(left => map += "id" -> left, right => right)
    account.license.refineEither[ValidUUID].fold(left => map += "license" -> left, right => right)
    account.emailAddress.refineEither[MinLength[3]].fold(left => map += "emailAddress" -> left, right => right)
    account.pin.refineEither[FixedLength[7]].fold(left => map += "pin" -> left, right => right)
    account.activated.refineEither[GreaterEqual[0]].fold(left => map += "activated" -> left, right => right)
    account.deactivated.refineEither[GreaterEqual[0]].fold(left => map += "deactivated" -> left, right => right)
    Valid(map.toMap) */

/* No given instance for constraint X, a common error above as well.
extension(account: Account)
  def validate: Either[String, Account] =
    for
      id           <- account.id.refineEither[GreaterEqual[0]]
      license      <- account.license.refineEither[ValidUUID]
      emailAddress <- account.emailAddress.refineEither[MinLength[3]]
      pin          <- account.pin.refineEither[FixedLength[7]]
      activated    <- account.activated.refineEither[GreaterEqual[0]]
      deactivated  <- account.deactivated.refineEither[GreaterEqual[0]]
    yield Account(id, license, emailAddress, pin, activated, deactivated) */

final case class Pool(id: Long :| GreaterEqual[0],
                      accountId: Long :| Greater[0],
                      name: String :| MinLength[3], 
                      built: Int :| Greater[0],
                      volume: Int :| GreaterEqual[100],
                      unit: UnitOfMeasure) extends Entity

final case class Cleaning(id: Long :| GreaterEqual[0],
                          poolId: Long :| Greater[0],
                          brush: Boolean,
                          net: Boolean,
                          skimmerBasket: Boolean,
                          pumpBasket: Boolean,
                          pumpFilter: Boolean,
                          vacuum: Boolean,
                          cleaned: Long :| Greater[0]) extends Entity

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
                             measured: Long :| Greater[0]) extends Entity

final case class Chemical(id: Long :| GreaterEqual[0],
                          poolId: Long :| Greater[0],
                          typeof: TypeOfChemical,
                          amount: Double :| Greater[0.0],
                          unit: UnitOfMeasure,
                          added: Long :| Greater[0]) extends Entity

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
