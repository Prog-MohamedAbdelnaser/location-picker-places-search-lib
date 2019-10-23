package com.softtech.android_structure.domain.validations

import io.reactivex.Single

/**
 * Used to validate business parameters or responses.
 * T is the type of Object to validate.
 */
interface Validator<T> {

    /**
     * Check if T will pass the validate rules.
     *
     * Return Single < Boolean > which is not operating in specific thread.
     *
     * Boolean value must be true if T is valid.
     *
     * If T is not valid Single.onError(Throwable) will be called with ValidationException or
     * CompositeValidationException.
     */
    fun validate(data: T): Single<Boolean>
}