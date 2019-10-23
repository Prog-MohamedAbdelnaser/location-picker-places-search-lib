package com.softtech.android_structure.di

import com.homeex.domain.errors.ValidationTypeValues
import com.softtech.android_structure.R
import com.softtech.android_structure.domain.validations.EmailValidator
import com.softtech.android_structure.domain.validations.MinLengthValidator
import com.softtech.android_structure.domain.validations.PhoneNumberValidator
import com.softtech.android_structure.domain.validations.RegisterValidator
import com.softtech.android_structure.domain.validations.ValidationConstants.FULL_NAME_MIN_LENGTH_VALIDATOR
import com.softtech.android_structure.domain.validations.ValidationConstants.MIN_LENGTH_VALIDATOR
import com.softtech.android_structure.domain.validations.ValidationConstants.PASSWORD_MIN_LENGTH_VALIDATOR
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val validatorModule = module {

    factory { PhoneNumberValidator(androidContext().getString(R.string.invalid_phone)) }

    factory { EmailValidator(androidContext().getString(R.string.invalid_email)) }

    single (MIN_LENGTH_VALIDATOR){ MinLengthValidator(androidContext().getString(R.string.fill_data),androidContext().resources.getInteger(R.integer.min_length)) }

    single (FULL_NAME_MIN_LENGTH_VALIDATOR){ MinLengthValidator(androidContext().getString(R.string.invalid_name),androidContext().resources.getInteger(R.integer.min_length), ValidationTypeValues.USER_NAME) }

    single (PASSWORD_MIN_LENGTH_VALIDATOR){ MinLengthValidator(androidContext().getString(R.string.invalid_password),androidContext().resources.getInteger(R.integer.min_length), ValidationTypeValues.PASSWORD) }




}