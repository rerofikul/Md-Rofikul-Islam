package com.example.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.UserSubmission
import com.example.ui.theme.UnionEmergencyRed
import com.example.ui.theme.UnionGreenPrimary
import com.example.util.NotificationHelper
import com.example.viewmodel.UnionViewModel

@Composable
fun AdminPanelDialog(
    viewModel: UnionViewModel,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val isAdminLoggedIn by viewModel.isAdminLoggedIn.collectAsState()
    val submissions by viewModel.submissions.collectAsState()
    val userMessage by viewModel.userMessage.collectAsState()
    val fcmToken by viewModel.fcmToken.collectAsState()

    var enteredPin by remember { mutableStateOf("") }
    var adminTab by remember { mutableStateOf(0) } // 0 = Submissions, 1 = Post Notice, 2 = FCM Push Console, 3 = Security

    // Post Notice State
    var noticeTitle by remember { mutableStateOf("") }
    var noticeDesc by remember { mutableStateOf("") }
    var noticeCategory by remember { mutableStateOf("জরুরি ঘোষণা") }
    var isUrgentNotice by remember { mutableStateOf(true) }
    var sendPushNotification by remember { mutableStateOf(true) }
    var noticePublisher by remember { mutableStateOf("চর আমখাওয়া ইউনিয়ন পরিষদ") }

    // Change Pin State
    var newPin by remember { mutableStateOf("") }

    LaunchedEffect(isAdminLoggedIn) {
        if (isAdminLoggedIn) {
            viewModel.loadFcmToken()
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.96f)
                .height(650.dp)
                .testTag("admin_panel_dialog_surface"),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = "Admin",
                        tint = UnionGreenPrimary,
                        modifier = Modifier.size(26.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ইউপি প্রশাসন ও রক্ষণাবেক্ষণ",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    if (isAdminLoggedIn) {
                        IconButton(
                            onClick = { viewModel.logoutAdmin() },
                            modifier = Modifier.testTag("admin_logout_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Logout,
                                contentDescription = "Logout",
                                tint = UnionEmergencyRed
                            )
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                if (!isAdminLoggedIn) {
                    // PIN Entry Screen
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = UnionGreenPrimary,
                            modifier = Modifier.size(54.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "অ্যাডমিন পিন কোড প্রবেশ করুন",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "তথ্য যাচাই, অনুমোদন এবং পুশ নোটিফিকেশন পাঠানোর জন্য পিন দিন।\n(ডিফল্ট অ্যাডমিন পিন: 1234)",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            lineHeight = 17.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        OutlinedTextField(
                            value = enteredPin,
                            onValueChange = { enteredPin = it },
                            label = { Text("৪-সংখ্যার পিন") },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .testTag("admin_pin_input")
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { viewModel.verifyAdminPin(enteredPin) },
                            colors = ButtonDefaults.buttonColors(containerColor = UnionGreenPrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(46.dp)
                                .testTag("admin_login_button")
                        ) {
                            Text("লগইন করুন", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                } else {
                    // Authenticated Admin Dashboard Tabs
                    TabRow(
                        selectedTabIndex = adminTab,
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = UnionGreenPrimary
                    ) {
                        val pendingCount = submissions.count { it.status == "PENDING" }
                        Tab(
                            selected = adminTab == 0,
                            onClick = { adminTab = 0 },
                            text = {
                                Text(
                                    text = "যাচাই ($pendingCount)",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            modifier = Modifier.testTag("admin_tab_submissions")
                        )
                        Tab(
                            selected = adminTab == 1,
                            onClick = { adminTab = 1 },
                            text = {
                                Text(
                                    text = "নতুন নোটিশ",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            modifier = Modifier.testTag("admin_tab_notices")
                        )
                        Tab(
                            selected = adminTab == 2,
                            onClick = { adminTab = 2 },
                            text = {
                                Text(
                                    text = "FCM কনসোল",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            modifier = Modifier.testTag("admin_tab_fcm")
                        )
                        Tab(
                            selected = adminTab == 3,
                            onClick = { adminTab = 3 },
                            text = {
                                Text(
                                    text = "পিন",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            modifier = Modifier.testTag("admin_tab_security")
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    when (adminTab) {
                        0 -> {
                            // Submissions Verification List
                            if (submissions.isEmpty()) {
                                EmptyView(message = "কোন পেন্ডিং আবেদন নেই।")
                            } else {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    items(submissions, key = { it.id }) { sub ->
                                        SubmissionReviewCard(
                                            submission = sub,
                                            onApprove = { viewModel.approveSubmission(sub) },
                                            onReject = { viewModel.rejectSubmission(sub) },
                                            onDelete = { viewModel.deleteSubmission(sub.id) }
                                        )
                                    }
                                }
                            }
                        }
                        1 -> {
                            // Post New Urgent Notice + Push Notification
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Text(
                                    text = "জরুরি ঘোষণা প্রচার ও পুশ নোটিফিকেশন",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = noticeTitle,
                                    onValueChange = { noticeTitle = it },
                                    label = { Text("ঘোষণার শিরোনাম *") },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth().testTag("input_notice_title")
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = noticeCategory,
                                    onValueChange = { noticeCategory = it },
                                    label = { Text("ক্যাটাগরি (জরুরি ঘোষণা, স্বাস্থ্য, কৃষি, ইত্যাদি)") },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth().testTag("input_notice_category")
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = noticePublisher,
                                    onValueChange = { noticePublisher = it },
                                    label = { Text("প্রচারকারী দপ্তর") },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth().testTag("input_notice_publisher")
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = noticeDesc,
                                    onValueChange = { noticeDesc = it },
                                    label = { Text("বিস্তারিত বিবরণ *") },
                                    maxLines = 4,
                                    modifier = Modifier.fillMaxWidth().testTag("input_notice_desc")
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 2.dp)
                                ) {
                                    Checkbox(
                                        checked = isUrgentNotice,
                                        onCheckedChange = { isUrgentNotice = it },
                                        colors = CheckboxDefaults.colors(checkedColor = UnionEmergencyRed)
                                    )
                                    Text("জরুরি ঘোষণা হিসেবে হাইলাইট করুন", fontSize = 12.sp)
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 2.dp)
                                ) {
                                    Checkbox(
                                        checked = sendPushNotification,
                                        onCheckedChange = { sendPushNotification = it },
                                        colors = CheckboxDefaults.colors(checkedColor = UnionGreenPrimary)
                                    )
                                    Text("ব্যবহারকারীদের ডিভাইসে তাৎক্ষণিক পুশ নোটিফিকেশন পাঠান", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Button(
                                    onClick = {
                                        if (noticeTitle.isNotBlank() && noticeDesc.isNotBlank()) {
                                            viewModel.postNotice(
                                                title = noticeTitle.trim(),
                                                description = noticeDesc.trim(),
                                                category = noticeCategory.trim(),
                                                isUrgent = isUrgentNotice,
                                                publisher = noticePublisher.trim(),
                                                sendPushNotification = sendPushNotification,
                                                context = context
                                            )
                                            noticeTitle = ""
                                            noticeDesc = ""
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = UnionGreenPrimary),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(44.dp)
                                        .testTag("publish_notice_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Campaign,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("ঘোষণাটি প্রচার ও নোটিফিকেশন পাঠান", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        2 -> {
                            // FCM Push Console & Token Info
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Text(
                                    text = "Firebase Cloud Messaging (FCM) পুশ কনসোল",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Topic status
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.NotificationsActive,
                                                contentDescription = null,
                                                tint = UnionGreenPrimary,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = "স্বয়ংক্রিয় ব্রডকাস্ট টপিক সক্রিয়",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.sp,
                                                color = Color(0xFF1B5E20)
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "• /topics/urgent_announcements (জরুরি ঘোষণা)\n• /topics/sanandabari_news (সকল নোটিশ ও সংবাদ)",
                                            fontSize = 11.sp,
                                            color = Color(0xFF2E7D32)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                // FCM Registration Token Card
                                Text(
                                    text = "এই ডিভাইসের FCM রেজিস্ট্রেশন টোকেন:",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = fcmToken ?: "টোকেন লোড হচ্ছে...",
                                            fontSize = 10.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            maxLines = 4
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            OutlinedButton(
                                                onClick = {
                                                    fcmToken?.let { token ->
                                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                                        val clip = ClipData.newPlainText("FCM Token", token)
                                                        clipboard.setPrimaryClip(clip)
                                                        Toast.makeText(context, "FCM টোকেন কপি করা হয়েছে", Toast.LENGTH_SHORT).show()
                                                    }
                                                },
                                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                                shape = RoundedCornerShape(8.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.ContentCopy,
                                                    contentDescription = "Copy",
                                                    modifier = Modifier.size(14.dp)
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text("টোকেন কপি করুন", fontSize = 11.sp)
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                // Test Push Notification Button
                                Button(
                                    onClick = {
                                        NotificationHelper.showPushNotification(
                                            context = context,
                                            title = "🚨 টেস্ট নোটিফিকেশন: আমাদের সানন্দবাড়ী",
                                            message = "এটি একটি পরীক্ষামূলক জরুরি পুশ নোটিফিকেশন। শব্দ ও ভাইব্রেশন সফলভাবে কাজ করছে।",
                                            category = "জরুরি ঘোষণা",
                                            isUrgent = true
                                        )
                                        Toast.makeText(context, "টেস্ট পুশ নোটিফিকেশন পাঠানো হয়েছে!", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.NotificationsActive,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("টেস্ট পুশ নোটিফিকেশন পাঠান", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "💡 নির্দেশনা: Firebase Console -> Cloud Messaging ট্যাবে গিয়ে 'New Campaign' তৈরি করে টপিক 'urgent_announcements' নির্বাচন করলেই সানন্দবাড়ী অ্যাপের সকল ব্যবহারকারীর মোবাইলে সরাসরি পুশ নোটিফিকেশন পৌঁছে যাবে।",
                                    fontSize = 11.sp,
                                    color = Color.Gray,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                        3 -> {
                            // Security & Change PIN
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(top = 16.dp)
                            ) {
                                Text(
                                    text = "অ্যাডমিন পিন পরিবর্তন",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = newPin,
                                    onValueChange = { newPin = it },
                                    label = { Text("নতুন ৪-সংখ্যার পিন") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth().testTag("input_new_pin")
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = {
                                        viewModel.updateAdminPin(newPin)
                                        newPin = ""
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = UnionGreenPrimary),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.fillMaxWidth().testTag("save_pin_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Key,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("নতুন পিন সংরক্ষণ করুন", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SubmissionReviewCard(
    submission: UserSubmission,
    onApprove: () -> Unit,
    onReject: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("submission_card_${submission.id}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                val (typeBg, typeLabel) = when (submission.submissionType) {
                    "BLOOD_DONOR" -> Color(0xFFFFEBEE) to "🩸 রক্তদাতা"
                    "DOCTOR" -> Color(0xFFE0F2F1) to "👨‍⚕️ ডাক্তার"
                    "EDUCATION" -> Color(0xFFE3F2FD) to "🎓 শিক্ষাপ্রতিষ্ঠান"
                    "OFFICIAL" -> Color(0xFFE0F2F1) to "🏛️ ইউপি সেবাকর্তা"
                    "SHOP" -> Color(0xFFFFF3E0) to "🛒 দোকান"
                    else -> Color(0xFFE8F5E9) to "🚨 জরুরি নম্বর"
                }

                Surface(
                    color = typeBg,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = typeLabel,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Status chip
                val (statusColor, statusText) = when (submission.status) {
                    "APPROVED" -> Color(0xFF2E7D32) to "অনুমোদিত"
                    "REJECTED" -> UnionEmergencyRed to "বাতিল"
                    else -> Color(0xFFF57F17) to "অপেক্ষমান"
                }

                Surface(
                    color = statusColor.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = statusText,
                        color = statusColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = submission.titleOrName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            if (submission.secondaryInfo.isNotBlank()) {
                Text(
                    text = submission.secondaryInfo,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }

            Text(
                text = "মোবাইল: ${submission.phoneNumber}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            if (submission.extraDetails.isNotBlank()) {
                Text(
                    text = "বিবরণ: ${submission.extraDetails}",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            Text(
                text = "জমা প্রদানকারী: ${submission.submittedBy} (${submission.submittedDate})",
                fontSize = 10.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Action Buttons
            if (submission.status == "PENDING") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onApprove,
                        colors = ButtonDefaults.buttonColors(containerColor = UnionGreenPrimary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("approve_btn_${submission.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Approve",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("অনুমোদন করুন", fontSize = 12.sp)
                    }

                    OutlinedButton(
                        onClick = onReject,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("reject_btn_${submission.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Reject",
                            tint = UnionEmergencyRed,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("বাতিল", fontSize = 12.sp, color = UnionEmergencyRed)
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
