package com.softtech.android_structure.domain.validations

import com.softtech.android_structure.domain.errors.ValidationException
import com.homeex.domain.errors.ValidationTypeValues.EMAIL
import io.reactivex.Single
import timber.log.Timber
import java.util.regex.Pattern


/**
 * Used to validate if `String` is valid email address.
 */
class EmailValidator(private val MESSAGE: String) : Validator<String> {

    companion object {
        private const val EMAIL_REGEX: String = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9]" +
                "[a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
    }

    override fun validate(data: String): Single<Boolean> =
            if (Pattern.compile(EMAIL_REGEX).matcher(data).matches())
                Single.just(true)
            else{
                Timber.i("EMAIL_REGEX$data")
                Single.error(ValidationException(EMAIL, MESSAGE))

            }

}

