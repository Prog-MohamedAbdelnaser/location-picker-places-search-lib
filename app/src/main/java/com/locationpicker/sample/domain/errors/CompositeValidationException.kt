package com.locationpicker.sample.domain.errors

import com.locationpicker.sample.domain.errors.ValidationTypeValues.COMPOSITE

class CompositeValidationException(val validationExceptions: List<ValidationException>) :
        ValidationException(COMPOSITE, "Multiple validation errors")