package com.example.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.R
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging

object NotificationHelper {

    const val CHANNEL_ID_URGENT = "urgent_announcements_channel"
    const val CHANNEL_NAME_URGENT = "সানন্দবাড়ী জরুরি ঘোষণা ও নোটিশ"
    const val CHANNEL_DESC_URGENT = "জরুরি ঘোষণা, আবহাওয়া সতর্কতা, সরকারি অনুদান ও স্বাস্থ্য ক্যাম্প সম্পর্কিত পুশ নোটিফিকেশন।"

    const val TOPIC_URGENT_ANNOUNCEMENTS = "urgent_announcements"
    const val TOPIC_ALL_NEWS = "sanandabari_news"

    /**
     * Safely ensures FirebaseApp is initialized before invoking any Firebase services.
     * Prevents "Default FirebaseApp is not initialized" crashes.
     */
    fun ensureFirebaseInitialized(context: Context): Boolean {
        return try {
            if (FirebaseApp.getApps(context).isEmpty()) {
                // Try default initialization first
                val app = FirebaseApp.initializeApp(context)
                if (app == null) {
                    // Fallback to programmatic options if string resources were not packaged
                    val options = FirebaseOptions.Builder()
                        .setApplicationId("1:444865286237:android:a67b4589d89ef123456789")
                        .setApiKey("AIzaSyDummyKeyForGoogleServicesInitialization")
                        .setProjectId("smartunion-sanandabari")
                        .setGcmSenderId("444865286237")
                        .build()
                    FirebaseApp.initializeApp(context, options)
                }
            }
            true
        } catch (e: Exception) {
            Log.e("FCM", "Failed to initialize FirebaseApp: ${e.message}")
            false
        }
    }

    /**
     * Create High-Priority Notification Channels for Android 8.0+ (API 26+)
     */
    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val channel = NotificationChannel(
                CHANNEL_ID_URGENT,
                CHANNEL_NAME_URGENT,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESC_URGENT
                enableLights(true)
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 300, 200, 300)
            }

            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Subscribe app instance to FCM topics for universal broadcast notifications
     */
    fun subscribeToFcmTopics(context: Context) {
        if (!ensureFirebaseInitialized(context)) {
            Log.w("FCM", "Cannot subscribe to FCM topics: FirebaseApp initialization failed")
            return
        }

        try {
            FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_URGENT_ANNOUNCEMENTS)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("FCM", "Subscribed to $TOPIC_URGENT_ANNOUNCEMENTS")
                    } else {
                        Log.w("FCM", "Failed to subscribe to $TOPIC_URGENT_ANNOUNCEMENTS: ${task.exception?.message}")
                    }
                }

            FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_ALL_NEWS)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("FCM", "Subscribed to $TOPIC_ALL_NEWS")
                    } else {
                        Log.w("FCM", "Failed to subscribe to $TOPIC_ALL_NEWS: ${task.exception?.message}")
                    }
                }
        } catch (e: Exception) {
            Log.e("FCM", "Error subscribing to FCM topics: ${e.message}")
        }
    }

    /**
     * Fetch the device's FCM Registration Token with optional Context for auto-init
     */
    fun fetchFcmToken(context: Context? = null, onTokenReceived: (String) -> Unit) {
        if (context != null) {
            ensureFirebaseInitialized(context)
        }

        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val token = task.result
                    Log.d("FCM", "Current FCM Token: $token")
                    onTokenReceived(token)
                } else {
                    onTokenReceived("টোকেন পাওয়া যায়নি (ইন্টারনেট বা গুগল প্লে সার্ভিস প্রয়োজন)")
                }
            }
        } catch (e: Exception) {
            Log.e("FCM", "Error fetching FCM token: ${e.message}")
            onTokenReceived("FCM সার্ভিস ইনিশিয়ালাইজেশন এরর: ${e.message}")
        }
    }

    /**
     * Overload for fetchFcmToken without context
     */
    fun fetchFcmToken(onTokenReceived: (String) -> Unit) {
        fetchFcmToken(null, onTokenReceived)
    }

    /**
     * Show a rich, high-priority system push notification on the device
     */
    fun showPushNotification(
        context: Context,
        title: String,
        message: String,
        category: String = "জরুরি ঘোষণা",
        isUrgent: Boolean = true
    ) {
        createNotificationChannels(context)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("notification_title", title)
            putExtra("notification_message", message)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID_URGENT)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setVibrate(longArrayOf(0, 300, 200, 300))
            .setPriority(if (isUrgent) NotificationCompat.PRIORITY_MAX else NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setContentIntent(pendingIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = (System.currentTimeMillis() % 100000).toInt()
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}
