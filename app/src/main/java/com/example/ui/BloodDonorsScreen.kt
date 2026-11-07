package com.example.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BloodDonor
import com.example.ui.theme.UnionEmergencyRed
import com.example.ui.theme.UnionEmergencyRedLight
import com.example.ui.theme.UnionGreenPrimary
import com.example.viewmodel.UnionViewModel

@Composable
fun BloodDonorsScreen(
    viewModel: UnionViewModel,
    context: Context,
    modifier: Modifier = Modifier
) {
    val donors by viewModel.bloodDonors.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedBloodGroup by viewModel.selectedBloodGroup.collectAsState()

    val bloodGroups = listOf("সকল", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")

    val filteredDonors = donors.filter { donor ->
        val matchesGroup = if (selectedBloodGroup == "সকল") true else donor.bloodGroup == selectedBloodGroup
        val matchesQuery = if (searchQuery.isBlank()) true else {
            val cleanDigits = searchQuery.replace(Regex("[^0-9]"), "")
            donor.name.contains(searchQuery, ignoreCase = true) ||
            (cleanDigits.isNotEmpty() && donor.phoneNumber.replace(Regex("[^0-9]"), "").contains(cleanDigits)) ||
            donor.villageOrWard.contains(searchQuery, ignoreCase = true) ||
            donor.bloodGroup.contains(searchQuery, ignoreCase = true)
        }
        matchesGroup && matchesQuery
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("blood_donors_screen_column")
    ) {
        // Search and Blood Group Chips
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            SearchBox(
                query = searchQuery,
                onQueryChange = { viewModel.setSearchQuery(it) },
                placeholder = "রক্তদাতার নাম বা মোবাইল নম্বর দিয়ে খুঁজুন..."
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Blood Group Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                bloodGroups.forEach { group ->
                    FilterChip(
                        selected = selectedBloodGroup == group,
                        onClick = { viewModel.setSelectedBloodGroup(group) },
                        label = { Text(group, fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = UnionEmergencyRed,
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.testTag("chip_blood_$group")
                    )
                }
            }
        }

        // Available count banner
        val availableCount = filteredDonors.count { it.isAvailable }
        Surface(
            color = UnionEmergencyRedLight,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.WaterDrop,
                    contentDescription = null,
                    tint = UnionEmergencyRed,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "মোট রক্তদাতা: ${filteredDonors.size} জন (রক্তদানে প্রস্তুত: $availableCount জন)",
                    fontSize = 12.sp,
                    color = UnionEmergencyRed,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        if (filteredDonors.isEmpty()) {
            EmptyView(message = "এই গ্রুপের কোন রক্তদাতা পাওয়া যায়নি। আপনি নতুন রক্তদাতা যোগ করতে পারেন।")
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredDonors, key = { it.id }) { donor ->
                    BloodDonorCard(
                        donor = donor,
                        context = context,
                        onToggleAvailability = { viewModel.toggleDonorAvailability(donor) }
                    )
                }
            }
        }
    }
}

@Composable
fun BloodDonorCard(
    donor: BloodDonor,
    context: Context,
    onToggleAvailability: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("donor_card_${donor.id}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Blood group badge
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(UnionEmergencyRed, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = donor.bloodGroup,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = donor.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        if (donor.isAvailable) {
                            Surface(
                                color = Color(0xFFE8F5E9),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Color(0xFF2E7D32),
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "প্রস্তুত",
                                        color = Color(0xFF2E7D32),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        } else {
                            Surface(
                                color = Color(0xFFFFEBEE),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "সম্প্রতি রক্ত দিয়েছেন",
                                    color = Color(0xFFC62828),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = donor.villageOrWard,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "শেষ রক্তদান: ${donor.lastDonationDate}",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons: Call, SMS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { DialHelper.makePhoneCall(context, donor.phoneNumber) },
                    colors = ButtonDefaults.buttonColors(containerColor = UnionEmergencyRed),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("call_donor_${donor.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Call Donor",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "সরাসরি কল (${donor.phoneNumber})",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                OutlinedButton(
                    onClick = {
                        DialHelper.sendSms(
                            context = context,
                            phoneNumber = donor.phoneNumber,
                            body = "আসসালামু আলাইকুম, আমাদের একজন রোগীর জন্য জরুরি ${donor.bloodGroup} রক্তের প্রয়োজন। আপনি কি রক্তদানে সহায়তা করতে পারবেন?"
                        )
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("sms_donor_${donor.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Message,
                        contentDescription = "SMS",
                        tint = UnionEmergencyRed,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("এসএমএস", fontSize = 12.sp, color = UnionEmergencyRed)
                }
            }
        }
    }
}
