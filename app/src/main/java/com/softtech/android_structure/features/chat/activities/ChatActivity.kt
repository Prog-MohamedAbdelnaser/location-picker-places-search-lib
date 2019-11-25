package com.softtech.android_structure.features.chat.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.softtech.android_structure.R
import com.softtech.android_structure.domain.entities.MessageParams
import com.softtech.android_structure.domain.firbase.FireBaseMessageUseCase
import com.softtech.android_structure.domain.firbase.FirebaseConstants
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.softtech.android_structure.domain.entities.ChatMessage
import com.softtech.android_structure.domain.entities.Room
import com.softtech.android_structure.features.FeatureConstants
import com.softtech.android_structure.features.chat.ChatRecycleAdapter
import kotlinx.android.synthetic.main.activity_chat.*


class ChatActivity : AppCompatActivity() {

    companion object {
      const val  uid="id1"
  }

    private var room:Room?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setSupportActionBar(toolbar)
        room = getRoomFromArgs()
        toolbar.title = room?.friend?.name


        /*  val scoresRef = FirebaseDatabase.getInstance().getReference(FirebaseConstants.MESSAGES_COLLECTION_KEY+"/${room?.roomId}/chat")
        scoresRef.keepSynced(true)*/

    }

    @Throws(IllegalArgumentException::class)
    private fun getRoomFromArgs(): Room? {
        return  if (intent.extras !=null && intent.extras!!.containsKey(FeatureConstants.KEY_ROOM)){
            intent.extras?.getParcelable(FeatureConstants.KEY_ROOM)
        }else
            throw IllegalArgumentException("${this.javaClass.simpleName} arguments must contains order params")

    }

}
