package objektwerks

import scala.collection.mutable

final class Invalidations:
  type Field = String
  type Message = String

  private val invalidFields = mutable.Map[Field, Message]()

  def isEmpty: Boolean = invalidFields.isEmpty

  def add(field: Field, message: Message): Unit = invalidFields += field -> message

  def get(field: Field): Option[Message] = invalidFields.get(field)

  def toMap: Map[Field, Message] = invalidFields.toMap