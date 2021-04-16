package com.softartch_lib.component.widget

import android.app.ActionBar
import android.content.Context
import android.graphics.Typeface
import android.text.InputType
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import androidx.core.widget.doOnTextChanged
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
import java.util.concurrent.TimeUnit


class AutoCompletePlacesController(val context: Context) :
    PlacesSearchResultAdapter.ClickPlaceItemListener {

    interface AutoCompletePlacesControllerListener{
        fun onSearchNoResult()
        fun onFailure(exception: Throwable)
        fun onSearchStart()
        fun onSearchFinished()
        fun onSearchResult(place: Place)
    }

    val token = AutocompleteSessionToken.newInstance()
    private var localizationFillter :String =""
    private var recyclerViewResults:RecyclerView? = null
    private var searchView:EditText? = null
    var placesClient: PlacesClient?=null
    private var placesSearchResultAdapter : PlacesSearchResultAdapter?=null
    private var mAutoCompleteSearchViewListener :AutoCompletePlacesControllerListener ?=null

    fun setAutoCompletePlacesControllerListener(autoCompletePlacesControllerListener: AutoCompletePlacesControllerListener){
        mAutoCompleteSearchViewListener=autoCompletePlacesControllerListener
    }



    private fun initUI(editText: EditText,recyclerView: RecyclerView){
         searchView =editText
         recyclerViewResults = recyclerView
        recyclerViewResults?.layoutManager = LinearLayoutManager(this.context)
    }

    fun initComponent(apiKey: String,recyclerView: RecyclerView,editText: EditText): AutoCompletePlacesController {

        try {
            if (!Places.isInitialized()) {
                Places.initialize(this.context, apiKey)
            }

        }catch (e: Exception){
            e.printStackTrace()
        }

        try {
            placesClient = Places.createClient(this.context!!)
        }catch (e: Exception){
            e.printStackTrace()
        }


        initUI(editText,recyclerView)
        placesSearchResultAdapter = PlacesSearchResultAdapter(
            this.context,
            localizationFillter ?: ""
        )
        placesSearchResultAdapter!!.setClickListener(this)
        initSearchViewRecyclerView()
        initSearchQueryListener()

        return this
    }


    fun setLocalizationFilter(localizationName: String): AutoCompletePlacesController {
        localizationFillter = localizationName
        return this
    }




    fun <I : RecyclerView.ViewHolder> setAdapter(adapter: RecyclerView.Adapter<I>){
        recyclerViewResults?.adapter=adapter
    }

    private fun getRecycleViewResults()=recyclerViewResults

    private fun getSearchView()=searchView

    private fun initSearchQueryListener(){

        searchView?.doOnTextChanged { text, start, count, after ->
            if (!searchView?.text.isNullOrEmpty()) {
                searchQueryListener(searchView?.text.toString())
            }else{
                getRecycleViewResults()!!.hide()
            }
        }

    }

    fun closeSearch(){
        getRecycleViewResults()!!.hide()
        placesSearchResultAdapter?.clearItems()
    }

    private fun searchQueryListener(newText: String) {
        getPredictions(newText, "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {onAutoCompleteSearchStart() }
            .doFinally {  mAutoCompleteSearchViewListener?.onSearchFinished()}
            .doOnError{onAutoCompleteSearchFailure(it)}
            .doOnSuccess { data->
                onAutoCompleteSearchFinised(data.size > 0)
                placesSearchResultAdapter?.setResultList(data)
                placesSearchResultAdapter?.notifyDataSetChanged()
            }.subscribe()
    }

   private fun initSearchViewRecyclerView(){
        val adapter=placesSearchResultAdapter
        adapter?.let {setAdapter(it) }
    }

    private  fun onAutoCompleteSearchFailure(exception: Throwable) {
        getRecycleViewResults()!!.hide()
        placesSearchResultAdapter?.clearItems()
        mAutoCompleteSearchViewListener?.onFailure(exception)
    }

    private  fun onAutoCompleteSearchFinised(resultIsNotEmpty: Boolean) {
        getRecycleViewResults()!!.show()
        if (!resultIsNotEmpty){
            getRecycleViewResults()!!.hide()
            placesSearchResultAdapter?.clearItems()
            mAutoCompleteSearchViewListener?.onSearchNoResult()
        }
    }

    private fun onAutoCompleteSearchStart() {
        getRecycleViewResults()!!.show()
        mAutoCompleteSearchViewListener?.onSearchStart()
    }

    private  fun getPredictions(constraint: CharSequence, localizationFillter: String):
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
                        Log.i("getPredictions", "getPredictions ${it.toString()}")
                        resultList.add(
                            PlaceAutoComplete(
                                it.placeId,
                                it.getPrimaryText(STYLE_NORMAL).toString(),
                                it.getFullText(STYLE_BOLD).toString()
                            )
                        )
                    }
                    emitter.onSuccess(resultList)
                }else{
                    onAutoCompleteSearchFinised(false)
                }

            }.addOnFailureListener {
                emitter.onError(it)
            }

        }
    }

    override fun clickPickedPlace(place: Place) {
        mAutoCompleteSearchViewListener?.onSearchResult(place)
    }


    override fun clickPickedPlace(locationName: String) {
        getRecycleViewResults()!!.hide()
        getSearchView()?.clearFocus()
    }

}