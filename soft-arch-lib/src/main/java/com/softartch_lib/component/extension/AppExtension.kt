package com.softartch_lib.component.extension

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.softartch_lib.utility.TimeConvertor
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

inline fun LocalDate.toFormat( parseFormat:String,date: LocalDate,local:Locale= Locale.getDefault()):String{
    Locale.setDefault(local)
    val dateFormat = SimpleDateFormat(parseFormat,local)
    return dateFormat.format(date)
}
inline fun View.show(){
    this.visibility=View.VISIBLE
}

inline fun View.hide(){
    this.visibility=View.GONE
}
