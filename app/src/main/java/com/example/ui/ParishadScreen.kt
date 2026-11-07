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
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ParishadOfficial
import com.example.ui.theme.UnionGreenPrimary
import com.example.viewmodel.UnionViewModel

@Composable
fun ParishadScreen(
    viewModel: UnionViewModel,
    context: Context,
    modifier: Modifier = Modifier
) {
    val officials by viewModel.parishadOfficials.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedRole by viewModel.selectedOfficialRole.collectAsState()

    val roles = listOf(
        "সকল", "চেয়ারম্যান ও সচিব", "ইউপি সদস্য", "সংরক্ষিত মহিলা সদস্য",
        "ডিজিটাল সেন্টার", "গ্রাম পুলিশ", "স্বাস্থ্য ও পরিবার কল্যাণ"
    )

    val filteredOfficials = officials.filter { official ->
        val matchesRole = when (selectedRole) {
            "সকল" -> true
            "চেয়ারম্যান ও সচিব" -> official.roleType.contains("চেয়ারম্যান") || official.roleType.contains("সচিব")
            "ইউপি সদস্য" -> official.roleType == "ইউপি সদস্য"
            "সংরক্ষিত মহিলা সদস্য" -> official.roleType.contains("মহিলা")
            "ডিজিটাল সেন্টার" -> official.roleType.contains("ডিজিটাল")
            "গ্রাম পুলিশ" -> official.roleType.contains("পুলিশ")
            "স্বাস্থ্য ও পরিবার কল্যাণ" -> official.roleType.contains("স্বাস্থ্য")
            else -> true
        }
        val matchesQuery = if (searchQuery.isBlank()) true else {
            val cleanDigits = searchQuery.replace(Regex("[^0-9]"), "")
            official.name.contains(searchQuery, ignoreCase = true) ||
            (cleanDigits.isNotEmpty() && official.phoneNumber.replace(Regex("[^0-9]"), "").contains(cleanDigits)) ||
            official.designation.contains(searchQuery, ignoreCase = true) ||
            official.wardOrArea.contains(searchQuery, ignoreCase = true) ||
            official.villageAddress.contains(searchQuery, ignoreCase = true)
        }
        matchesRole && matchesQuery
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("parishad_screen_column")
    ) {
        // Search & Role Filters
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            SearchBox(
                query = searchQuery,
                onQueryChange = { viewModel.setSearchQuery(it) },
                placeholder = "মেম্বার, সচিবের নাম বা মোবাইল নম্বর দিয়ে খুঁজুন..."
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                roles.forEach { role ->
                    FilterChip(
                        selected = selectedRole == role,
                        onClick = { viewModel.setSelectedOfficialRole(role) },
                        label = { Text(role, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF00796B),
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.testTag("chip_role_$role")
                    )
                }
            }
        }

        // Summary Banner
        Surface(
            color = Color(0xFFE0F2F1),
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
                    imageVector = Icons.Default.AccountBalance,
                    contentDescription = null,
                    tint = Color(0xFF004D40),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "চর আমখাওয়া ইউপি পরিষদ সদস্য ও কর্মকর্তা: ${filteredOfficials.size} জন",
                    fontSize = 12.sp,
                    color = Color(0xFF004D40),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        if (filteredOfficials.isEmpty()) {
            EmptyView(message = "কোন সদস্য বা কর্মকর্তার তথ্য পাওয়া যায়নি")
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredOfficials, key = { it.id }) { official ->
                    OfficialCard(official = official, context = context)
                }
            }
        }
    }
}

@Composable
fun OfficialCard(
    official: ParishadOfficial,
    context: Context
) {
    val roleColor = when {
        official.roleType.contains("চেয়ারম্যান") -> Color(0xFF1B5E20)
        official.roleType.contains("সচিব") -> Color(0xFF0D47A1)
        official.roleType.contains("মহিলা") -> Color(0xFFC2185B)
        official.roleType.contains("ডিজিটাল") -> Color(0xFFE65100)
        official.roleType.contains("পুলিশ") -> Color(0xFF37474F)
        official.roleType.contains("স্বাস্থ্য") -> Color(0xFF00796B)
        else -> Color(0xFF2E7D32)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("official_card_${official.id}"),
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
                        .background(roleColor.copy(alpha = 0.12f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (official.roleType.contains("পুলিশ")) Icons.Default.Security else Icons.Default.Badge,
                        contentDescription = "Official",
                        tint = roleColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = official.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Surface(
                        color = roleColor.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = official.designation,
                            color = roleColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Address & Ward
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "ঠিকানা: ${official.villageAddress} (${official.wardOrArea})",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                    fontWeight = FontWeight.Medium
                )
            }

            // Duty details
            if (official.dutyTime.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "সেবার বিবরণ: ${official.dutyTime}",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Direct Call Button
            Button(
                onClick = { DialHelper.makePhoneCall(context, official.phoneNumber) },
                colors = ButtonDefaults.buttonColors(containerColor = roleColor),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("call_official_${official.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call Official",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "সরাসরি যোগাযোগ ও কল (${official.phoneNumber})",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
