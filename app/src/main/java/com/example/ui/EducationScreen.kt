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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
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
import com.example.data.model.EducationalInstitute
import com.example.ui.theme.UnionGreenPrimary
import com.example.viewmodel.UnionViewModel

@Composable
fun EducationScreen(
    viewModel: UnionViewModel,
    context: Context,
    modifier: Modifier = Modifier
) {
    val institutes by viewModel.educationalInstitutes.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedType by viewModel.selectedEducationType.collectAsState()

    val types = listOf("সকল", "কলেজ", "উচ্চ বিদ্যালয়", "প্রাথমিক বিদ্যালয়", "মাদ্রাসা")

    val filteredList = institutes.filter { inst ->
        val matchesType = when (selectedType) {
            "সকল" -> true
            "কলেজ" -> inst.type.contains("কলেজ")
            "উচ্চ বিদ্যালয়" -> inst.type.contains("উচ্চ")
            "প্রাথমিক বিদ্যালয়" -> inst.type.contains("প্রাথমিক")
            "মাদ্রাসা" -> inst.type.contains("মাদ্রাসা")
            else -> true
        }
        val matchesQuery = if (searchQuery.isBlank()) true else {
            val cleanDigits = searchQuery.replace(Regex("[^0-9]"), "")
            inst.name.contains(searchQuery, ignoreCase = true) ||
            inst.headName.contains(searchQuery, ignoreCase = true) ||
            (cleanDigits.isNotEmpty() && inst.phoneNumber.replace(Regex("[^0-9]"), "").contains(cleanDigits)) ||
            inst.villageOrLocation.contains(searchQuery, ignoreCase = true) ||
            inst.type.contains(searchQuery, ignoreCase = true)
        }
        matchesType && matchesQuery
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("education_screen_column")
    ) {
        // Search & Filters
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            SearchBox(
                query = searchQuery,
                onQueryChange = { viewModel.setSearchQuery(it) },
                placeholder = "বিদ্যালয়, কলেজ বা ফোন নম্বর দিয়ে খুঁজুন..."
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                types.forEach { type ->
                    FilterChip(
                        selected = selectedType == type,
                        onClick = { viewModel.setSelectedEducationType(type) },
                        label = { Text(type, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF1E88E5),
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.testTag("chip_edu_$type")
                    )
                }
            }
        }

        // Summary Count
        Surface(
            color = Color(0xFFE3F2FD),
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
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = Color(0xFF1565C0),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "চর আমখাওয়া (সানন্দবাড়ী) ইউনিয়নের মোট শিক্ষাপ্রতিষ্ঠান: ${filteredList.size} টি",
                    fontSize = 12.sp,
                    color = Color(0xFF0D47A1),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        if (filteredList.isEmpty()) {
            EmptyView(message = "কোন শিক্ষাপ্রতিষ্ঠান পাওয়া যায়নি")
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredList, key = { it.id }) { institute ->
                    EducationalInstituteCard(institute = institute, context = context)
                }
            }
        }
    }
}

@Composable
fun EducationalInstituteCard(
    institute: EducationalInstitute,
    context: Context
) {
    val typeColor = when {
        institute.type.contains("কলেজ") -> Color(0xFF6A1B9A)
        institute.type.contains("উচ্চ") -> Color(0xFF1565C0)
        institute.type.contains("মাদ্রাসা") -> Color(0xFF00796B)
        else -> Color(0xFF2E7D32)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("edu_card_${institute.id}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .background(typeColor.copy(alpha = 0.12f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "School",
                        tint = typeColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = institute.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Surface(
                        color = typeColor.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = institute.type,
                            color = typeColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Head / Principal
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = institute.headName,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                )
            }

            // Location
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = institute.villageOrLocation,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            // Established Year
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "স্থাপিত: ${institute.establishedYear} খ্রিঃ",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            if (institute.totalStudentsOrInfo.isNotBlank()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = institute.totalStudentsOrInfo,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Call Button
            Button(
                onClick = { DialHelper.makePhoneCall(context, institute.phoneNumber) },
                colors = ButtonDefaults.buttonColors(containerColor = typeColor),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("call_edu_${institute.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call Institute",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "প্রতিষ্ঠান প্রধানকে কল (${institute.phoneNumber})",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
