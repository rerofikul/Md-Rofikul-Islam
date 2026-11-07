package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.UnionGreenPrimary
import com.example.viewmodel.UnionViewModel

@Composable
fun AddInfoDialog(
    viewModel: UnionViewModel,
    onDismiss: () -> Unit
) {
    var selectedType by remember { mutableStateOf("EDUCATION") }
    var titleOrName by remember { mutableStateOf("") }
    var secondaryInfo by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }
    var submitterName by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val types = listOf(
        "EDUCATION" to "🎓 শিক্ষাপ্রতিষ্ঠান",
        "OFFICIAL" to "🏛️ ইউপি সেবাকর্তা",
        "BLOOD_DONOR" to "🩸 রক্তদাতা",
        "DOCTOR" to "👨‍⚕️ ডাক্তার",
        "SHOP" to "🛒 সানন্দবাড়ী দোকান",
        "EMERGENCY_CONTACT" to "🚨 জরুরি সেবা"
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .testTag("add_info_dialog_surface"),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = null,
                        tint = UnionGreenPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "নতুন তথ্য যুক্ত করুন",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                // Verification Notice
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = UnionGreenPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "আপনার তথ্যটি অ্যাডমিন প্যানেল কর্তৃক যাচাইয়ের পর আমাদের সানন্দবাড়ী অ্যাপে যুক্ত হবে।",
                            fontSize = 12.sp,
                            color = Color(0xFF1B5E20),
                            lineHeight = 17.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Select Type Chips
                Text("তথ্যের ধরণ নির্বাচন করুন:", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    types.forEach { (typeKey, typeLabel) ->
                        FilterChip(
                            selected = selectedType == typeKey,
                            onClick = {
                                selectedType = typeKey
                                errorMessage = null
                            },
                            label = { Text(typeLabel, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = UnionGreenPrimary,
                                selectedLabelColor = Color.White
                            ),
                            modifier = Modifier.testTag("type_chip_$typeKey")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Dynamic Form Fields based on Type
                when (selectedType) {
                    "EDUCATION" -> {
                        OutlinedTextField(
                            value = titleOrName,
                            onValueChange = { titleOrName = it },
                            label = { Text("বিদ্যালয় / কলেজ / মাদ্রাসার নাম *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_title_or_name"),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = category,
                            onValueChange = { category = it },
                            label = { Text("ধরণ (প্রাথমিক / মাধ্যমিক / কলেজ / মাদ্রাসা) *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_category"),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = secondaryInfo,
                            onValueChange = { secondaryInfo = it },
                            label = { Text("প্রধান শিক্ষক / অধ্যক্ষের নাম *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_secondary_info"),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = details,
                            onValueChange = { details = it },
                            label = { Text("অবস্থান ও গ্রাম (যেমন: সানন্দবাড়ী, ৩নং ওয়ার্ড)") },
                            modifier = Modifier.fillMaxWidth().testTag("input_details"),
                            singleLine = true
                        )
                    }
                    "OFFICIAL" -> {
                        OutlinedTextField(
                            value = titleOrName,
                            onValueChange = { titleOrName = it },
                            label = { Text("কর্মকর্তা বা কর্মচারীর নাম *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_title_or_name"),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = category,
                            onValueChange = { category = it },
                            label = { Text("পদবী (মেম্বার / গ্রাম পুলিশ / স্বাস্থ্যকর্মী ইত্যাদি) *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_category"),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = secondaryInfo,
                            onValueChange = { secondaryInfo = it },
                            label = { Text("দায়িত্বপ্রাপ্ত ওয়ার্ড বা এলাকা *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_secondary_info"),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = details,
                            onValueChange = { details = it },
                            label = { Text("স্থায়ী গ্রাম ও ঠিকানা *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_details"),
                            singleLine = true
                        )
                    }
                    "BLOOD_DONOR" -> {
                        OutlinedTextField(
                            value = titleOrName,
                            onValueChange = { titleOrName = it },
                            label = { Text("রক্তদাতার নাম *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_title_or_name"),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = category,
                            onValueChange = { category = it },
                            label = { Text("রক্তের গ্রুপ (যেমন: A+, B+, O+, AB+) *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_category"),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = secondaryInfo,
                            onValueChange = { secondaryInfo = it },
                            label = { Text("গ্রাম ও ওয়ার্ড নং (যেমন: সানন্দবাড়ী, ১নং ওয়ার্ড) *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_secondary_info"),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = details,
                            onValueChange = { details = it },
                            label = { Text("শেষ রক্তদানের তারিখ (ঐচ্ছিক)") },
                            modifier = Modifier.fillMaxWidth().testTag("input_details"),
                            singleLine = true
                        )
                    }
                    "DOCTOR" -> {
                        OutlinedTextField(
                            value = titleOrName,
                            onValueChange = { titleOrName = it },
                            label = { Text("ডাক্তারের নাম *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_title_or_name"),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = category,
                            onValueChange = { category = it },
                            label = { Text("বিশেষজ্ঞতা (মেডিসিন, শিশু, গাইনি, পল্লী চিকিৎসক) *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_category"),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = secondaryInfo,
                            onValueChange = { secondaryInfo = it },
                            label = { Text("ডিগ্রি (যেমন: এমবিবিএস, ডিএমএফ)") },
                            modifier = Modifier.fillMaxWidth().testTag("input_secondary_info"),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = details,
                            onValueChange = { details = it },
                            label = { Text("সানন্দবাড়ী চেম্বার বা ক্লিনিক ও ভিজিটিং সময় *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_details"),
                            singleLine = true
                        )
                    }
                    "SHOP" -> {
                        OutlinedTextField(
                            value = titleOrName,
                            onValueChange = { titleOrName = it },
                            label = { Text("দোকানের নাম *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_title_or_name"),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = secondaryInfo,
                            onValueChange = { secondaryInfo = it },
                            label = { Text("দোকান মালিক / প্রোপাইটরের নাম *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_secondary_info"),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = category,
                            onValueChange = { category = it },
                            label = { Text("ক্যাটাগরি (মুদি, ফার্মেসি, শস্য আড়ৎ, কাঁচাবাজার) *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_category"),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = details,
                            onValueChange = { details = it },
                            label = { Text("সানন্দবাড়ী বাজারের অবস্থান বা গলি *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_details"),
                            singleLine = true
                        )
                    }
                    "EMERGENCY_CONTACT" -> {
                        OutlinedTextField(
                            value = titleOrName,
                            onValueChange = { titleOrName = it },
                            label = { Text("জরুরি সেবার নাম *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_title_or_name"),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = secondaryInfo,
                            onValueChange = { secondaryInfo = it },
                            label = { Text("বিবরণ বা দায়িত্বপ্রাপ্ত কর্মকর্তা *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_secondary_info"),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = details,
                            onValueChange = { details = it },
                            label = { Text("অবস্থান ও সেবার সময় (২৪ ঘণ্টা ইত্যাদি) *") },
                            modifier = Modifier.fillMaxWidth().testTag("input_details"),
                            singleLine = true
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Mobile number (Required for all)
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("যোগাযোগের মোবাইল নম্বর *") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth().testTag("input_phone"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Submitter Name
                OutlinedTextField(
                    value = submitterName,
                    onValueChange = { submitterName = it },
                    label = { Text("আপনার নাম (তথ্য প্রদানকারী)") },
                    modifier = Modifier.fillMaxWidth().testTag("input_submitter_name"),
                    singleLine = true
                )

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Submit Button
                Button(
                    onClick = {
                        if (titleOrName.isBlank() || phone.isBlank()) {
                            errorMessage = "দয়া করে নাম এবং মোবাইল নম্বর সঠিকভাবে পূরণ করুন।"
                        } else {
                            viewModel.submitUserContribution(
                                type = selectedType,
                                titleOrName = titleOrName.trim(),
                                secondary = secondaryInfo.trim(),
                                category = category.trim(),
                                phone = phone.trim(),
                                details = details.trim(),
                                submittedBy = submitterName.trim()
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = UnionGreenPrimary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("submit_contribution_button")
                ) {
                    Text(
                        text = "যাচাইয়ের জন্য জমা দিন",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
