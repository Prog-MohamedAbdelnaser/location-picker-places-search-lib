package com.softartch_lib.component.widget

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Tasks
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.softartch_lib.R
import com.softartch_lib.component.extension.hide
import com.softartch_lib.component.extension.show
import com.softartch_lib.exceptions.ApiKeyRequiredException
import com.softartch_lib.locationpicker.PlaceAutoComplete
import com.softartch_lib.locationpicker.PlacesSearchResultAdapter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.w3c.dom.Text
import timber.log.Timber
import java.util.concurrent.TimeUnit


class AutoCompleteSearchView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs),
    PlacesSearchResultAdapter.ClickPlaceItemListener {

    interface AutoCompleteSearchViewListener{
        fun onSearchResult(place: Place)
    }
    val token = AutocompleteSessionToken.newInstance()
    private var localizationFillter :String =""
    private var recyclerViewResults:RecyclerView? = null
    private var searchView:SearchView? = null
    private var tvPlaceHolderMessage:TextView?=null
    private var progressBar:ProgressBar?=null
    var placesClient: PlacesClient?=null
    private var placesSearchResultAdapter : PlacesSearchResultAdapter?=null
    private var mAutoCompleteSearchViewListener :AutoCompleteSearchViewListener ?=null

    fun setAutoCompleteSearchViewListener(autoCompleteSearchViewListener:AutoCompleteSearchViewListener){
        mAutoCompleteSearchViewListener=autoCompleteSearchViewListener
    }

    init {


        orientation=VERTICAL
        searchView =createSearchViewLayout(this.context)
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

    fun initComponent(apiKey:String): AutoCompleteSearchView {

        try {
            if (!Places.isInitialized()) {
                Places.initialize(this.context, apiKey)
            }

        }catch (e:Exception){
            e.printStackTrace()
        }

        try {
            placesClient = Places.createClient(this.context!!)
        }catch (e:Exception){
            e.printStackTrace()
        }

        placesSearchResultAdapter = PlacesSearchResultAdapter(this.context,localizationFillter?:"")
        placesSearchResultAdapter!!.setClickListener(this)
        initSearchViewRecyclerView()
        initSearchQueryListener()
        return this
    }

    fun setLocalizationFilter(localizationName: String): AutoCompleteSearchView {
        localizationFillter = localizationName
        return this
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

   private fun createSearchViewLayout(context: Context?):SearchView{
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

    fun initCustomSearchView(searchView: SearchView){
        this.searchView =searchView
    }

    private fun initSearchQueryListener(){
        getSearchView()?.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty().not()) {
                    searchQueryListener(newText!!)
                }else{
                    getRecycleViewResults()!!.hide()
                }
                return false
            }
        })

        getSearchView()?.setOnCloseListener {
            getRecycleViewResults()!!.hide()
            getTextViewPlaceHolder()!!.hide()
            false
        }
    }

    private fun searchQueryListener(newText: String) {
        getPredictions(newText,localizationFillter)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {onAutoCompleteSearchStart() }
            .doFinally {  getProgressBar()!!.hide()}
            .doOnError{onAutoCompleteSearchFailure(it)}
            .doOnSuccess {data->
                onAutoCompleteSearchFinised(data.size>0)
                placesSearchResultAdapter?.setResultList(data)
                placesSearchResultAdapter?.notifyDataSetChanged()
            }.subscribe()
    }

   private fun initSearchViewRecyclerView(){
        val adapter=placesSearchResultAdapter
        adapter?.let {setAdapter(it) }
    }

    open fun onAutoCompleteSearchFailure(exception: Throwable) {
        exception.printStackTrace()
        getRecycleViewResults()!!.hide()
        getProgressBar()!!.hide()
        getTextViewPlaceHolder()!!.show()
        getTextViewPlaceHolder()!!.text=exception.message
    }

    open fun onAutoCompleteSearchFinised(resultIsNotEmpty: Boolean) {
        getRecycleViewResults()!!.show()
        getProgressBar()!!.hide()
        if (resultIsNotEmpty.not()){
            getRecycleViewResults()!!.hide()
            getTextViewPlaceHolder()!!.show()
            getTextViewPlaceHolder()!!.text = "No Result"
        }else{
            placesSearchResultAdapter?.clearItems()

        }
    }

    open fun onAutoCompleteSearchStart() {
        getRecycleViewResults()!!.show()
        getProgressBar()!!.show()
    }

    private  fun getPredictions(constraint: CharSequence,localizationFillter:String):
            Single<ArrayList<PlaceAutoComplete>> {

        return Single.create<ArrayList<PlaceAutoComplete>> { emitter->

            if (placesClient==null){
                emitter.onError(ApiKeyRequiredException(message = "ApiKeyRequiredException"))
                return@create
            }
            val STYLE_NORMAL = StyleSpan(Typeface.NORMAL)
            val STYLE_BOLD = StyleSpan(Typeface.BOLD)

            val resultList = ArrayList<PlaceAutoComplete>()

            val request = FindAutocompletePredictionsRequest.builder()
                .setSessionToken(token)
                .setQuery(constraint.toString())
                .setCountry(localizationFillter)
                .setTypeFilter(TypeFilter.ADDRESS)
                .build()


            val autoCompletePredictions = placesClient?.findAutocompletePredictions(request)

            Tasks.await(autoCompletePredictions!!, 60, TimeUnit.SECONDS)

            autoCompletePredictions.addOnSuccessListener {

                if (it.autocompletePredictions.isNullOrEmpty().not()){

                    it.autocompletePredictions.iterator().forEach { it ->
                        Log.i("getPredictions","getPredictions ${it.toString()}")
                        resultList.add(
                            PlaceAutoComplete(
                            it.placeId,
                            it.getPrimaryText(STYLE_NORMAL).toString(),
                            it.getFullText(STYLE_BOLD).toString())
                        )
                    }
                    emitter.onSuccess(resultList)
                }else{
                    onAutoCompleteSearchFinised(false)
                    println("ON EMPTY LIST SEARCH")
                }

            }.addOnFailureListener {
                emitter.onError(it)
            }

        }
    }

    override fun clickPickedPlace(place: Place) {

    }


    override fun clickPickedPlace(locationName: String) {
        Timber.i("clickPickedPlace${locationName}")
        getRecycleViewResults()!!.hide()
        getProgressBar()!!.hide()
        getTextViewPlaceHolder()!!.hide()
        getSearchView()?.clearFocus()
    }

    fun setText(text:String){
        getSearchView()?.setQuery(text, false);
    }


}