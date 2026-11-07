package com.example.service

import android.content.Context
import android.util.Log
import com.example.data.db.AppDatabase
import com.example.data.model.NoticeItem
import com.example.util.NotificationHelper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SanandabariMessagingService : FirebaseMessagingService() {

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "New FCM registration token generated: $token")
        // Save the token locally in SharedPreferences
        val prefs = getSharedPreferences("sanandabari_prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("fcm_device_token", token).apply()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FCM", "FCM Message received from: ${remoteMessage.from}")

        // 1. Extract notification title and body
        var title = remoteMessage.notification?.title
        var body = remoteMessage.notification?.body
        var category = "জরুরি ঘোষণা"
        var isUrgent = true

        // 2. Check if data payload is present
        if (remoteMessage.data.isNotEmpty()) {
            if (remoteMessage.data.containsKey("title")) {
                title = remoteMessage.data["title"]
            }
            if (remoteMessage.data.containsKey("body")) {
                body = remoteMessage.data["body"]
            } else if (remoteMessage.data.containsKey("description")) {
                body = remoteMessage.data["description"]
            }
            if (remoteMessage.data.containsKey("category")) {
                category = remoteMessage.data["category"] ?: "জরুরি ঘোষণা"
            }
            if (remoteMessage.data.containsKey("isUrgent")) {
                isUrgent = remoteMessage.data["isUrgent"]?.toBoolean() ?: true
            }
        }

        val finalTitle = title ?: "জরুরি ঘোষণা: আমাদের সানন্দবাড়ী"
        val finalBody = body ?: "চর আমখাওয়া ইউনিয়ন পরিষদ হতে নতুন একটি জরুরি সংবাদ প্রচার করা হয়েছে।"

        // 3. Automatically persist into local Room database so it immediately shows up in the News Feed!
        serviceScope.launch {
            try {
                val dateFormat = SimpleDateFormat("dd MMMM yyyy, hh:mm a", Locale.getDefault())
                val notice = NoticeItem(
                    title = finalTitle,
                    description = finalBody,
                    category = category,
                    date = dateFormat.format(Date()),
                    isUrgent = isUrgent,
                    publisher = "চর আমখাওয়া ইউনিয়ন পরিষদ (পুশ নোটিফিকেশন)",
                    isApproved = true
                )
                val database = AppDatabase.getDatabase(applicationContext, this)
                database.unionDao().insertNotice(notice)
            } catch (e: Exception) {
                Log.e("FCM", "Error saving FCM notice to Room: ${e.message}")
            }
        }

        // 4. Display high-priority system push notification on device
        NotificationHelper.showPushNotification(
            context = applicationContext,
            title = finalTitle,
            message = finalBody,
            category = category,
            isUrgent = isUrgent
        )
    }
}
