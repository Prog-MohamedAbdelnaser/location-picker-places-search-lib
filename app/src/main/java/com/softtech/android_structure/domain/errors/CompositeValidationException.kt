package com.softtech.android_structure.domain.errors

import com.softtech.android_structure.domain.errors.ValidationTypeValues.COMPOSITE

class CompositeValidationException(val validationExceptions: List<ValidationException>) :
        ValidationException(COMPOSITE, "Multiple validation errors")