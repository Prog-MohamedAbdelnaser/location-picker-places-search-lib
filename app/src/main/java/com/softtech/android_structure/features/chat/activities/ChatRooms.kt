package com.softtech.android_structure.features.chat.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.softtech.android_structure.R
import com.softtech.android_structure.base.utility.EncryptUtils
import com.softtech.android_structure.domain.entities.ChatMessage
import com.softtech.android_structure.domain.entities.Room
import com.softtech.android_structure.domain.entities.User
import com.softtech.android_structure.domain.firbase.FireBaseMessageUseCase
import com.softtech.android_structure.domain.firbase.FirebaseConstants
import com.softtech.android_structure.domain.firbase.FirebaseConstants.MEMEBERS_COLLECTION
import com.softtech.android_structure.features.FeatureConstants
import com.softtech.android_structure.features.chat.RoomRecycleAdapter
import com.softtech.android_structure.features.common.showErrorSnackbar
import com.softtech.android_structure.features.common.showSnackbar
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_chat_rooms.*
import org.koin.android.ext.android.inject
import java.util.*
import kotlin.collections.ArrayList
import io.reactivex.subjects.PublishSubject


class ChatRooms : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    val listMessegs: ArrayList<ChatMessage> = ArrayList()
    private val fireBaseMessageUseCase:FireBaseMessageUseCase by inject()
    private val roomRecycleAdapter: RoomRecycleAdapter by lazy { RoomRecycleAdapter() }
    private val databaseRef:DatabaseReference by inject()
    private val firebaseDatabase:FirebaseDatabase = FirebaseDatabase.getInstance()

    val livedata=MutableLiveData<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_rooms)
        setSupportActionBar(toolbar)
        tvTitle.text=ChatActivity.uid
        initChatRecycleView()
        initObservers()
        initEventHandler()
        getRooms()



    }


    private fun initEventHandler() {
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()){
                    getFriend(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        searchView.setOnCloseListener(object :SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                searchView.visibility=View.GONE
                return true
            }
        })

        btnNewChat.setOnClickListener {
            searchView.visibility=View.VISIBLE
        }
    }

    private fun getFriend(query: String) {

        FirebaseDatabase.getInstance().
            getReference(FirebaseConstants.USERS_COLLECTION_KEY).orderByChild("mail").equalTo(query)
            .addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                p0.toException().printStackTrace()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("onSearchFind", " ${snapshot.value}")
                if (snapshot.hasChildren()) {
                    val friend = snapshot.children.elementAt(0).getValue(User::class.java)
                    Log.i("onSearchFind F", " ${snapshot.key} : ${friend}")
                    val indexRoom = roomRecycleAdapter.getItems()
                        .indexOfFirst { room -> room.friend?.id == friend?.id }
                    Log.i("onDataChangeFindROOm", "${indexRoom}")

                    if (indexRoom < 0) {
                        addChatRoomToDatabase(friend)
                    } else {
                        navigateToChatScreen(roomRecycleAdapter.getItems()[indexRoom])
                    }
                }else{
                    showSnackbar(toolbar,getString(R.string.not_found))
                }
            }
        })
    }

    private fun initObservers() {
        compositeDisposable.add(roomRecycleAdapter.callClickedObservable()
            .subscribeOn(Schedulers.io()).
            observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                navigateToChatScreen(it)
            },{it.printStackTrace()})
        )
    }

    fun navigateToChatScreen(room: Room){
        val intent =Intent(this,ChatActivity::class.java)
        intent.putExtra(FeatureConstants.KEY_ROOM,room)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun initChatRecycleView() {
        recyclerRooms.apply {
            adapter = roomRecycleAdapter
            setHasFixedSize(true)
            verticalScrollbarPosition = View.SCROLL_INDICATOR_START
            layoutManager = object : LinearLayoutManager(context) {
                override fun isAutoMeasureEnabled(): Boolean {
                    return false
                }
            }

        }
    }

    private fun getRooms() {
        FirebaseDatabase.getInstance().getReference(FirebaseConstants.USER_ROOMS_COLLECTION+"/${ChatActivity.uid}").
            addChildEventListener(object :ChildEventListener{
                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    Log.i("onChildAdded getRooms",p0.toString())
                    val member=p0.child(MEMEBERS_COLLECTION).children.elementAt(0).value.toString()
                    val room=   Room(roomId = p0.key.toString())
                    roomRecycleAdapter.addItem(room)
                    getFriendInfo(member,room)
                    getRoomLastMessage(room)


                }

                override fun onChildRemoved(p0: DataSnapshot) {
                }

                override fun onCancelled(p0: DatabaseError) {

            }

        })
    }

    private fun getRoomLastMessage(room: Room) {
        Log.i("onChildAdded getLM",room.toString())


        FirebaseDatabase.getInstance().
            getReference(FirebaseConstants.MESSAGES_COLLECTION_KEY+"/${room.roomId}/chat").orderByKey().limitToLast(1).
            addChildEventListener(object :ChildEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
                    Log.i("onChildAdded c", "${snapshot?.value.toString()}")

                }

                override fun onChildRemoved(p0: DataSnapshot) {

                }
            })

        FirebaseDatabase.getInstance().
            getReference(FirebaseConstants.MESSAGES_COLLECTION_KEY+"/${room.roomId}/chat").orderByKey().limitToLast(1)
            .addValueEventListener(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        val message: ChatMessage? =
                            snapshot.children?.elementAt(0)?.getValue(ChatMessage::class.java)
                        message?.apply {
                            this.type = if (senderId.equals(ChatActivity.uid)) 1 else 2
                        }
                        Log.i("onDataChange c", "${snapshot?.value.toString()}")

                        room.lastMessage = message

                        val roomIndex =
                            roomRecycleAdapter.getItems().indexOfFirst { it.roomId == room.roomId }


                        Log.i("onDataChange roomIndex", "${roomIndex}")
                           roomRecycleAdapter.updateItem(room, roomIndex)

                        val sorted=    roomRecycleAdapter.getEditableList().sortedByDescending { it.lastMessage?.created_at?.toLong() }
                        sorted.forEach {
                            Log.w("RoomResorted", "Room ${it.roomId} : ${it.friend?.name} At ${it.lastMessage?.created_at.toString()}")
                        }

                        roomRecycleAdapter.updateItems(  sorted)


                    }
                }
            })

    }

    fun getFriendInfo(id:String,room: Room){

        FirebaseDatabase.getInstance().
            getReference(FirebaseConstants.USERS_COLLECTION_KEY+"/$id").addValueEventListener(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    p0.toException().printStackTrace()
                }

                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.hasChildren()) {
                        Log.i("onDataChangeFriend", "${snapshot?.value.toString()}")
                        val friend = snapshot.getValue(User::class.java)

                          val roomIndex = roomRecycleAdapter.getItems().indexOfFirst { it.roomId == room.roomId }

                          Log.i("onDataChangeFriendIndex", "$friend : ${roomIndex}")
                              room.friend=friend
                              val oldRoom=roomRecycleAdapter.getItems()[roomIndex]
                              oldRoom.friend=friend
                              roomRecycleAdapter.updateItem(oldRoom, roomIndex)


                    }
                }
            })
    }

    private fun addChatRoomToDatabase(friend: User?) {


        val roomId="${EncryptUtils.getRandomString()}@${ChatActivity.uid}"
        Log.i("newRoomId",roomId)
        val roomRef= firebaseDatabase.getReference(FirebaseConstants.USER_ROOMS_COLLECTION)

        roomRef.child("${ChatActivity.uid}/$roomId")
            .child("${FirebaseConstants.MEMEBERS_COLLECTION}/${friend?.id}")
            .setValue(friend?.id).addOnCompleteListener {
                if (it.isSuccessful){

                    roomRef.child("${friend?.id}/$roomId")
                        .child("${FirebaseConstants.MEMEBERS_COLLECTION}/${ChatActivity.uid}")
                        .setValue(ChatActivity.uid).addOnCompleteListener {
                            if (it.isSuccessful){
                                navigateToChatScreen(Room(roomId=roomId,friend = friend))
                            }else{
                                it.exception?.message?.let { it1 -> showErrorSnackbar(toolbar, it1) }
                            }
                        }
                }else{
                    it.exception?.message?.let { it1 -> showErrorSnackbar(toolbar, it1) }
                }

            }
    }




}
