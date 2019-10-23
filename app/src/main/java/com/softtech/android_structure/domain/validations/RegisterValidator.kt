package com.softtech.android_structure.domain.validations

import com.softtech.android_structure.domain.errors.CompositeValidationException
import com.softtech.android_structure.domain.errors.ValidationException
import com.softtech.android_structure.domain.entities.account.RegisterParams
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.functions.Function

class RegisterValidator(private val phoneNumberValidator: PhoneNumberValidator,
                        private val passwordValidator: MinLengthValidator,
                        private val userNameValidator: MinLengthValidator,
                        private val emailValidator: EmailValidator
) : Validator<RegisterParams> {

    private val errorsList: ArrayList<ValidationException> by lazy {
        ArrayList<ValidationException>()
    }

    override fun validate(data: RegisterParams): Single<Boolean> {
        errorsList.clear() // Clear old errors from previous validate
        return Single.zip(listOfValidatorSingles(data), createValidatorsZipper())
    }

    private fun listOfValidatorSingles(data: RegisterParams) =
            listOf(passwordValidationSingle(data), phoneNumberValidationSingle(data),userNameValidationSingle(data),emailValidationSingle(data))

    private fun passwordValidationSingle(data: RegisterParams) =
        passwordValidator.validate(data.password!!).onErrorResumeNext(onErrorResumeNextFunction())


    private fun phoneNumberValidationSingle(data: RegisterParams) =
            phoneNumberValidator.validate(data.phoneNumber!!).onErrorResumeNext(onErrorResumeNextFunction())


    private fun userNameValidationSingle(data: RegisterParams) =
       userNameValidator.validate(data.userName!!).onErrorResumeNext(onErrorResumeNextFunction())


    private fun emailValidationSingle(data: RegisterParams) =
        emailValidator.validate(data.email!!).onErrorResumeNext(onErrorResumeNextFunction())




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