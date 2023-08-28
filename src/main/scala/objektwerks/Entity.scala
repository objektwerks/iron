package objektwerks

import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.collection.{Length, MinLength}
import io.github.iltotore.iron.constraint.numeric.{Greater, GreaterEqual, LessEqual}
import io.github.iltotore.iron.constraint.string.ValidUUID

type Between[Min, Max] = GreaterEqual[Min] & LessEqual[Max]

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
                             totalChlorine: Int :| Between[1, 5],
                             freeChlorine: Int :| Between[1, 5],
                             combinedChlorine: Double :| Between[0.0, 0.5],
                             ph: Double :| Between[6.2, 8.4],
                             calciumHardness: Int :| Between[250, 500],
                             totalAlkalinity: Int :| Between[80, 120],
                             cyanuricAcid: Int :| Between[30, 100],
                             totalBromine: Int :| Between[2, 10],
                             salt: Int :| Between[2700, 3400],
                             temperature: Int :| Between[50, 100],
                             measured: Long :| GreaterEqual[1]) extends Entity

final case class Chemical(id: Long :| GreaterEqual[0],
                          poolId: Long :| GreaterEqual[1],
                          typeof: TypeOfChemical,
                          amount: Double :| Greater[0.0],
                          unit: UnitOfMeasure,
                          added: Long :| GreaterEqual[1]) extends Entity