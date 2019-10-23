package com.softtech.android_structure.base.dialogs

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.softtech.android_structure.R

object AlertDialogManager{

    fun showAlertMessage(context: Context, setMessage: String) {
        createOneButtonAlertDialog(context, setMessage,"",DialogInterface.OnClickListener { dialog, which -> dialog.cancel() }).show()
    }


     fun createOneButtonAlertDialog(context: Context, setMessage: String, title: String = "", okAction: DialogInterface.OnClickListener): AlertDialog {
        val alertDialogBuilder: AlertDialog.Builder= AlertDialog.Builder(context)
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setMessage(setMessage)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setPositiveButton(context.getString(R.string.ok), okAction )
        val alertDialog: AlertDialog =alertDialogBuilder.create()
        return alertDialog
    }

     fun createAlertDialog(context: Context, setMessage: String, title: String = "", okAction: DialogInterface.OnClickListener): AlertDialog {
        val alertDialogBuilder: AlertDialog.Builder= AlertDialog.Builder(context)
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setMessage(setMessage)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setPositiveButton(context.getString(R.string.ok), okAction )
        alertDialogBuilder.setNegativeButton(context.getString(R.string.cancel)) { dialogInterface, i -> dialogInterface.dismiss() }

        val alertDialog: AlertDialog =alertDialogBuilder.create()
        return alertDialog
    }

}