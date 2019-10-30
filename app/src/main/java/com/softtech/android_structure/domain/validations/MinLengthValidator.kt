package com.softtech.android_structure.domain.validations

import com.softtech.android_structure.domain.errors.ValidationException
import com.softtech.android_structure.domain.errors.ValidationType
import com.softtech.android_structure.domain.errors.ValidationTypeValues
import io.reactivex.Single
import timber.log.Timber

/**
 * Used to validate If `String.length` is larger than or equal given `minLength`.
 *
 * `minLength` default value is `6`
 */
class MinLengthValidator(private val MESSAGE: String, private val minLength: Int,@ValidationType var validationType: Int?=null) :
    Validator<String> {
    override fun validate(data: String): Single<Boolean> =
            if (data.length >= minLength)
                Single.just(true)
            else {
                Timber.i("validationType$validationType")
                if (validationType == null) {
                    Single.error(ValidationException(ValidationTypeValues.LENGTH, MESSAGE))

                } else {
                    Single.error(ValidationException(validationType!!, MESSAGE))

                }
            }
}