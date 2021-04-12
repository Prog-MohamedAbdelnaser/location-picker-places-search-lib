package com.softartch_lib.component.widget

import android.content.Context
import android.graphics.Typeface
import android.text.InputType
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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


class AutoCompletePlacesEditText(context: Context?, attrs: AttributeSet?) : LinearLayout(
    context,
    attrs
),
    PlacesSearchResultAdapter.ClickPlaceItemListener {

    interface AutoCompleteSearchViewListener{
        fun onSearchResult(place: Place)
    }

    private var isEnableSearch: Boolean =true
    val token = AutocompleteSessionToken.newInstance()
    private var localizationFillter :String =""
    private var recyclerViewResults:RecyclerView? = null
    private var searchView:EditText? = null
    private var editTextParentView:View? = null
    var  placeHolderNoSearchResultMessage : String  = "No Result"
    private var tvPlaceHolderMessage:TextView?=null
    private var progressBar:ProgressBar?=null
    var placesClient: PlacesClient?=null
    private var placesSearchResultAdapter : PlacesSearchResultAdapter?=null
    private var mAutoCompleteSearchViewListener :AutoCompleteSearchViewListener ?=null

    fun setAutoCompleteSearchViewListener(autoCompleteSearchViewListener: AutoCompleteSearchViewListener){
        mAutoCompleteSearchViewListener=autoCompleteSearchViewListener
    }
    
    init {

        orientation=VERTICAL
        searchView =createSearchViewLayout(this.context)
        addView(editTextParentView)
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

    private fun initCustom(editText: EditText){
        this.removeAllViews()
         orientation=VERTICAL
            searchView =editText

        addView(editTextParentView)
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

    fun initComponent(apiKey: String,@LayoutRes editTextLayout: Int, editTextID: Int): AutoCompletePlacesEditText {

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

        val factory: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        editTextParentView= factory.inflate(editTextLayout, null)
        val editText = editTextParentView?.findViewById(editTextID) as EditText
        initCustom(editText)
        placesSearchResultAdapter = PlacesSearchResultAdapter(
            this.context,
            localizationFillter ?: ""
        )
        placesSearchResultAdapter!!.setClickListener(this)
        initSearchViewRecyclerView()
        initSearchQueryListener()

        return this
    }

    fun initComponent(apiKey: String,editText: EditText): AutoCompletePlacesEditText {

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
        editTextParentView=editText
        initCustom(editText)
        placesSearchResultAdapter = PlacesSearchResultAdapter(
            this.context,
            localizationFillter ?: ""
        )
        placesSearchResultAdapter!!.setClickListener(this)
        initSearchViewRecyclerView()
        initSearchQueryListener()

        return this
    }


    fun initComponent(apiKey: String,@LayoutRes editTextLayout: Int): AutoCompletePlacesEditText {

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

        val factory: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        editTextParentView = factory.inflate(editTextLayout, null)
        val editText = editTextParentView as EditText
        initCustom(editText)
        placesSearchResultAdapter = PlacesSearchResultAdapter(
            this.context,
            localizationFillter ?: ""
        )
        placesSearchResultAdapter!!.setClickListener(this)
        initSearchViewRecyclerView()
        initSearchQueryListener()

        return this
    }

    fun initComponent(apiKey: String): AutoCompletePlacesEditText {

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

        placesSearchResultAdapter = PlacesSearchResultAdapter(
            this.context,
            localizationFillter ?: ""
        )
        placesSearchResultAdapter!!.setClickListener(this)
        initSearchViewRecyclerView()
        initSearchQueryListener()
        return this
    }

    fun setLocalizationFilter(localizationName: String): AutoCompletePlacesEditText {
        localizationFillter = localizationName
        return this
    }


    private fun getProgressBarLayout(context: Context?):ProgressBar{
        val progressBar = ProgressBar(context)
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, 60)
        progressBar.layoutParams = lp
        return progressBar
    }

    private fun getTextViewLayout(context: Context?):TextView{
        val textView = TextView(context)
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        textView.layoutParams = lp
        return textView
    }

   private fun createSearchViewLayout(context: Context?):EditText{
        val searchView = EditText(context)
       editTextParentView =searchView
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

    fun <I : RecyclerView.ViewHolder> setAdapter(adapter: RecyclerView.Adapter<I>){
        recyclerViewResults?.adapter=adapter
    }

    private fun getRecycleViewResults()=recyclerViewResults

    fun getSearchView()=searchView

    private fun getProgressBar()=progressBar

    private fun getTextViewPlaceHolder()=tvPlaceHolderMessage

    private fun initSearchQueryListener(){


     /*   val mSearchListener = TextView.OnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                return@OnEditorActionListener true
            }
            false
        }*/
        searchView?.doOnTextChanged { text, start, count, after ->
            if (isEnableSearch) {
                if (!searchView?.text.isNullOrEmpty()) {
                    searchQueryListener(searchView?.text.toString())
                } else {
                    getRecycleViewResults()!!.hide()
                }
            }else{
                isEnableSearch =true
            }
        }
        searchView?.inputType=InputType.TYPE_CLASS_TEXT
       // searchView?.setOnEditorActionListener(mSearchListener)

    }

    fun closeSearch(){
        getRecycleViewResults()!!.hide()
        getTextViewPlaceHolder()!!.hide()
        placesSearchResultAdapter?.clearItems()
    }

    private fun searchQueryListener(newText: String) {
        getPredictions(newText, localizationFillter)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {onAutoCompleteSearchStart() }
            .doFinally {  getProgressBar()!!.hide()}
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

    private fun onAutoCompleteSearchFailure(exception: Throwable) {
        exception.printStackTrace()
        getRecycleViewResults()!!.hide()
        placesSearchResultAdapter?.clearItems()
        getProgressBar()!!.hide()
        getTextViewPlaceHolder()!!.show()
        getTextViewPlaceHolder()!!.text=exception.message
    }

    private fun onAutoCompleteSearchFinised(resultIsNotEmpty: Boolean) {
        getRecycleViewResults()!!.show()
        getProgressBar()!!.hide()
        if (!resultIsNotEmpty){
            getRecycleViewResults()!!.hide()
            getTextViewPlaceHolder()!!.show()
            placesSearchResultAdapter?.clearItems()
            getTextViewPlaceHolder()!!.text = placeHolderNoSearchResultMessage
        }else{
            getTextViewPlaceHolder()!!.hide()
        }
    }

    private fun onAutoCompleteSearchStart() {
        getRecycleViewResults()!!.show()
        getProgressBar()!!.show()
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
                    println("ON EMPTY LIST SEARCH")
                }

            }.addOnFailureListener {
                emitter.onError(it)
            }

        }
    }

    override fun clickPickedPlace(place: Place) {
        mAutoCompleteSearchViewListener?.onSearchResult(place)
    }

    fun setText(text:String,search:Boolean){
        isEnableSearch = search
        searchView?.setText(text)
    }


    fun setText(text:String){
        isEnableSearch = false
        searchView?.setText(text)
    }


    override fun clickPickedPlace(locationName: String) {
        getRecycleViewResults()!!.hide()
        getProgressBar()!!.hide()
        getTextViewPlaceHolder()!!.hide()
        getSearchView()?.clearFocus()
    }

}