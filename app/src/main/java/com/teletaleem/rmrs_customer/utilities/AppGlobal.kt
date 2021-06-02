package com.teletaleem.rmrs_customer.utilities

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.kaopiz.kprogresshud.KProgressHUD
import com.squareup.picasso.Picasso
import com.teletaleem.rmrs_customer.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class AppGlobal {
    companion object{
        var NOTIFICATION_CHANNEL = "RMRSCUSTOMER"
        val NOTIFICATION_GENERAL = "RMRS_Gernal"
        var BUILD = "PRODUCTION"
        val LONG=3500
        val SHORT=2000
        var mCurrency="Rs. "

        val testEmail="usamawajid116@hotmail.com"
        val testPassword="Usama@123"

        const val tokenId = "token_id"
        const val customerId = "customer_id"
        const val restaurantId="restaurant_id"
        const val ownerId="owner_id"
        const val restaurantName="restaurant_name"
        const val restaurantAddress="restaurant_address"
        const val restaurantImage="restaurant_image"
        const val customerAddress="customer_address"
        const val customerName="customer_name"
        const val customerEmail="customer_email"
        const val customerMobile="customer_mobile"
        const val deviceId="device_id"



        /*****************************************************Base URLs********************************************************/
        var HOME_BASE_URL = if ("PRODUCTION" == BUILD) "https://Customer.teletaleem.com" else "https://customer-backend.k8s.symcloud.net"
        var HOME_BASE_URL_IMAGE = if ("PRODUCTION" == BUILD) "live-url" else "stage-url"
        const val LOCATION_IQ_URL = "https://us1.locationiq.com/"

        /*****************************************************API URLs**********************************************************/



        /*****************************************************Shared Preferences**************************************************/
        private fun getPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences("CUSTOMERPREFERENCES",
                    Context.MODE_PRIVATE)
        }

        private fun getEditor(appContext: Context): SharedPreferences.Editor {
            return getPreferences(appContext).edit()
        }

        fun writeInteger(context: Context, key: String, value: Int) {
            getEditor(context).putInt(key, value).commit()
        }

        fun readInteger(context: Context, key: String, defValue: Int): Int {
            return getPreferences(context).getInt(key, defValue)
        }

        fun writeLong(context: Context, key: String, value: Long) {
            getEditor(context).putLong(key, value).commit()
        }

        fun readLong(context: Context, key: String, defValue: Long): Long {
            return getPreferences(context).getLong(key, defValue)
        }

        fun writeString(context: Context, key: String, value: String) {
            getEditor(context).putString(key, value).commit()
        }

        fun readString(context: Context, key: String, defValue: String): String {
            return getPreferences(context).getString(key, defValue)!!
        }

        fun writeBoolean(context: Context, key: String, value: Boolean?) {
            getEditor(context).putBoolean(key, value!!).commit()
        }

        fun readBoolean(context: Context, key: String,
                        defValue: Boolean?): Boolean {
            return getPreferences(context).getBoolean(key, defValue!!)
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

        fun setProgressDialog(context: Context):KProgressHUD
        {
           return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setBackgroundColor(ContextCompat.getColor(context,R.color.colorAccent))
               .setCancellable(false)
                .setAnimationSpeed(1)
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

        fun roundTwoPlaces(value: Double?): String? {
            val df = DecimalFormat("0.00")
            return df.format(value)
        }

        fun loadImageIntoGlide(imageURL:String?,imageView: ImageView,context: Context)
        {
            Glide
                .with(context)
                .load(imageURL)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .fitCenter()
                .into(imageView)
        }

        fun loadImageIntoPicasso(imageURL:String?,imageView: ImageView){
            Picasso.get().load(imageURL).placeholder(R.drawable.burger_10956).error(R.drawable.burger_10956).into(imageView)
        }

        fun sendNotification(context:Context, notificationID : Int, title:String, message:String, drawableImage:Int, vibration:Boolean, onGoing:Boolean, pendingIntent: PendingIntent, whenTime:Long) {

            try { //            NotificationManager notificationManager =
//                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                val notificationManager = NotificationManagerCompat.from(context)
                val bigTextStyle = Notification.BigTextStyle()
                val mBuilder: Notification.Builder
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    var mChannel = notificationManager.getNotificationChannel(AppGlobal.NOTIFICATION_CHANNEL)
                    if (mChannel == null) {
                        mChannel = NotificationChannel(NOTIFICATION_CHANNEL,
                            NOTIFICATION_GENERAL, NotificationManager.IMPORTANCE_DEFAULT)
                        mChannel.enableVibration(vibration)
                        notificationManager.createNotificationChannel(mChannel)
                    }
                    mBuilder = Notification.Builder(context, AppGlobal.NOTIFICATION_CHANNEL)
                } else {
                    mBuilder = Notification.Builder(context)
                }
                val color: Int = ContextCompat.getColor(context,R.color.white)
                mBuilder
                    .setSmallIcon(drawableImage)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setStyle(bigTextStyle)
                    .setColor(color)
                    .setAutoCancel(true)
                    .setOngoing(onGoing)
//                        .setDefaults(Notification.DEFAULT_SOUND)
//                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                mBuilder.setContentIntent(pendingIntent)
                if (whenTime != -1L) {
                    mBuilder.setWhen(whenTime)
                }
                notificationManager.notify(notificationID, mBuilder.build())
                val intentUpdate = Intent()
                intentUpdate.action = "com.teletaleem.rmrs_customer.broadcast.notification"
                intentUpdate.putExtra("messageBody", message)

                context.sendBroadcast(intentUpdate)

            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
            }
        }

        fun startGMapIntent(context: Context, address: String, latitude: Double, longitude: Double)
        {
            val gmmIntentUri: Uri = Uri.parse("geo:$latitude,$longitude?q=$address")
            val mapIntent =Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(mapIntent);
            }
        }

    }
}