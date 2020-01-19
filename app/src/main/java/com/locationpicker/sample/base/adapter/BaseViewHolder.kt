package com.locationpicker.sample.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<I>(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    protected var item: I? = null
    protected var position:Int?=null
    protected var isLast:Boolean=false

    fun onBind(item: I, position: Int, isLast: Boolean) {
        this.item = item ?: throw IllegalArgumentException("Item can't be null")
        this.position=position
        this.isLast=isLast
        itemView.setOnClickListener(this)
        fillData()
    }

    protected abstract fun fillData()

    override fun onClick(v: View?) {

    }
}