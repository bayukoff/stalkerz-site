package ru.cool.sectorsite.util

import org.springframework.validation.BindingResult

object ErrorUtil {
    fun checkErrorField(fieldName: String, bindingResult: BindingResult): String?{
        val errors = bindingResult.fieldErrors
        errors.forEach {
            if (it.field == fieldName) {
                return it.field
            }
        }
        return null
    }
}