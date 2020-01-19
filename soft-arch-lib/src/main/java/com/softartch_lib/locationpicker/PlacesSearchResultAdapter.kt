package com.softartch_lib.locationpicker

import android.content.Context
import android.graphics.Typeface
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Tasks
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.softartch_lib.R
import kotlinx.android.synthetic.main.location_item.view.*
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class PlacesSearchResultAdapter(val context: Context,var localizationFillter:String) : RecyclerView.Adapter<PlacesSearchResultAdapter.PredictionHolder>(),Filterable{

    private val STYLE_NORMAL = StyleSpan(Typeface.NORMAL)
    private val STYLE_BOLD = StyleSpan(Typeface.BOLD)
    private lateinit var clickPlaceItemListener: ClickPlaceItemListener
    private var mResultList: ArrayList<PlaceAutoComplete> = ArrayList()

    private val placesClient: PlacesClient = Places.createClient(context)
    val token = AutocompleteSessionToken.newInstance()

    fun setClickListener(clickPlaceItemListener: ClickPlaceItemListener) {
        this.clickPlaceItemListener = clickPlaceItemListener
    }

    interface ClickPlaceItemListener {
        fun onAutoCompleteSearchStart()
        fun onAutoCompleteSearchFinised(resultIsNotEmpty:Boolean)
        fun clickPickedPlace(place: Place)
        fun clickPickedPlace(locationName:String)

    }

    override fun getFilter(): Filter {
        clickPlaceItemListener. onAutoCompleteSearchStart()
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                if (constraint != null) {

                    mResultList = getPredictions(constraint)
                    if (mResultList != null) {
                        results.values = mResultList
                        results.count = mResultList.size
                    }
                }
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                Log.i("inputLocationAddress","getFilter ${mResultList.size>0}")
                clickPlaceItemListener.onAutoCompleteSearchFinised(mResultList.size>0)
                notifyDataSetChanged()
            }

        }
    }

    fun getPredictions(constraint: CharSequence): ArrayList<PlaceAutoComplete> {

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
                    resultList.add(PlaceAutoComplete(
                        it.placeId,
                        it.getPrimaryText(STYLE_NORMAL).toString(),
                        it.getFullText(STYLE_BOLD).toString()))
                }
            }
        }.addOnFailureListener {
            it.message?.let { it1 -> showErrorAlertToUser(it1) }
            it.printStackTrace()
        }
        return resultList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictionHolder {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView = layoutInflater.inflate(R.layout.location_item, parent, false)
        return PredictionHolder(convertView)
    }

    override fun getItemCount(): Int {
        return mResultList.size
    }

    override fun onBindViewHolder(holder: PredictionHolder, position: Int) {
        holder.address.text = mResultList[position].placeFullDescription.toString()
        Toast.makeText(context, mResultList[position].placeName.toString(), Toast.LENGTH_LONG).show()

    }

    fun getItem(position: Int): PlaceAutoComplete {
        return mResultList.get(position)
    }

    inner class PredictionHolder(view: View) : RecyclerView.ViewHolder(view) {

        var address: AppCompatTextView = view.tvItem

        init {
            view.setOnClickListener {

                val item = mResultList[adapterPosition]
                val placeId = item.placeId.toString()
                val placeFields = Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.ADDRESS)
                val request = FetchPlaceRequest.builder(placeId, placeFields).build()
                placesClient.fetchPlace(request).addOnSuccessListener { p0 ->
                        val place = p0!!.place
                        clickPlaceItemListener.clickPickedPlace(place)
                         clickPlaceItemListener. clickPickedPlace(place.address!!)


                }.addOnFailureListener { p0 ->
                    if (p0 is ApiException) {
                        Log.d("Home Activity ->", p0.message)
                        Toast.makeText(context, p0.message, Toast.LENGTH_LONG).show()
                    }
                }

            }
        }

    }


    private fun showErrorAlertToUser(msg: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setMessage(msg)
            .setCancelable(false)
            .setPositiveButton(
                "OK"
            ) { dialog, _ ->
                dialog.cancel()
            }
        val alert = alertDialogBuilder.create()
        alert.show()
    }
}