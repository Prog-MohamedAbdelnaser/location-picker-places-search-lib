package com.softtech.android_structure.domain.errors

import androidx.annotation.IntDef
import com.softtech.android_structure.domain.errors.ValidationTypeValues.COMPOSITE
import com.softtech.android_structure.domain.errors.ValidationTypeValues.EMAIL
import com.softtech.android_structure.domain.errors.ValidationTypeValues.ID_NUMBER
import com.softtech.android_structure.domain.errors.ValidationTypeValues.LARGER_THAN
import com.softtech.android_structure.domain.errors.ValidationTypeValues.LENGTH
import com.softtech.android_structure.domain.errors.ValidationTypeValues.LESS_THAN
import com.softtech.android_structure.domain.errors.ValidationTypeValues.LOCATION
import com.softtech.android_structure.domain.errors.ValidationTypeValues.PHONE
import com.softtech.android_structure.domain.errors.ValidationTypeValues.REGEX


/**
 *
 */
@IntDef(value = [COMPOSITE, EMAIL, LENGTH, PHONE, REGEX, ID_NUMBER, LOCATION, LARGER_THAN, LESS_THAN])
@Retention(value = AnnotationRetention.SOURCE)
annotation class ValidationType