package com.softtech.android_structure.di

import com.softtech.android_structure.R
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import org.koin.experimental.builder.single

val validatorModule = module {
//
//    single { EmailValidator(androidContext().resources.getString(R.string.invalid_email)) }
//
//    single(FULL_NAME_MIN_LENGTH_VALIDATOR) {
//        MinLengthValidator(
//                androidContext().resources.getString(R.string.invalid_full_name),
//                androidContext().resources.getInteger(R.integer.full_name_min_length)
//        )
//    }
//
//    single(FULL_NAME_MAX_LENGTH_VALIDATOR) {
//        MaxLengthValidator(
//                androidContext().resources.getString(R.string.invalid_full_name),
//                androidContext().resources.getInteger(R.integer.full_name_max_length)
//        )
//    }
//
//
//
//    single(FULL_NAME_LENGTH_VALIDATOR) {
//        LengthValidator(get(FULL_NAME_MIN_LENGTH_VALIDATOR), get(FULL_NAME_MAX_LENGTH_VALIDATOR))
//    }
//
//
//
//    single(ValidationConstants.PROMOTION_CODE_MIN_LENGTH_VALIDATOR){
//        MinLengthValidator(
//                androidContext().resources.getString(R.string.invalid_promo_code),
//                androidContext().resources.getInteger(R.integer.promo_code_min_length)
//        )
//    }
//
//
//
//    single { PhoneNumberValidator(get(), androidContext().getString(R.string.invalid_phone_number)) }
//
//    factory { (requestCode: Int) ->
//        NotEmptyValidator(requestCode, androidContext().getString(R.string.can_not_be_empty))
//    }

}