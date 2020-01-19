package com.locationpicker.sample.domain.errors


/**
 *
 */
open class ValidationException(@ValidationType val validationType: Int,
                               override val message: String = "") : IllegalArgumentException()