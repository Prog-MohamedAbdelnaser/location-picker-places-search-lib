package com.softtech.android_structure.features.chat

import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.softartch_lib.utility.TimeConvertor
import com.softtech.android_structure.R
import com.softtech.android_structure.base.adapter.BaseAdapter
import com.softtech.android_structure.base.adapter.BaseViewHolder
import com.softtech.android_structure.domain.entities.Room
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.chat_room_item.view.*

class RoomRecycleAdapter :BaseAdapter<Room>(){

    private val callClickSubject = PublishSubject.create<Room>()
    fun callClickedObservable(): Observable<Room> = callClickSubject

    private var currentPosition:Int=0
    val isLastPosition :Boolean
        get() = getLastPosition()==currentPosition

    companion object{
        const val  SENDER_VIEW =1
        const val  RECEIVER_VIEW =2

    }
    fun getPosition()=currentPosition

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Room> {
        return  ChatViewHolder( getItemView(parent, R.layout.chat_room_item))
    }

    inner class ChatViewHolder(view: View):BaseViewHolder<Room>(view){

        override fun fillData() {
            currentPosition = adapterPosition
            Log.w("onScrollStateChanged",item.toString())
            itemView.apply {
                tvUserName?.apply {
                   text= item?.friend?.name
                }
                tvLastMessage.text=item?.lastMessage?.text
                item?.lastMessage?.created_at?.let { tvMessageTime.text= TimeConvertor.getOptimizedTimeFromMiliSeconde(it.toLong()) }
                item?.lastMessage?.let {

                    if (it.readStatus){
                        tvMessageTime.textSize=14f
                        tvLastMessage.textSize=14f

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tvMessageTime.setTextColor(context.getColor(R.color.grey))
                            tvLastMessage.setTextColor(context.getColor(R.color.grey))
                        }else{
                            tvMessageTime.setTextColor(context.resources.getColor(R.color.grey))
                            tvLastMessage.setTextColor(context.resources.getColor(R.color.grey))
                        }
                    }else{
                        tvMessageTime.textSize=16f
                        tvLastMessage.textSize=16f
                        tvUserName.textSize=18f

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tvMessageTime.setTextColor(context.getColor(R.color.black))
                            tvLastMessage.setTextColor(context.getColor(R.color.black))
                        }else{
                            tvMessageTime.setTextColor(context.resources.getColor(R.color.black))
                            tvLastMessage.setTextColor(context.resources.getColor(R.color.black))
                        }
                    }
                }
            }
        }

        override fun onClick(v: View?) {
            super.onClick(v)
            callClickSubject.onNext(item!!)
        }
    }


}