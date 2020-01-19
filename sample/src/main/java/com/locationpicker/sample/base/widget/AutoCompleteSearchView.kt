package com.locationpicker.sample.base.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView


class AutoCompleteSearchView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private var recyclerViewResults:RecyclerView? = null
    private var searchView:SearchView? = null

    init {
        orientation=VERTICAL
   /*     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setBackgroundColor(this.context.getColor(android.R.color.transparent))
        }else{
            setBackgroundColor(this.context.resources.getColor(android.R.color.transparent))

        }*/
        searchView =getSearchViewLayout(this.context)
        addView(searchView)
        recyclerViewResults =getRecycleViewLayout(this.context)
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

    fun getRecycleViewResults()=recyclerViewResults
    fun getSearchView()=searchView

}