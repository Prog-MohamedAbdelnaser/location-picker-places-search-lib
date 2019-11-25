package com.softtech.android_structure.features.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softtech.android_structure.R
import com.softtech.android_structure.domain.entities.Room
import kotlinx.android.synthetic.main.chat_room_item.view.*

class NormalAdapter(var list: ArrayList<Room>): RecyclerView.Adapter<NormalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.chat_room_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text= list[position].friend?.name ?: "null"
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName =view.tvUserName
    }
}