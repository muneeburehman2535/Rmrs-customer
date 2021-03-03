package com.teletaleem.rmrs_customer.utilities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class AppGlobal {
    companion object{
        var BUILD = "STAGE"
        val LONG=3500
        val SHORT=2000
        private val prefsTokenId = "prefstoken"
        private val tokenId = "tokenId"

        /*****************************************************Base URLs********************************************************/
        var HOME_BASE_URL = if ("PRODUCTION" == BUILD) "live-url" else "https://Customer.teletaleem.com"
        var HOME_BASE_URL_IMAGE = if ("PRODUCTION" == BUILD) "live-url" else "stage-url"
        const val LOCATION_IQ_URL = "https://us1.locationiq.com/"

        /*****************************************************API URLs**********************************************************/



        /*****************************************************Shared Preferences**************************************************/
        fun saveToken(context: Context, token: String?) {
            val sp = context.getSharedPreferences(prefsTokenId, Context.MODE_PRIVATE)
            sp.edit().putString(tokenId, token).apply()
        }

        fun getToken(context: Context): String? {
            val sp = context.getSharedPreferences(prefsTokenId, Context.MODE_PRIVATE)
            return sp.getString(tokenId, "0")
        }

        /***************************************************Global Methods********************************************************/

        fun startNewActivity(context: Context, mClass: Class<*>) {
            val intent = Intent(context, mClass)
            context.startActivity(intent)
        }

        fun showDialog(title: String, msg: String, context: Context) {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle(title)
            alertDialog.setMessage(msg)
            alertDialog.setCancelable(false)
            alertDialog.setPositiveButton("Ok") { dialog, _ ->
                dialog.cancel()
            }
            val alertDialog1 = alertDialog.create()
            alertDialog1.show()
        }
        fun showDialogWithCloseActivity(title: String, msg: String, context: Activity) {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle(title)
            alertDialog.setMessage(msg)
            alertDialog.setCancelable(false)
            alertDialog.setPositiveButton("Ok") { dialog, _ ->
                context.finish()
                dialog.cancel()
            }
            val alertDialog1 = alertDialog.create()
            alertDialog1.show()
        }

        fun showToast(message: String, context: Context?) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun snackBar(view: View, message: String, duration: Int) {
            Snackbar.make(view, message, duration).show()
        }

        fun hideKeyboardFrom(context: Context, view: View) {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun dateToTimeStamp(date: Date, pattern: String?): String? {
            val sdf = SimpleDateFormat(pattern,Locale.ENGLISH)
            return sdf.format(date.time)
        }

        @Suppress("DEPRECATION")
        fun isInternetAvailable(context: Context): Boolean {
            var result = false
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm?.run {
                    cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                        result = when {
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                            else -> false
                        }
                    }
                }
            } else {
                cm?.run {
                    cm.activeNetworkInfo?.run {
                        if (type == ConnectivityManager.TYPE_WIFI) {
                            result = true
                        } else if (type == ConnectivityManager.TYPE_MOBILE) {
                            result = true
                        }
                    }
                }
            }
            return result
        }
    }
}