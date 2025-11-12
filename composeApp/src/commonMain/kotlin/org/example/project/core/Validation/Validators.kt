package org.example.project.core.Validation

object Validators {

    fun required(value: String, fieldName: String = "campo"): org.example.project.core.Validation.ValidationResult =
        if (value.isBlank()) _root_ide_package_.org.example.project.core.Validation.ValidationResult.Invalid("$fieldName es obligatorio") else _root_ide_package_.org.example.project.core.Validation.ValidationResult.Valid

}