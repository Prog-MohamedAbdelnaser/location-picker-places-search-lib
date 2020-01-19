package com.locationpicker.sample.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ChildBaseViewHolder<I,P>(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    protected var item: I? = null
    protected var parentAdapterPosition:P? = null
    protected var isLast:Boolean=false
    protected var position:Int?=null

    fun onBind(item: I,parent:P, isLast: Boolean,position: Int) {
        this.isLast=isLast
        this.item = item ?: throw IllegalArgumentException("Item can't be null")
        this.parentAdapterPosition = parent ?: throw IllegalArgumentException("parent can't be null")
        this.position=position?: throw IllegalArgumentException("Item can't be null")
        itemView.setOnClickListener(this)
        fillData()
    }

    protected abstract fun fillData()

    override fun onClick(v: View?) {

    }
}