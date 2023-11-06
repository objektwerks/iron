package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*

import scala.collection.mutable

type Field = String
type Message = String

final case class Invalidations(fields: mutable.Map[Field, Message] = mutable.Map[Field, Message]()):
  def add(field: Field, message: Message): Unit = fields += field -> message

  def get(field: Field): Option[Message] = fields.get(field)

  def isEmpty: Boolean = fields.isEmpty

  def toEither[E](either: Either[Unit, E]): Either[Invalidations, E] =
    if this.isEmpty && either.isRight then Right(either.right.get)
    else Left(this)

  def toMap: Map[Field, Message] = fields.toMap

object Invalidations:
  given JsonValueCodec[Invalidations] = JsonCodecMaker.make[Invalidations]