package objektwerks

final class Validated[E](val validations: Validations, val entity: E):
  def isValid: Boolean = validations.isValid