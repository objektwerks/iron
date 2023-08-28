package objektwerks

import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.collection.{Length, MinLength}
import io.github.iltotore.iron.constraint.numeric.{Greater, GreaterEqual}
import io.github.iltotore.iron.constraint.string.ValidUUID

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

sealed trait Entity:
  val id: Long :| GreaterEqual[0]

final case class Account(id: Long :| GreaterEqual[0],
                         license: String :| ValidUUID,
                         emailAddress: String,
                         pin: String :| Length[7],
                         activated: Long :| GreaterEqual[0],
                         deactivated: Long :| GreaterEqual[0]) extends Entity

final case class Pool(id: Long :| GreaterEqual[0],
                      accountId: Long :| GreaterEqual[1],
                      built: Long :| GreaterEqual[1],
                      name: String :| MinLength[3], 
                      volume: Int :| Greater[100],
                      unit: UnitOfMeasure) extends Entity

final case class Cleaning(id: Long :| GreaterEqual[0],
                          poolId: Long :| GreaterEqual[1],
                          brush: Boolean,
                          net: Boolean,
                          skimmerBasket: Boolean,
                          pumpBasket: Boolean,
                          pumpFilter: Boolean,
                          vacuum: Boolean,
                          cleaned: Long :| GreaterEqual[1]) extends Entity

final case class Measurement(id: Long :| GreaterEqual[0],
                             poolId: Long :| GreaterEqual[1],
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
                             measured: Long :| GreaterEqual[1]) extends Entity

object Measurement:
  val totalChlorineRange = Range(1, 5).inclusive
  val freeChlorineRange = Range(1, 5).inclusive
  val combinedChlorineRange = Set(0.0, 0.1, 0.2, 0.3, 0.4, 0.5)
  val phRange = Set(6.2, 6.3, 6.4, 6.5, 6.6, 6.7, 6.8, 6.9, 7.0, 7.1, 7.2, 7.3, 7.4, 7.5, 7.6, 7.7, 7.8, 7.9, 8.0, 8.1, 8.2, 8.3, 8.4)
  val calciumHardnessRange = Range(250, 500).inclusive
  val totalAlkalinityRange = Range(80, 120).inclusive
  val cyanuricAcidRange = Range(30, 100).inclusive
  val totalBromineRange = Range(2, 10).inclusive
  val saltRange = Range(2700, 3400).inclusive
  val temperatureRange = Range(50, 100).inclusive

final case class Chemical(id: Long :| GreaterEqual[0],
                          poolId: Long :| GreaterEqual[1],
                          typeof: TypeOfChemical,
                          amount: Double :| Greater[0.0],
                          unit: UnitOfMeasure,
                          added: Long :| GreaterEqual[1]) extends Entity