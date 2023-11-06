package objektwerks

import com.github.plokhotnyuk.jsoniter_scala.core.*
import com.github.plokhotnyuk.jsoniter_scala.macros.*

import scala.collection.mutable

final class Invalidations:
  type Field = String
  type Message = String

  private val invalidFields = mutable.Map[Field, Message]()

  def add(field: Field, message: Message): Unit = invalidFields += field -> message

  def get(field: Field): Option[Message] = invalidFields.get(field)

  def isEmpty: Boolean = invalidFields.isEmpty

  def toEither[E](either: Either[Unit, E]): Either[Invalidations, E] =
    if this.isEmpty && either.isRight then Right(either.right.get)
    else Left(this)

  def toMap: Map[Field, Message] = invalidFields.toMap

object Invalidations:
  given JsonValueCodec[Invalidations] = JsonCodecMaker.make[Invalidations]