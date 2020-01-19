package com.locationpicker.sample.base.widget

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class SpinnerAdapter<T>(context: Context, textViewResourceId: Int, objects: List<T>) : ArrayAdapter<T>(context, textViewResourceId, objects) {

    private var defaultTextColors: ColorStateList? = null

    override fun isEnabled(position: Int): Boolean = position > 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)

        if (view is TextView) {
//            view.typeface = FontUtility.getTypeFace(context,context.getString(R.string.font_medium))
         /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.setTextAppearance(R.style.textMediumStyle)
            }else{
                view.setTextAppearance(context, R.style.textMediumStyle)
            }*/
        }

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = super.getDropDownView(position, convertView, parent)

        if (view is TextView) {
            if (defaultTextColors == null) {
                defaultTextColors = view.textColors
            }
            view.setTextColor(if (position == 0) view.hintTextColors else defaultTextColors)
        }

        return view
    }

}