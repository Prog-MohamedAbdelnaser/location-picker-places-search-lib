package com.locationpicker.sample.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<I>(items: ArrayList<I>? = null, @LayoutRes private var itemLayoutRes: Int?=null) : RecyclerView.Adapter<com.locationpicker.sample.base.adapter.BaseViewHolder<I>>() {

    private val items: MutableList<I> = items ?: ArrayList()

    override fun onBindViewHolder(holder: com.locationpicker.sample.base.adapter.BaseViewHolder<I>, position: Int) {
        val isLast:Boolean=position.equals(items.size-1)
        holder.onBind(getItem(position),position,isLast)

    }

    override fun getItemCount(): Int {
        return items.size
    }


    private fun getItem(position: Int): I {
        return items[position]
    }

    fun updateItems(newItems: List<I>) {
        items.clear()
        if (newItems.isNullOrEmpty().not()) {
            items.addAll(newItems)
        }

        notifyDataSetChanged()
    }



    fun getEditableList(): MutableList<I> {
        return items
    }
    fun getLastPosition():Int{
        return items.size-1
    }
    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

    fun addItem(item: I) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addFirstItem(item: I) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItems(newItems: List<I>) {
        val start = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(start, newItems.size - 1)
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun removeItem(item: I) {
        val position = items.indexOf(item)
        if (position >= 0) {
            removeItem(position)
        }
    }

    fun updateItem(item: I) {
        val position = items.indexOf(item)
        if (position >= 0) {
            notifyItemChanged(position)
        }
    }

    fun updateItem(item: I,position: Int) {
        items[position] = item
        if (position >= 0) {
            notifyItemChanged(position)
        }
    }
    fun removeWithoutNotify(position: Int): I {
        return items.removeAt(position)
    }

    fun addWithoutNotify(position: Int, item: I) {
        items.add(position, item)
    }

    fun getItems(): List<I> {
        return items
    }



    protected fun getItemView(parent: ViewGroup): View =
            LayoutInflater.from(parent.context).inflate(itemLayoutRes!!, parent, false)

    protected fun getItemView(parent: ViewGroup, @LayoutRes itemLayoutRes: Int): View =
            LayoutInflater.from(parent.context).inflate(itemLayoutRes, parent, false)

}