package com.locationpicker.sample.data.sources.remote


object RemoteConstants {
    const val BASE_URL = "https://nasmanpower.com/api/" /*"http://nasmanpower.getsandbox.com/"*/
    const val BASE_TEST_URL="http://nasmanpower.com:8002/api/"
    const val REAL_SERVER_URL = "https://nasmanpower.com/"
    const val TEST_SERVER_URL = "http://nasmanpower.com:8002/"

    const val CONNECT_TIMEOUT: Long = 60
    const val READ_TIMEOUT: Long = 60
    const val WRITE_TIMEOUT: Long = 60

    fun getBaseURL():String = if (com.locationpicker.sample.application.ApplicationClass.isTestVersion) BASE_TEST_URL else BASE_URL
    fun getServerURL():String = if (com.locationpicker.sample.application.ApplicationClass.isTestVersion) TEST_SERVER_URL else REAL_SERVER_URL


     val ABOUT_US_AR = getServerURL() +"/nav_home/phAboutUsAr"
     val ABOUT_US_EN = getServerURL() +"/nav_home/phAboutUsEn"

     val TERMS_AR = "${getServerURL()}/nav_home/PhConditionAndRightsAr"
     val TERMS_EN = "${getServerURL()}/nav_home/PhConditionAndRightsEn"


     val BUSINESS_LINK_AR = "${getServerURL()}/nav_home/phBusinessSectorsAr"
     val BUSINESS_LINK_EN = "${getServerURL()}/nav_home/phBusinessSectorsEn"

}