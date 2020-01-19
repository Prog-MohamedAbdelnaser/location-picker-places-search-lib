package com.softartch_lib.component.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class AutoCompleteSearchView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private var recyclerViewResults:RecyclerView? = null
    private var searchView:SearchView? = null

    init {
        orientation=VERTICAL
        searchView =getSearchViewLayout(this.context)
        addView(searchView)
        recyclerViewResults =getRecycleViewLayout(this.context)
        recyclerViewResults?.layoutManager = LinearLayoutManager(this.context)
        addView(recyclerViewResults)
    }

   private fun getSearchViewLayout(context: Context?):SearchView{
        val searchView = SearchView(context)
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        searchView.setLayoutParams(lp)
        return searchView
    }

   private fun getRecycleViewLayout(context: Context):RecyclerView{
        val recyclerView = RecyclerView(context)
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        recyclerView.setLayoutParams(lp)
        return recyclerView
    }

    fun <I : RecyclerView.ViewHolder> setAdapter(adapter : RecyclerView.Adapter<I>){
        recyclerViewResults?.adapter=adapter
    }

    fun getRecycleViewResults()=recyclerViewResults

    fun getSearchView()=searchView

}