package com.softtech.android_structure.domain.entities

class MessageParams(var senderId:String, var text:String,var created_at:String,var sendStatus:Boolean=false,var messageId:Long,var readStatus:Boolean=false){

    fun toMap():Map<String,Any>{
       return HashMap<String,Any>().apply {
           put("senderId",senderId)
           put("text",text)
           put("created_at",created_at)
           put("sendStatus",true)
       }
    }
}