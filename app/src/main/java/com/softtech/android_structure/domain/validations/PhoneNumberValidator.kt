package com.softtech.android_structure.domain.validations
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.PhoneNumberUtil.*
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber
import com.softtech.android_structure.domain.errors.ValidationTypeValues
import com.softtech.android_structure.domain.errors.ValidationException
import com.softtech.android_structure.domain.validations.ValidationConstants.EGYPT_COUNTRY_CODE
import io.reactivex.Single
import timber.log.Timber

/**
 * Used to validate if `String` is a valid phone number based on given `countryCode` with default value equals `SA`.
 */
class PhoneNumberValidator( private val MESSAGE: String) : Validator<String> {

    val phoneNumberUtil: PhoneNumberUtil= getInstance()

    override fun validate(data: String): Single<Boolean> {
        try {

            val phoneNumber: PhoneNumber = getPhoneNumber(data)

            val numberType = phoneNumberUtil.getNumberType(phoneNumber)
            if (numberType == PhoneNumberType.MOBILE &&
                phoneNumberUtil.isValidNumberForRegion(phoneNumber, EGYPT_COUNTRY_CODE)) {
                return Single.just(true)
            }else{
                return Single.error(ValidationException(ValidationTypeValues.PHONE, MESSAGE))
            }

        } catch (numberParseException: NumberParseException) {
            Timber.d(numberParseException)
        }

        return Single.error(ValidationException(ValidationTypeValues.PHONE, MESSAGE))
    }

    @Throws(NumberParseException::class)
    private fun getPhoneNumber(phoneNumber: String?): PhoneNumber = phoneNumberUtil.parse(phoneNumber,EGYPT_COUNTRY_CODE )

    companion object {
        fun isPhoneNumberValid(phoneNumber: String?): Boolean {
            val phoneNumberUtil: PhoneNumberUtil = getInstance()
            val phoneNumberq: PhoneNumber = phoneNumberUtil.parse(phoneNumber, EGYPT_COUNTRY_CODE)
            val numberType = phoneNumberUtil.getNumberType(phoneNumberq)
            return numberType == PhoneNumberType.MOBILE && phoneNumberUtil.isValidNumberForRegion(
                phoneNumberq, EGYPT_COUNTRY_CODE)
        }
    }
}