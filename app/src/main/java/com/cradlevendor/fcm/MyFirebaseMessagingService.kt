package com.cradlevendor.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.cradlevendor.R
import com.cradlevendor.chat.model.ChatActivity
import com.cradlevendor.utils.SharaGoPref
import org.json.JSONObject


class MyFirebaseMessagingService : FirebaseMessagingService() {
    private var broadcaster: androidx.localbroadcastmanager.content.LocalBroadcastManager? = null
    private var mNotificationManager: NotificationManager? = null
    private var mBuilder: NotificationCompat.Builder? = null
    private var healthPref: SharaGoPref? = null
    var context: Context? = null
    override fun onCreate() {
        broadcaster = androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(this)
        healthPref = SharaGoPref.getInstance(this)

    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // playing audio and vibration when user se reques
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val r = RingtoneManager.getRingtone(
            applicationContext, notification
        )
        r.play()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            r.isLooping = false
        }

   /*     if (isActivePhysician.isNotEmpty() && isActivePatient.isNotEmpty()) {

        } else if (isActivePhysician.isEmpty() && isActivePatient.isNotEmpty()) {

        } else if (isActivePatient.isEmpty() && isActivePhysician.isNotEmpty()) {

        }
        else {

        }*/
        if (remoteMessage.data != null) {
            if (remoteMessage.data["actionRequired"] == "VIEW_REPORT") {
                val json = JSONObject(remoteMessage.data["response"]!!)
               // readNotificationApi(json.getInt("notificationId"),context as Activity)
                val queryId = json.getString("queryId")
                val email: String?
                if (healthPref!!.getUserType("")?.equals("PATIENT")!!) {
                    email = json.getString("consultantEmail")
                } else {
                    email = json.getString("patientEmail")
                }
                val status = json.getString("status")
              /*  viewReportNotification(
                    remoteMessage.data["title"],
                    remoteMessage.data["body"]!!,
                    email,
                    queryId,
                    status
                )*/
            } else if (remoteMessage.data["actionRequired"] == "VIEW CHAT") {
                val json = JSONObject(remoteMessage.data["response"]!!)
               // readNotificationApi(json.getInt("notificationId"),context as Activity)
                val queryId = json.getString("queryId")
                var email: String? = null
                if (healthPref!!.getUserType("")?.equals("PATIENT")!!) {
                    email = json.getString("consultantEmail")
                } else {
                    email = json.getString("patientEmail")

                }
                val status = json.getString("status")
                chatViewNotification(
                    remoteMessage.data["title"],
                    remoteMessage.data["body"]!!,
                    email,
                    queryId,
                    status
                )
            } else if (remoteMessage.data["actionRequired"] == "CONSULTATION_DETAILS") {
                val json = JSONObject(remoteMessage.data["response"]!!)
              //  readNotificationApi(json.getInt("notificationId"),context as Activity)
                var queryId: String? = null
                var email: String? = null
                if (healthPref!!.getUserType("")?.equals("PATIENT")!!) {
                    queryId = json.getString("queryId")
                    email = json.getString("consultantEmail")
                } else {
                    queryId = json.getString("querId")
                    email = json.getString("patientEmail")

                }
                val status = json.getString("status")
           /*     consultDetailsNotification(
                    remoteMessage.data["title"],
                    remoteMessage.data["body"]!!,
                    email,
                    queryId,
                    status
                )*/
            }
            else if (remoteMessage.data["actionRequired"] == "SUBSCRIPTION_LIST"){
                val json = JSONObject(remoteMessage.data["response"]!!)
              //  subscriptionExpire(json.getInt("notificationId"), remoteMessage.data["title"], remoteMessage.data["body"]!!)
            }
            else {
                try {
                    pushNotification(
                        remoteMessage.data["title"],
                        remoteMessage.data["body"]!!,
                        remoteMessage.data["email"]!!,
                        remoteMessage.data["queryID"]!!
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    /*private fun subscriptionExpire(patientId: Int,title: String?, message: String) {
        if (healthPref!!.getRole("")?.equals("PATIENT")!!) {
            val resultIntent = Intent(this@MyFirebaseMessagingService, SubcriptionActivity::class.java)
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            resultIntent.putExtra("patientId", patientId)
            val resultPendingIntent = PendingIntent.getActivity(this@MyFirebaseMessagingService, 0 *//* Request code *//*, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            mBuilder = NotificationCompat.Builder(this@MyFirebaseMessagingService)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.app_icon)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent)

        }
        mNotificationManager = this@MyFirebaseMessagingService.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            assert(mNotificationManager != null)
            mBuilder!!.setChannelId(NOTIFICATION_CHANNEL_ID)
            mNotificationManager!!.createNotificationChannel(notificationChannel)
        }
        assert(mNotificationManager != null)
        mNotificationManager!!.notify(0 *//* Request Code *//*, mBuilder!!.build())

    }*/

   /* fun viewReportNotification(title: String?, message: String, patientEmail: String, queryId: String, status: String) {
        if (healthPref!!.getRole("")?.equals("PATIENT")!!) {
            val resultIntent = Intent(this@MyFirebaseMessagingService, ActiveConsultsActivity::class.java)
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            resultIntent.putExtra("email", patientEmail)
            resultIntent.putExtra("queryId", queryId)
            resultIntent.putExtra("status", status)
            val resultPendingIntent = PendingIntent.getActivity(this@MyFirebaseMessagingService, 0 *//* Request code *//*, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            mBuilder = NotificationCompat.Builder(this@MyFirebaseMessagingService)
                .setContentTitle(title)
                .setContentText(message)
               .setSmallIcon(R.mipmap.app_icon)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent)

        } else if (healthPref!!.getRole("")?.equals("PHYSICIAN")!!) {

        }
        mNotificationManager = this@MyFirebaseMessagingService.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            assert(mNotificationManager != null)
            mBuilder!!.setChannelId(NOTIFICATION_CHANNEL_ID)
            mNotificationManager!!.createNotificationChannel(notificationChannel)
        }
        assert(mNotificationManager != null)
        mNotificationManager!!.notify(0 *//* Request Code *//*, mBuilder!!.build())
    }
*/
    fun chatViewNotification(title: String?, message: String, email: String, queryId: String, status: String) {
        if (healthPref!!.getUserType("")?.equals("PATIENT")!!) {
            val resultIntent = Intent(this@MyFirebaseMessagingService, ChatActivity::class.java)
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            resultIntent.putExtra("email", email)
            resultIntent.putExtra("queryId", queryId)
            resultIntent.putExtra("status", status)
            resultIntent.putExtra("active", "true")
            val resultPendingIntent = PendingIntent.getActivity(this@MyFirebaseMessagingService, 0 /* Request code */, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            mBuilder = NotificationCompat.Builder(this@MyFirebaseMessagingService,NOTIFICATION_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
               .setSmallIcon(R.drawable.app_launcher_vendor)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent)

        }

        mNotificationManager = this@MyFirebaseMessagingService.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            assert(mNotificationManager != null)
            mBuilder!!.setChannelId(NOTIFICATION_CHANNEL_ID)
            mNotificationManager!!.createNotificationChannel(notificationChannel)
        }
        assert(mNotificationManager != null)
        mNotificationManager!!.notify(0 /* Request Code */, mBuilder!!.build())
    }

    /*fun consultDetailsNotification(title: String?, message: String, email: String, queryId: String, status: String) {
        if (healthPref!!.getRole("")?.equals("PATIENT")!!) {
            val resultIntent = Intent(this@MyFirebaseMessagingService, ActiveConsultsActivity::class.java)
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            resultIntent.putExtra("email", email)
            resultIntent.putExtra("queryId", queryId)
            resultIntent.putExtra("status", status)
            val resultPendingIntent = PendingIntent.getActivity(this@MyFirebaseMessagingService, 0 *//* Request code *//*, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            mBuilder = NotificationCompat.Builder(this@MyFirebaseMessagingService,)
                .setContentTitle(title)
                .setContentText(message)
               .setSmallIcon(R.mipmap.app_icon)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent)

        } else if (healthPref!!.getRole("")?.equals("PHYSICIAN")!!) {
            val resultIntent = Intent(this@MyFirebaseMessagingService, PhysicianDashboardActivity::class.java)
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            resultIntent.putExtra("email", email)
            resultIntent.putExtra("queryId", queryId)
            resultIntent.putExtra("status", status)
            val resultPendingIntent = PendingIntent.getActivity(this@MyFirebaseMessagingService, 0 *//* Request code *//*, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            mBuilder = NotificationCompat.Builder(this@MyFirebaseMessagingService,NOTIFICATION_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
               .setSmallIcon(R.mipmap.app_icon)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent)
        }
        mNotificationManager = this@MyFirebaseMessagingService.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            assert(mNotificationManager != null)
            mBuilder!!.setChannelId(NOTIFICATION_CHANNEL_ID)
            mNotificationManager!!.createNotificationChannel(notificationChannel)
        }
        assert(mNotificationManager != null)
        mNotificationManager!!.notify(0 *//* Request Code *//*, mBuilder!!.build())
    }
*/
    private fun pushNotification(title: String?, message: String, email: String, queryId: String) {
        if (healthPref!!.getUserType("")?.equals("PATIENT")!!) {
            val resultIntent = Intent(this@MyFirebaseMessagingService, ChatActivity::class.java)
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            resultIntent.putExtra("email", email)
            resultIntent.putExtra("queryId", queryId)
            resultIntent.putExtra("consult", "true")
            resultIntent.putExtra("status", "ACCEPTED")
            resultIntent.putExtra("Key", "active")
            val resultPendingIntent = PendingIntent.getActivity(this@MyFirebaseMessagingService, 0 /* Request code */, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            mBuilder = NotificationCompat.Builder(this@MyFirebaseMessagingService,NOTIFICATION_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
               .setSmallIcon(R.drawable.app_launcher_vendor)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent)

        } else if (healthPref!!.getUserType("")?.equals("PHYSICIAN")!!) {
            val resultIntent = Intent(this@MyFirebaseMessagingService, ChatActivity::class.java)
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            resultIntent.putExtra("email", email)
            resultIntent.putExtra("queryId", queryId)
            resultIntent.putExtra("status", "ACCEPTED")
            resultIntent.putExtra("activeConsult", "true")
            val resultPendingIntent = PendingIntent.getActivity(this@MyFirebaseMessagingService, 0 /* Request code */, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            mBuilder = NotificationCompat.Builder(this@MyFirebaseMessagingService,NOTIFICATION_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.app_launcher_vendor)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent)
        }
        mNotificationManager = this@MyFirebaseMessagingService.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            assert(mNotificationManager != null)
            mBuilder!!.setChannelId(NOTIFICATION_CHANNEL_ID)
            mNotificationManager!!.createNotificationChannel(notificationChannel)
        }
        assert(mNotificationManager != null)
        mNotificationManager!!.notify(0 /* Request Code */, mBuilder!!.build())
    }

    companion object {
        private val TAG = "MyFirebaseMessagingService"
        const val NOTIFICATION_CHANNEL_ID = "10001"
    }
}
