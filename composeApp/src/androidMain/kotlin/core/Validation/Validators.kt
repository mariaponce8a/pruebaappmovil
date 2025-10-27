package core.Validation

object Validators {

    fun required(value: String, fieldName: String = "campo"): ValidationResult =
        if (value.isBlank()) ValidationResult.Invalid("$fieldName es obligatorio") else ValidationResult.Valid

}