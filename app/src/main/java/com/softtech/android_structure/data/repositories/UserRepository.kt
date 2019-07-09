package com.softtech.android_structure.data.repositories

import com.softtech.android_structure.data.repositories.RepositoriesConstants.KEY_PREFRENCE_USER
import com.softtech.android_structure.data.sources.local.AppPreference
import com.softtech.android_structure.entities.account.User

// Created by mohamed abdelnaser on 7/4/19.
class UserRepository (private val appPreference: AppPreference){

    fun getLogedInUser():User?=
        if (appPreference.getObject(KEY_PREFRENCE_USER,User::class.java) != null)
            appPreference.getObject(KEY_PREFRENCE_USER,User::class.java)
         else null

    fun isLoged():Boolean = getLogedInUser()!=null && !getLogedInUser()!!.id.isNullOrEmpty()

}