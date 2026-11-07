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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.data.model.DoctorInfo
import com.example.ui.theme.UnionGreenPrimary
import com.example.viewmodel.UnionViewModel

@Composable
fun DoctorsScreen(
    viewModel: UnionViewModel,
    context: Context,
    modifier: Modifier = Modifier
) {
    val doctors by viewModel.doctors.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedSpecialty by viewModel.selectedDoctorSpecialty.collectAsState()

    val specialties = listOf("সকল", "মেডিসিন", "শিশু রোগ", "স্ত্রী ও প্রসূতি", "দন্ত", "পল্লী চিকিৎসক")

    val filteredDoctors = doctors.filter { doctor ->
        val matchesSpecialty = if (selectedSpecialty == "সকল") true else doctor.specialty.contains(selectedSpecialty)
        val matchesQuery = if (searchQuery.isBlank()) true else {
            val cleanDigits = searchQuery.replace(Regex("[^0-9]"), "")
            doctor.name.contains(searchQuery, ignoreCase = true) ||
            (cleanDigits.isNotEmpty() && doctor.phoneNumber.replace(Regex("[^0-9]"), "").contains(cleanDigits)) ||
            doctor.specialty.contains(searchQuery, ignoreCase = true) ||
            doctor.chamberOrHospital.contains(searchQuery, ignoreCase = true) ||
            doctor.location.contains(searchQuery, ignoreCase = true)
        }
        matchesSpecialty && matchesQuery
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("doctors_screen_column")
    ) {
        // Search and Filters
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            SearchBox(
                query = searchQuery,
                onQueryChange = { viewModel.setSearchQuery(it) },
                placeholder = "ডাক্তারের নাম বা মোবাইল নম্বর দিয়ে খুঁজুন..."
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Specialty Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                specialties.forEach { spec ->
                    FilterChip(
                        selected = selectedSpecialty == spec,
                        onClick = { viewModel.setSelectedDoctorSpecialty(spec) },
                        label = { Text(spec, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = UnionGreenPrimary,
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.testTag("chip_doctor_$spec")
                    )
                }
            }
        }

        // List of Doctors
        if (filteredDoctors.isEmpty()) {
            EmptyView(message = "কোন ডাক্তারের তথ্য পাওয়া যায়নি। আপনি নতুন ডাক্তার যোগ করতে পারেন।")
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredDoctors, key = { it.id }) { doctor ->
                    DoctorCard(doctor = doctor, context = context)
                }
            }
        }
    }
}

@Composable
fun DoctorCard(
    doctor: DoctorInfo,
    context: Context
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("doctor_card_${doctor.id}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .background(Color(0xFF00796B).copy(alpha = 0.12f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MedicalServices,
                        contentDescription = "Doctor",
                        tint = Color(0xFF00796B),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = doctor.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Surface(
                        color = Color(0xFFE0F2F1),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = doctor.specialty,
                            color = Color(0xFF00695C),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = doctor.degrees,
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Chamber & Hospital
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${doctor.chamberOrHospital} (${doctor.location})",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Visiting hours
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "সময়সূচী: ${doctor.visitingHours}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Fee
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Payments,
                    contentDescription = null,
                    tint = Color(0xFF2E7D32),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "ভিজিট: ${doctor.consultationFee}",
                    fontSize = 12.sp,
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Direct Call Button
            Button(
                onClick = { DialHelper.makePhoneCall(context, doctor.phoneNumber) },
                colors = ButtonDefaults.buttonColors(containerColor = UnionGreenPrimary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("call_doctor_btn_${doctor.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call Doctor",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "সিরিয়াল ও পরামর্শের জন্য কল করুন (${doctor.phoneNumber})",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
