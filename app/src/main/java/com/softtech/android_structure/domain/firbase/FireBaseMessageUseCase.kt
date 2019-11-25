package com.softtech.android_structure.domain.firbase

import android.util.Log
import com.google.firebase.database.*
import com.softtech.android_structure.domain.entities.ChatMessage
import com.softtech.android_structure.domain.entities.MessageParams
import com.softtech.android_structure.domain.firbase.FirebaseConstants.CHAT_COLLECTION
import com.softtech.android_structure.domain.firbase.FirebaseConstants.IS_READ_KEY

class FireBaseMessageUseCase(private val databaseReference: DatabaseReference) {


    fun sendMessage(messageParams: MessageParams,roomName:String){
       insertIntoCollection(FirebaseConstants.MESSAGES_COLLECTION_KEY+"/"+roomName+"/$CHAT_COLLECTION",messageParams,messageParams.created_at.toString())
    }

    fun readMessage(roomName: String, message: ChatMessage){
        databaseReference.child(FirebaseConstants.MESSAGES_COLLECTION_KEY+"/$roomName/$CHAT_COLLECTION/${message.messageId}")
            .updateChildren(HashMap<String,Any>().apply { put("readStatus",true) })
    }

    private fun insertIntoCollection(collection:String,collectionIbject: MessageParams,childKey:String){
        Log.i("sendMessage","insertIntoCollection")

        val ref=databaseReference.child(collection).child(childKey)

        ref.setValue(collectionIbject).addOnFailureListener {
           Log.i("sendMessage",it.message)
           it.printStackTrace()
       }.addOnCompleteListener {
           Log.i("sendMessage C",it.isComplete.toString())
           Log.i("sendMessage S",it.isSuccessful.toString())


       }.addOnCanceledListener {
           Log.i("sendMessage","addOnCanceledListener")

       }.addOnSuccessListener {
            collectionIbject.sendStatus = true
            ref.updateChildren(collectionIbject.toMap()).addOnCompleteListener {
                Log.i("MessageupdateChildren","addOnCompleteListener")

            }
           Log.i("sendMessage","addOnSuccessListener")

       }


    }

    fun getRooms(){
        databaseReference.child(FirebaseConstants.MESSAGES_COLLECTION_KEY).addChildEventListener(object :ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
                p0.toException().printStackTrace()
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                Log.i("onChildChanged",p0.toString())
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
               var  arrayList = ArrayList<String>()
                p0.child("users").hasChild("id1")
                var  inRoom=p0.child("users").hasChild("id1")
                Log.i("onChildAdded in users",p0.child("users").ref.toString())
                if (inRoom){
                    Log.i("onChildAdded chat ",p0.key)
                }else{
                    Log.i("onChildAdded","Didnt found here")

                }


            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        })


        databaseReference.child(FirebaseConstants.MESSAGES_COLLECTION_USER_ROOMS+"/id1").
            addChildEventListener(object :ChildEventListener{
                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    Log.i("onChildAdded Room ",p0.value.toString())
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                }

                override fun onCancelled(p0: DatabaseError) {
                p0.toException().printStackTrace()
            }


        })
    }
}