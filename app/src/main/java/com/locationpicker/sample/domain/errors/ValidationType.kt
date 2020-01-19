package com.locationpicker.sample.domain.errors

import androidx.annotation.IntDef
import com.locationpicker.sample.domain.errors.ValidationTypeValues.COMPOSITE
import com.locationpicker.sample.domain.errors.ValidationTypeValues.EMAIL
import com.locationpicker.sample.domain.errors.ValidationTypeValues.ID_NUMBER
import com.locationpicker.sample.domain.errors.ValidationTypeValues.LARGER_THAN
import com.locationpicker.sample.domain.errors.ValidationTypeValues.LENGTH
import com.locationpicker.sample.domain.errors.ValidationTypeValues.LESS_THAN
import com.locationpicker.sample.domain.errors.ValidationTypeValues.LOCATION
import com.locationpicker.sample.domain.errors.ValidationTypeValues.PHONE
import com.locationpicker.sample.domain.errors.ValidationTypeValues.REGEX


/**
 *
 */
@IntDef(value = [COMPOSITE, EMAIL, LENGTH, PHONE, REGEX, ID_NUMBER, LOCATION, LARGER_THAN, LESS_THAN])
@Retention(value = AnnotationRetention.SOURCE)
annotation class ValidationType