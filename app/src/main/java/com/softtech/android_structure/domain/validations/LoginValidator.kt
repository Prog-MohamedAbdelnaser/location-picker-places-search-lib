package com.softtech.android_structure.domain.validations

import com.softtech.android_structure.domain.entities.account.LoginParameters
import com.softtech.android_structure.domain.errors.CompositeValidationException
import com.softtech.android_structure.domain.errors.ValidationException
import com.softtech.android_structure.domain.entities.account.RegisterParams
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.functions.Function

class LoginValidator(private val phoneNumberValidator: PhoneNumberValidator,
                     private val passwordValidator: MinLengthValidator,
                     private val deviceIdValidator: MinLengthValidator
) : Validator<LoginParameters> {

    private val errorsList: ArrayList<ValidationException> by lazy {
        ArrayList<ValidationException>()
    }

    override fun validate(data: LoginParameters): Single<Boolean> {
        errorsList.clear() // Clear old errors from previous validate
        return Single.zip(listOfValidatorSingles(data), createValidatorsZipper())
    }

    private fun listOfValidatorSingles(data: LoginParameters) =
            listOf(passwordValidationSingle(data.password),
                phoneNumberValidationSingle(data.phoneNumber),
                deviceIdValidationSingle(data.deviceId))

    private fun passwordValidationSingle(data: String?) =
        passwordValidator.validate(data!!).onErrorResumeNext(onErrorResumeNextFunction())


    private fun phoneNumberValidationSingle(data: String?) =
            phoneNumberValidator.validate(data!!).onErrorResumeNext(onErrorResumeNextFunction())


    private fun deviceIdValidationSingle(data: String?) =
       deviceIdValidator.validate(data!!).onErrorResumeNext(onErrorResumeNextFunction())



    private fun onErrorResumeNextFunction(): Function<in Throwable, SingleSource<out Boolean>> {
        return Function {
            if (it is ValidationException) {
                errorsList.add(it)
                Single.just(true)
            } else {
                Single.error(it)
            }
        }
    }

    private fun createValidatorsZipper(): Function<Any, Boolean> = Function {
        if (errorsList.isEmpty()) {
            true
        } else {
            throw CompositeValidationException(errorsList)
        }
    }
}