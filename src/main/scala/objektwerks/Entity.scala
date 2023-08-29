package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*

import io.github.iltotore.iron.*
import io.github.iltotore.iron.jsoniter.given
import io.github.iltotore.iron.constraint.collection.{FixedLength, MinLength}
import io.github.iltotore.iron.constraint.numeric.{Greater, GreaterEqual}
import io.github.iltotore.iron.constraint.numeric.Interval.Closed
import io.github.iltotore.iron.constraint.string.ValidUUID

sealed trait Entity:
  val id: Long :| Greater[0]

object Entity:
  given JsonValueCodec[Entity] = JsonCodecMaker.make[Entity]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Account] = JsonCodecMaker.make[Account]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Pool] = JsonCodecMaker.make[Pool]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Cleaning] = JsonCodecMaker.make[Cleaning]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Measurement] = JsonCodecMaker.make[Measurement]( CodecMakerConfig.withDiscriminatorFieldName(None) )
  given JsonValueCodec[Chemical] = JsonCodecMaker.make[Chemical]( CodecMakerConfig.withDiscriminatorFieldName(None) )

final case class Account(id: Long :| Greater[0],
                         license: String :| ValidUUID,
                         emailAddress: String :| MinLength[3],
                         pin: String :| FixedLength[7],
                         activated: Long :| GreaterEqual[0],
                         deactivated: Long :| GreaterEqual[0]) extends Entity

final case class Pool(id: Long :| Greater[0],
                      accountId: Long :| Greater[0],
                      name: String :| MinLength[3], 
                      built: Long :| GreaterEqual[1],
                      volume: Int :| GreaterEqual[100],
                      unit: UnitOfMeasure) extends Entity

final case class Cleaning(id: Long :| Greater[0],
                          poolId: Long :| Greater[0],
                          brush: Boolean,
                          net: Boolean,
                          skimmerBasket: Boolean,
                          pumpBasket: Boolean,
                          pumpFilter: Boolean,
                          vacuum: Boolean,
                          cleaned: Long :| GreaterEqual[1]) extends Entity

final case class Measurement(id: Long :| Greater[0],
                             poolId: Long :| Greater[0],
                             totalChlorine: Int :| Closed[1, 5],
                             freeChlorine: Int :| Closed[1, 5],
                             combinedChlorine: Double :| Closed[0.0, 0.5],
                             ph: Double :| Closed[6.2, 8.4],
                             calciumHardness: Int :| Closed[250, 500],
                             totalAlkalinity: Int :| Closed[80, 120],
                             cyanuricAcid: Int :| Closed[30, 100],
                             totalBromine: Int :| Closed[2, 10],
                             salt: Int :| Closed[2700, 3400],
                             temperature: Int :| Closed[50, 100],
                             measured: Long :| GreaterEqual[1]) extends Entity

final case class Chemical(id: Long :| Greater[0],
                          poolId: Long :| Greater[0],
                          typeof: TypeOfChemical,
                          amount: Double :| Greater[0.0],
                          unit: UnitOfMeasure,
                          added: Long :| GreaterEqual[1]) extends Entity

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