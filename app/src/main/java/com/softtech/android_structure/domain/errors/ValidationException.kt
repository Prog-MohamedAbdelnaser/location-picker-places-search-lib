package com.softtech.android_structure.domain.errors

import com.softtech.android_structure.domain.errors.ValidationType


/**
 *
 */
open class ValidationException(@ValidationType val validationType: Int,
                               override val message: String = "") : IllegalArgumentException()