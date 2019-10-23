package com.homeex.domain.errors

/**
 *
 */
object ValidationTypeValues {

    /**
     * Used in case of multiple errors
     */
    const val COMPOSITE: Int = -1

    /**
     * Used for Email validation
     */
    const val EMAIL: Int = 0

    /**
     * Used for Password validation
     */
    const val LENGTH: Int = 1

    /**
     * Used for Phone validation
     */
    const val PHONE: Int = 2

    /**
     * Used for Regex validation
     */
    const val REGEX: Int = 3

    /**
     * Used for Id Number validation
     */
    const val ID_NUMBER: Int = 4


    /**
     * used for Latitude and Longitude validation
     */
    const val LOCATION: Int = 5

    /**
     * used for Larger than validation
     */
    const val LARGER_THAN = 6

    /**
     * used for Less than validation
     */
    const val LESS_THAN = 7

    /**
     * used for user name  validation
     */
    const val USER_NAME = 8

    /**
     * used for Password validation
     */
    const val PASSWORD = 9

    /**
     * used for Password validation
     */
    const val NEWPASSWORD = 12


    /**
     * used for Password validation
     */
    const val FEEDBACK_MESSAGE = 10

    /**
     * used for Password validation
     */
    const val FEEDBACK_TITLE = 11


    /**
     * used for Address validation
     */
    const val PACKAGE_ADDRESS = 12

    const val RECEIVER_ADDRESS = 13
    /**
     * used for RECEIVER_NAME validation
     */
    const val RECEIVER_NAME = 14

    /**
     * used for SHIPMENT_CONTENT validation
     */
    const val SHIPMENT_CONTENT = 15


}