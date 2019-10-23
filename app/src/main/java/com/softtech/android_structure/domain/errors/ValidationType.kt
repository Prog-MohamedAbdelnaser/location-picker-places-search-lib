package com.softtech.android_structure.domain.errors

import androidx.annotation.IntDef
import com.homeex.domain.errors.ValidationTypeValues.COMPOSITE
import com.homeex.domain.errors.ValidationTypeValues.EMAIL
import com.homeex.domain.errors.ValidationTypeValues.ID_NUMBER
import com.homeex.domain.errors.ValidationTypeValues.LARGER_THAN
import com.homeex.domain.errors.ValidationTypeValues.LENGTH
import com.homeex.domain.errors.ValidationTypeValues.LESS_THAN
import com.homeex.domain.errors.ValidationTypeValues.LOCATION
import com.homeex.domain.errors.ValidationTypeValues.PHONE
import com.homeex.domain.errors.ValidationTypeValues.REGEX


/**
 *
 */
@IntDef(value = [COMPOSITE, EMAIL, LENGTH, PHONE, REGEX, ID_NUMBER, LOCATION, LARGER_THAN, LESS_THAN])
@Retention(value = AnnotationRetention.SOURCE)
annotation class ValidationType