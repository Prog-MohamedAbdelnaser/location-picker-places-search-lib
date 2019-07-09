package com.softtech.android_structure.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class ChildBaseAdapter<I,P>(items: ArrayList<I>? = null,parent:P?=null, @LayoutRes private var itemLayoutRes: Int) : RecyclerView.Adapter<ChildBaseViewHolder<I, P>>() {

    private val items: MutableList<I> = items ?: ArrayList()
    private var parentAdapterPosition:P?=parent
    override fun onBindViewHolder(holder: ChildBaseViewHolder<I, P>, position: Int) {
        val isLast:Boolean=position.equals(items.size-1)
        holder.onBind(getItem(position), this.getParent()!!,isLast,position)
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


    fun getParent(): P? =parentAdapterPosition

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

    fun addItem(item: I) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItems(newItems: List<I>,parent:P?) {
        val start = items.size
        items.addAll(newItems)
        this.parentAdapterPosition=parent
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
            LayoutInflater.from(parent.context).inflate(itemLayoutRes, parent, false)

    protected fun getItemView(parent: ViewGroup, @LayoutRes itemLayoutRes: Int): View? =
            LayoutInflater.from(parent.context).inflate(itemLayoutRes, parent, false)

}