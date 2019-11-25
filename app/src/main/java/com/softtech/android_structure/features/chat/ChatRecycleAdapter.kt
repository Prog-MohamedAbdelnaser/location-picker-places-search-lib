package com.softtech.android_structure.features.chat

import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.softtech.android_structure.R
import com.softtech.android_structure.base.adapter.BaseAdapter
import com.softtech.android_structure.base.adapter.BaseViewHolder
import com.softtech.android_structure.domain.entities.ChatMessage
import com.softtech.android_structure.domain.entities.MessageParams
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.layout_receiver.view.*
import kotlinx.android.synthetic.main.layout_sender.view.*


class ChatRecycleAdapter :BaseAdapter<ChatMessage>(){
    private val callClickSubject = PublishSubject.create<String>()
    private var currentPosition:Int=0
    val isLastPosition :Boolean
        get() = getLastPosition()==currentPosition

    companion object{
        const val  SENDER_VIEW =1
        const val  RECEIVER_VIEW =2

    }
    fun getPosition()=currentPosition
    override fun getItemViewType(position: Int): Int {
        return if (getItems()[position].type == SENDER_VIEW) SENDER_VIEW else RECEIVER_VIEW
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ChatMessage> {
        return if (viewType == SENDER_VIEW) ChatViewHolder( getItemView(parent, R.layout.layout_sender))
        else  ChatViewHolder(getItemView(parent,R.layout.layout_receiver))
    }

    fun resort(){

        getItems(). sortedWith(compareBy { it.messageId })
        notifyDataSetChanged()
        for (item in getItems()){
            Log.w("Resort",item.text.toString())
        }
    }


    inner class ChatViewHolder(view: View):BaseViewHolder<ChatMessage>(view){

        override fun fillData() {
            currentPosition = adapterPosition
            Log.w("onScrollStateChanged",item.toString())
            itemView.apply {
                this.textViewSender?.apply {
                    text = item?.text
                    if (item?.sendStatus!!){
                        setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_done,0,0,0)
                    }else{
                        setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_history,0,0,0)
                    }

                   item?.let {
                       if (it.readStatus){
                           this.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_done_all,0,0,0)
                       }
                   }
                }
                this.textViewReceiver?.text = item?.text

            }
        }
    }


}