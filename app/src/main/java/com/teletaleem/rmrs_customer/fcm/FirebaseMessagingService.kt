package com.teletaleem.rmrs_customer.fcm

/**
 * Created by Usama Wajid.
 */


import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.teletaleem.rmrs_customer.R
import com.teletaleem.rmrs_customer.ui.splash.SplashActivity
import com.teletaleem.rmrs_customer.utilities.AppGlobal
import timber.log.Timber


class FirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "MyFirebaseMsgService"
    internal var objAppGlobal = AppGlobal()


    override fun onNewToken(p0: String) {
        AppGlobal.writeString(this, AppGlobal.deviceId, p0)
        Timber.d("Token: $p0")
    }


    @SuppressLint("LogNotTimber")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, remoteMessage!!.data.toString())
        Log.d("MessageBody", remoteMessage!!.messageId!!)


        if (remoteMessage.getNotification() != null) {
            Timber.d( "Message Notification Title: ${remoteMessage.notification!!.title} " );
            Log.d(TAG, "Message Notification Body: ${ remoteMessage.notification!!.body}");
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val intent = Intent(this, SplashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)
            AppGlobal.sendNotification(this,1,getString(R.string.app_name),remoteMessage.getNotification()!!.getBody().toString(),R.mipmap.ic_launcher_round,true,false,pendingIntent,-1)
        }
    }


    private fun getNotificationIcon(): Int {
        val useWhiteIcon = VERSION.SDK_INT >= LOLLIPOP
        return if (useWhiteIcon) R.mipmap.ic_launcher else R.mipmap.ic_launcher
    }








}