package com.softtech.android_structure.features.chat.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.softtech.android_structure.domain.entities.Room
import com.softtech.android_structure.domain.firbase.FirebaseConstants
import com.softtech.android_structure.features.chat.activities.ChatActivity
import com.softtech.android_structure.features.common.ChatRoomStates
import com.softtech.android_structure.features.common.CommonState
import io.reactivex.Single

class RoomViewModel : ViewModel() {

    private val roomLiveData = MutableLiveData<CommonState<ChatRoomStates>>()

/*
    private fun getRooms():Single<CommonState<ChatRoomStates>> {
        FirebaseDatabase.getInstance().getReference(FirebaseConstants.USER_ROOMS_COLLECTION+"/${ChatActivity.uid}").
            addChildEventListener(object : ChildEventListener {
                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                }
                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                }
                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    Log.i("onChildAdded getRooms",p0.toString())
                    val member=p0.child(FirebaseConstants.MEMEBERS_COLLECTION).children.elementAt(0).value.toString()
                    val room=   Room(roomId = p0.key.toString())

                    roomLiveData.value=CommonState.Success(ChatRoomStates.AddNewRoom(room))

                    *//* getFriendInfo(member,room)
                    getRoomLastMessage(room)*//*

                }

                override fun onChildRemoved(p0: DataSnapshot) {

                }
                override fun onCancelled(p0: DatabaseError) {
                    roomLiveData.value=CommonState.Error(p0.toException())
                }

            })
    }*/
    fun setRoom(){}
}