package com.softtech.android_structure.base.utility

import java.util.*


object EncryptUtils {



     fun getRandomString(): String {
        val SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
        val salt = StringBuilder()
        val rnd = Random()
        while (salt.length < 18) { // length of the random string.
            val index = (rnd.nextFloat() * SALTCHARS.length) .toInt()
            salt.append(SALTCHARS[index])
        }
        return salt.toString()

    }
}