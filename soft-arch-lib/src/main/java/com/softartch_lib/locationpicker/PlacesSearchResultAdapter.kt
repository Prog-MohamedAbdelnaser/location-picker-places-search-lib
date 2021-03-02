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

class PlacesSearchResultAdapter(val context: Context,var localizationFillter:String) : RecyclerView.Adapter<PlacesSearchResultAdapter.PredictionHolder>(){

    private val STYLE_NORMAL = StyleSpan(Typeface.NORMAL)
    private val STYLE_BOLD = StyleSpan(Typeface.BOLD)
    private lateinit var clickPlaceItemListener: ClickPlaceItemListener
    private var mResultList: ArrayList<PlaceAutoComplete> = ArrayList()

    private var placesClient: PlacesClient?=null
    init {
        try {
            placesClient= Places.createClient(context)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun setResultList(resultList: ArrayList<PlaceAutoComplete>){
        mResultList=resultList
    }

    fun clearItems(){
        mResultList.clear()
        notifyDataSetChanged()
    }

    fun setClickListener(clickPlaceItemListener: ClickPlaceItemListener) {
        this.clickPlaceItemListener = clickPlaceItemListener
    }

    interface ClickPlaceItemListener {
        fun clickPickedPlace(place: Place)
        fun clickPickedPlace(locationName:String)

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

                placesClient?.fetchPlace(request)?.addOnSuccessListener { p0 ->
                        val place = p0!!.place
                        clickPlaceItemListener.clickPickedPlace(place)
                         clickPlaceItemListener. clickPickedPlace(place.address!!)

                    println("addOnSuccessListener")

                }?.addOnFailureListener { p0 ->
                    if (p0 is ApiException) {
                        Log.d("Home Activity ->", p0.message)
                        Toast.makeText(context, "ERROR "+p0.message, Toast.LENGTH_LONG).show()
                    }
                }?.addOnCompleteListener {
                    println("addOnCompleteListener")
                }?.addOnCanceledListener {
                    println("addOnCanceledListener")

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