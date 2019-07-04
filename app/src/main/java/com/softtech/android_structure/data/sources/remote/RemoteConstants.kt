package com.nasmanpower.nas.data.sources.remote

import com.nasmanpower.nas.application.NasApp

object RemoteConstants {
    const val BASE_URL = "https://nasmanpower.com/api/" /*"http://nasmanpower.getsandbox.com/"*/
    const val BASE_TEST_URL="http://nasmanpower.com:8002/api/"
    const val REAL_SERVER_URL = "https://nasmanpower.com/"
    const val TEST_SERVER_URL = "http://nasmanpower.com:8002/"

    const val CONNECT_TIMEOUT: Long = 60
    const val READ_TIMEOUT: Long = 60
    const val WRITE_TIMEOUT: Long = 60

    fun getBaseURL():String = if (NasApp.isTestVersion) BASE_TEST_URL else BASE_URL
    fun getServerURL():String = if (NasApp.isTestVersion) TEST_SERVER_URL else REAL_SERVER_URL


     val ABOUT_US_AR = getServerURL() +"/home/phAboutUsAr"
     val ABOUT_US_EN = getServerURL()+"/home/phAboutUsEn"

     val TERMS_AR = "${getServerURL()}/home/PhConditionAndRightsAr"
     val TERMS_EN = "${getServerURL()}/home/PhConditionAndRightsEn"


     val BUSINESS_LINK_AR = "${getServerURL()}/home/phBusinessSectorsAr"
     val BUSINESS_LINK_EN = "${getServerURL()}/home/phBusinessSectorsEn"

}