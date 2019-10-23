package com.softtech.android_structure.base.extension

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import androidx.fragment.app.Fragment
import com.softtech.android_structure.application.ApplicationClass
import com.softtech.android_structure.base.dialogs.AlertDialogManager
import com.softtech.android_structure.data.exceptions.APIException
import com.softtech.android_structure.data.repositories.UserRepository
import com.softtech.android_structure.features.FeatureConstants.EXPIRE_SESSION_LIST_STATUS
import com.softtech.android_structure.features.authorization.AuthorizationActivity
import com.softtech.android_structure.features.common.showErrorSnackbar
import org.koin.android.ext.android.inject
import retrofit2.HttpException
import timber.log.Timber

fun ApplicationClass.isInternetConnected(): Boolean {

    val connectivityManager =
        ApplicationClass.appContext.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
}

    inline fun Throwable.handleApiError(action: (Int, String) -> Unit) {
        if (this is HttpException) {
            if (this.message!=null) {

                action(code(), this.message())
            }else{
                val message ="unsepcified error !!"
                action(code(), message)
            }

        } else if (this is APIException){
            val errorMessage: String = message!!
            action(this.code.toInt(),errorMessage)
            Log.d("API EXCEPTION", errorMessage)
        } else {
            val errorMessage: String = message!!
            action(0,errorMessage)
            Log.d("Not_HTTP", errorMessage)
        }

    }

    fun Fragment.handleApiError(e: Throwable) {

        val mainRepository : UserRepository by inject()
        e.handleApiError { code, message ->
            Timber.i("handleApiError $code" )
            if (code in EXPIRE_SESSION_LIST_STATUS) {
                if (mainRepository.isLoged()) {
                    mainRepository.clear()
                //    showUnAuthorizedAlerDialog(requireActivity(),message)
                } else showErrorSnackbar(view!!, message)
            }
        }

    }



    fun Fragment.handleApiErrorWithSnackBar(e: Throwable) {

        val mainRepository :UserRepository by inject()
        e.handleApiError { code, message ->
            Timber.i("handleApiError $code user ${mainRepository.isLoged()}" )
            if (code in EXPIRE_SESSION_LIST_STATUS) {

                if (mainRepository.isLoged()) {
                    mainRepository.clear()
                    showUnAuthorizedAlerDialog(requireActivity(),message)
                } else showErrorSnackbar(view!!, message)
            }else{
                showErrorSnackbar(view!!,message)
                Timber.i("handle api ${code in EXPIRE_SESSION_LIST_STATUS}")
            }
        }

    }

    fun showUnAuthorizedAlerDialog(activity: Activity, message:String) {

        AlertDialogManager.createOneButtonAlertDialog(
            activity,
            message,
            okAction = DialogInterface.OnClickListener { dialogInterface, i ->
                activity.startActivity(Intent(activity, AuthorizationActivity::class.java))
                activity.overridePendingTransition(0, 0)
                activity.finish()
            }).show()
    }

