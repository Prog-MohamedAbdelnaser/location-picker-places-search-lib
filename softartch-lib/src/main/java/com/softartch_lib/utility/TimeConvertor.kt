package com.agentsapp.naas.softview

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object TimeConvertor {
       const  val DATETIME_ShortFormat =   "EEE, d MMM GGG hh:mm aaa"
        const  val DAY_OF_YEAY_Foramt = "yyyy-MM-dd"
       const  val HOUR_OF_DAY_Format12=   "hh:mm aaa"
        const  val HOUR_OF_DAY_Format="HH:mm"
        const val TIMESTAMP_Format="yyyy-MM-dd HH:mm:ss"
        const val TIMESTAMP_MS_Format="yyyy-MM-dd HH:mm:ss.SSS"
       const val DAY_MONTH_ByName_Format=  "EEEE,dd MMMM  "
       const val DAY_MONTH_ByName_ShortFormat = "EEE, d MMM "

       fun getSqlTimestampe(milliSec: Long): String {
            val dateTimeFormat = SimpleDateFormat(TIMESTAMP_Format)
            val timestamp = dateTimeFormat.format(milliSec)

            println("timestamp ${timestamp}")
            return timestamp
        }

          fun getDifferenceDaysWithCurrentTime(date: String): Long {
            val locale = Locale("en")
            Locale.setDefault(locale)
            val dateTimeFormat_MS = SimpleDateFormat(TIMESTAMP_MS_Format)
            val dateTimeFormat = SimpleDateFormat(TIMESTAMP_Format)
            val dateFormat = SimpleDateFormat(DAY_OF_YEAY_Foramt)
            val c = Calendar.getInstance().time
            var minutes: Long = 0
            var hours: Long = 0
            var days: Long = 0
            var oldDate: Date?=null
            var currentDate: Date?=null
            try {
                val oldDateTime = dateTimeFormat_MS.parse(date)
                val oldDateString=dateFormat.format(oldDateTime)
                oldDate = dateFormat.parse(oldDateString)
                val currentDateString=dateFormat.format(c);
                currentDate=dateFormat.parse(currentDateString)
            } catch (e: ParseException) {
                println("date format exeption ")

                try {

                    val oldDateTime = dateTimeFormat.parse(date)
                    val oldDateString=dateFormat.format(oldDateTime)
                    oldDate = dateFormat.parse(oldDateString)
                    val currentDateString=dateFormat.format(c);
                    currentDate=dateFormat.parse(currentDateString)
                } catch (e: ParseException) {
                    e.printStackTrace()
                    days =1
                }
            }
            val dif=currentDate!!.time - oldDate!!.time
            val s=dif/1000
            minutes=s/60
            hours =minutes/60
            days=hours/24
            // Log.e("toyBornTime", "" + toyBornTime);
            return days
        }


          fun dateMiliSC_To_Date(date: String,parseFormat:String,local: String): String? {
            val locale = Locale(local)
            Locale.setDefault(locale)
            val dateTimeFormat_MS = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
            val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val dateFormat = SimpleDateFormat(parseFormat)

            var oldDateString: String?=null

            try {
                val oldDateTime = dateTimeFormat_MS.parse(date)
                oldDateString=dateFormat.format(oldDateTime)



            } catch (e: ParseException) {
                println("date format exeption ")

                try {

                    val oldDateTime = dateTimeFormat.parse(date)
                    oldDateString=dateFormat.format(oldDateTime)


                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }

            return oldDateString
        }


          fun dateMiliSC_To_DateName(date: String,parseFormat:String,local: String): String? {
           val locale = Locale(local)
           Locale.setDefault(locale)
           val dateTimeFormat_MS = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
           val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
           val dateFormat = SimpleDateFormat(parseFormat)

           var oldDateString: String?=null

           try {
               val oldDateTime = dateTimeFormat_MS.parse(date)
               oldDateString=dateFormat.format(oldDateTime)



           } catch (e: ParseException) {
               println("date format exeption ")

               try {

                   val oldDateTime = dateTimeFormat.parse(date)
                   oldDateString=dateFormat.format(oldDateTime)


               } catch (e: ParseException) {
                   e.printStackTrace()
               }
           }

           return oldDateString
       }

        fun getOptimizedTime(date: String,local: String=Locale.getDefault().language): String? {
            if (getDifferenceDaysWithCurrentTime(date) >1){
                return dateMiliSC_To_Date(date, DAY_MONTH_ByName_ShortFormat,local)
            }else{
                return dateMiliSC_To_Date(date, HOUR_OF_DAY_Format12,local)
            }
        }

    fun getOptimizedTimeFromMiliSeconde(milliSec: Long,local: String=Locale.getDefault().language): String? {
        val date= getSqlTimestampe(milliSec)
        if (getDifferenceDaysWithCurrentTime(date) >1){
            return dateMiliSC_To_Date(date, DAY_MONTH_ByName_ShortFormat,local)
        }else{
            return dateMiliSC_To_Date(date, HOUR_OF_DAY_Format12,local)
        }
    }

       fun getOptimizedDateTimeHour(date: String,local: String=Locale.getDefault().language): String? {
           if (getDifferenceDaysWithCurrentTime(date) >1){
               return dateMiliSC_To_Date(date, DATETIME_ShortFormat,local)
           }else{
               return dateMiliSC_To_Date(date, HOUR_OF_DAY_Format,local)
           }
       }
          fun getDifferenceHoursWithCurrentTime(date: String): Long {
            val locale = Locale("en")
            Locale.setDefault(locale)
            val dateTimeFormat_MS = SimpleDateFormat(TIMESTAMP_MS_Format)
            val dateTimeFormat = SimpleDateFormat(TIMESTAMP_Format)
            val dateFormat = SimpleDateFormat(DAY_OF_YEAY_Foramt)
            val c = Calendar.getInstance().time
            var minutes: Long = 0
            var hours: Long = 0
            var oldDate:Date?=null
            var currentDate:Date?=null
            try {

                val oldDateTime = dateTimeFormat_MS.parse(date)
                val oldDateString=dateFormat.format(oldDateTime)
                oldDate = dateFormat.parse(oldDateString)
                val currentDateString=dateFormat.format(c);
                currentDate=dateFormat.parse(currentDateString)
            } catch (e: ParseException) {
                e.printStackTrace()
                try {

                    val oldDateTime = dateTimeFormat.parse(date)
                    val oldDateString=dateFormat.format(oldDateTime)
                    oldDate = dateFormat.parse(oldDateString)
                    val currentDateString=dateFormat.format(c);
                    currentDate=dateFormat.parse(currentDateString)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
            val dif=currentDate!!.time - oldDate!!.time
            val s=dif/1000
            minutes=s/60
            hours =minutes/60

            return hours
        }

          fun getDifferenceMinutesWithCurrentTime(date: String): Long {
            val locale = Locale("en")
            Locale.setDefault(locale)


            val dateTimeFormat_MS = SimpleDateFormat(TIMESTAMP_MS_Format)
            val dateTimeFormat = SimpleDateFormat(TIMESTAMP_Format)

            val c = Calendar.getInstance().time
            var minutes: Long = 0


            var oldDateTiem:Date?=null
            var currentDateTime:Date?=null
            try {

                 oldDateTiem = dateTimeFormat_MS.parse(date)
                val currentDateString=dateTimeFormat.format(c);
                currentDateTime=dateTimeFormat.parse(currentDateString)

            } catch (e: ParseException) {
                println("date format exeption ")

                try {
                    val currentDateString=dateTimeFormat.format(c);
                    currentDateTime=dateTimeFormat.parse(currentDateString)
                } catch (e: ParseException) {
                    println("date time  format exeption ")

                    e.printStackTrace()
                }
            }
             // println("get date ${currentDateTime!!.time} - ${oldDateTiem!!.time}")
            val dif=currentDateTime!!.time - oldDateTiem!!.time
            val s=dif/1000
            minutes=s/60
            // Log.e("toyBornTime", "" + toyBornTime);


              println("test deff is "+minutes)
            return minutes
        }
        fun getDayDateMilliSeconde(dateDay:String):Long{
            val dateFormat = SimpleDateFormat(DAY_OF_YEAY_Foramt)
            val date = dateFormat.parse(dateDay)
            return date.time


        }


}