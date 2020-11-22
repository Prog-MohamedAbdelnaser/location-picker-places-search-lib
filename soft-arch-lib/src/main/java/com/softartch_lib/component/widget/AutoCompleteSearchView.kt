package com.softartch_lib.component.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.*
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softartch_lib.component.extension.hide


class AutoCompleteSearchView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private var recyclerViewResults:RecyclerView? = null
    private var searchView:SearchView? = null
    private var tvPlaceHolderMessage:TextView?=null
    private var progressBar:ProgressBar?=null

    init {
        orientation=VERTICAL
        searchView =getSearchViewLayout(this.context)
        addView(searchView)
        recyclerViewResults =getRecycleViewLayout(this.context)
        recyclerViewResults?.layoutManager = LinearLayoutManager(this.context)
        addView(recyclerViewResults)

        tvPlaceHolderMessage=getTextViewLayout(context)
        addView(tvPlaceHolderMessage)

        progressBar=getProgressBarLayout(context)
        addView(progressBar)

        progressBar!!.hide()
        tvPlaceHolderMessage!!.hide()
    }



    private fun getProgressBarLayout(context: Context?):ProgressBar{
        val progressBar = ProgressBar(context)
        val lp = LayoutParams(LayoutParams.MATCH_PARENT,60)
        progressBar.layoutParams = lp
        return progressBar
    }

    private fun getTextViewLayout(context: Context?):TextView{
        val textView = TextView(context)
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        textView.layoutParams = lp
        return textView
    }

   private fun getSearchViewLayout(context: Context?):SearchView{
        val searchView = SearchView(context)
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
       searchView.layoutParams = lp
        return searchView
    }

   private fun getRecycleViewLayout(context: Context):RecyclerView{
        val recyclerView = RecyclerView(context)
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
       recyclerView.layoutParams = lp
        return recyclerView
    }

    fun <I : RecyclerView.ViewHolder> setAdapter(adapter : RecyclerView.Adapter<I>){
        recyclerViewResults?.adapter=adapter
    }

    fun getRecycleViewResults()=recyclerViewResults

    fun getSearchView()=searchView

    fun getProgressBar()=progressBar

    fun getTextViewPlaceHolder()=tvPlaceHolderMessage


}