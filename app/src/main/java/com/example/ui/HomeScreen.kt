package com.example.ui

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.HolidayVillage
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Yard
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.EmergencyContact
import com.example.data.model.NoticeItem
import com.example.ui.theme.UnionEmergencyRed
import com.example.ui.theme.UnionEmergencyRedLight
import com.example.ui.theme.UnionGreenPrimary
import com.example.viewmodel.UnionViewModel

data class UniversalSearchResult(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String,
    val categoryColor: Color,
    val phoneNumber: String,
    val location: String = ""
)

@Composable
fun HomeScreen(
    viewModel: UnionViewModel,
    context: Context,
    onNavigateToTab: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val emergencyContacts by viewModel.emergencyContacts.collectAsState()
    val notices by viewModel.notices.collectAsState()
    val educationalInstitutes by viewModel.educationalInstitutes.collectAsState()
    val villages by viewModel.villages.collectAsState()
    val officials by viewModel.parishadOfficials.collectAsState()
    val doctors by viewModel.doctors.collectAsState()
    val bloodDonors by viewModel.bloodDonors.collectAsState()
    val marketMembers by viewModel.marketMembers.collectAsState()
    val shops by viewModel.shops.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    // Real-time search across ALL data by Name (নাম) OR Phone Number (নাম্বার)
    val searchResults = remember(
        searchQuery, officials, educationalInstitutes, doctors,
        bloodDonors, shops, marketMembers, emergencyContacts, villages
    ) {
        val q = searchQuery.trim()
        val cleanDigits = q.replace(Regex("[^0-9]"), "")

        if (q.isBlank()) {
            emptyList()
        } else {
            val list = mutableListOf<UniversalSearchResult>()

            // 1. Search in Parishad Officials
            officials.forEach { official ->
                val matchName = official.name.contains(q, ignoreCase = true) ||
                                official.designation.contains(q, ignoreCase = true) ||
                                official.villageAddress.contains(q, ignoreCase = true)
                val matchPhone = cleanDigits.isNotEmpty() && official.phoneNumber.replace(Regex("[^0-9]"), "").contains(cleanDigits)
                if (matchName || matchPhone) {
                    list.add(
                        UniversalSearchResult(
                            id = "official_${official.id}",
                            title = official.name,
                            subtitle = official.designation,
                            category = "ইউপি পরিষদ",
                            categoryColor = Color(0xFF00796B),
                            phoneNumber = official.phoneNumber,
                            location = "${official.villageAddress} (${official.wardOrArea})"
                        )
                    )
                }
            }

            // 2. Search in Educational Institutes
            educationalInstitutes.forEach { edu ->
                val matchName = edu.name.contains(q, ignoreCase = true) ||
                                edu.headName.contains(q, ignoreCase = true) ||
                                edu.villageOrLocation.contains(q, ignoreCase = true) ||
                                edu.type.contains(q, ignoreCase = true)
                val matchPhone = cleanDigits.isNotEmpty() && edu.phoneNumber.replace(Regex("[^0-9]"), "").contains(cleanDigits)
                if (matchName || matchPhone) {
                    list.add(
                        UniversalSearchResult(
                            id = "edu_${edu.id}",
                            title = edu.name,
                            subtitle = "${edu.type} • ${edu.headName}",
                            category = "শিক্ষাপ্রতিষ্ঠান",
                            categoryColor = Color(0xFF1565C0),
                            phoneNumber = edu.phoneNumber,
                            location = edu.villageOrLocation
                        )
                    )
                }
            }

            // 3. Search in Doctors
            doctors.forEach { doc ->
                val matchName = doc.name.contains(q, ignoreCase = true) ||
                                doc.specialty.contains(q, ignoreCase = true) ||
                                doc.chamberOrHospital.contains(q, ignoreCase = true)
                val matchPhone = cleanDigits.isNotEmpty() && doc.phoneNumber.replace(Regex("[^0-9]"), "").contains(cleanDigits)
                if (matchName || matchPhone) {
                    list.add(
                        UniversalSearchResult(
                            id = "doc_${doc.id}",
                            title = doc.name,
                            subtitle = "${doc.specialty} (${doc.degrees})",
                            category = "ডাক্তার ও স্বাস্থ্য",
                            categoryColor = Color(0xFFD32F2F),
                            phoneNumber = doc.phoneNumber,
                            location = doc.chamberOrHospital
                        )
                    )
                }
            }

            // 4. Search in Blood Donors
            bloodDonors.forEach { donor ->
                val matchName = donor.name.contains(q, ignoreCase = true) ||
                                donor.villageOrWard.contains(q, ignoreCase = true) ||
                                donor.bloodGroup.contains(q, ignoreCase = true)
                val matchPhone = cleanDigits.isNotEmpty() && donor.phoneNumber.replace(Regex("[^0-9]"), "").contains(cleanDigits)
                if (matchName || matchPhone) {
                    list.add(
                        UniversalSearchResult(
                            id = "donor_${donor.id}",
                            title = donor.name,
                            subtitle = "রক্তের গ্রুপ: ${donor.bloodGroup} • ${if (donor.isAvailable) "রক্তদানে প্রস্তুত" else "সম্প্রতি দিয়েছেন"}",
                            category = "রক্তদাতা (${donor.bloodGroup})",
                            categoryColor = Color(0xFFC2185B),
                            phoneNumber = donor.phoneNumber,
                            location = donor.villageOrWard
                        )
                    )
                }
            }

            // 5. Search in Shops
            shops.forEach { shop ->
                val matchName = shop.shopName.contains(q, ignoreCase = true) ||
                                shop.ownerName.contains(q, ignoreCase = true) ||
                                shop.category.contains(q, ignoreCase = true) ||
                                shop.shopNumberOrLane.contains(q, ignoreCase = true)
                val matchPhone = cleanDigits.isNotEmpty() && shop.phoneNumber.replace(Regex("[^0-9]"), "").contains(cleanDigits)
                if (matchName || matchPhone) {
                    list.add(
                        UniversalSearchResult(
                            id = "shop_${shop.id}",
                            title = shop.shopName,
                            subtitle = "মালিক: ${shop.ownerName} • ${shop.category}",
                            category = "সানন্দবাড়ী বাজার দোকান",
                            categoryColor = Color(0xFFE65100),
                            phoneNumber = shop.phoneNumber,
                            location = shop.shopNumberOrLane
                        )
                    )
                }
            }

            // 6. Search in Market Committee Members
            marketMembers.forEach { member ->
                val matchName = member.name.contains(q, ignoreCase = true) ||
                                member.designation.contains(q, ignoreCase = true) ||
                                member.shopName.contains(q, ignoreCase = true)
                val matchPhone = cleanDigits.isNotEmpty() && member.phoneNumber.replace(Regex("[^0-9]"), "").contains(cleanDigits)
                if (matchName || matchPhone) {
                    list.add(
                        UniversalSearchResult(
                            id = "member_${member.id}",
                            title = member.name,
                            subtitle = "${member.designation} • ${member.marketName}",
                            category = "বাজার কমিটি",
                            categoryColor = Color(0xFF1565C0),
                            phoneNumber = member.phoneNumber,
                            location = member.shopName
                        )
                    )
                }
            }

            // 7. Search in Emergency Contacts
            emergencyContacts.forEach { contact ->
                val matchName = contact.title.contains(q, ignoreCase = true) ||
                                contact.subtitle.contains(q, ignoreCase = true) ||
                                contact.location.contains(q, ignoreCase = true)
                val matchPhone = cleanDigits.isNotEmpty() && contact.phoneNumber.replace(Regex("[^0-9]"), "").contains(cleanDigits)
                if (matchName || matchPhone) {
                    list.add(
                        UniversalSearchResult(
                            id = "contact_${contact.id}",
                            title = contact.title,
                            subtitle = contact.subtitle,
                            category = "জরুরি সেবা",
                            categoryColor = Color(0xFFD32F2F),
                            phoneNumber = contact.phoneNumber,
                            location = contact.location
                        )
                    )
                }
            }

            // 8. Search in Villages
            villages.forEach { village ->
                val matchName = village.villageName.contains(q, ignoreCase = true) ||
                                village.wardNumber.contains(q, ignoreCase = true) ||
                                village.landmarksOrDescription.contains(q, ignoreCase = true)
                if (matchName) {
                    list.add(
                        UniversalSearchResult(
                            id = "village_${village.id}",
                            title = village.villageName,
                            subtitle = "${village.wardNumber} • পরিবার: ${village.householdCount} • জনসংখ্যা: ${village.approximatePopulation}",
                            category = "গ্রাম",
                            categoryColor = Color(0xFF2E7D32),
                            phoneNumber = "",
                            location = village.landmarksOrDescription
                        )
                    )
                }
            }

            list
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen_lazy_column"),
        contentPadding = PaddingValues(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Banner with Sanandabari Info
        item {
            HeroHeaderSection()
        }

        // UNIVERSAL SEARCH BAR: নাম অথবা মোবাইল নাম্বার দিয়ে অনুসন্ধান
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                SearchBox(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    placeholder = "নাম অথবা মোবাইল নাম্বার দিয়ে খুঁজুন..."
                )

                if (searchQuery.isNotBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "অনুসন্ধানের ফলাফল: ${searchResults.size} টি পাওয়া গেছে",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // If user is searching, display the Universal Search Results
        if (searchQuery.isNotBlank()) {
            if (searchResults.isEmpty()) {
                item {
                    EmptyView(
                        message = "'$searchQuery' নাম অথবা নাম্বার দিয়ে কোন তথ্য পাওয়া যায়নি। দয়া করে সঠিক বানান বা মোবাইল নম্বর দিয়ে চেষ্টা করুন।"
                    )
                }
            } else {
                items(searchResults, key = { it.id }) { result ->
                    UniversalSearchResultCard(
                        result = result,
                        context = context,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        } else {
            // Normal Home Dashboard when not searching:

            // Quick Emergency 1-tap call strip
            item {
                QuickEmergencyStrip(
                    context = context,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // ALL CATEGORIES IN ONE PLACE (সব গুলা ক্যাটাগরি এক জায়গায়)
            item {
                AllCategoriesHub(
                    eduCount = educationalInstitutes.size,
                    villageCount = villages.size,
                    officialCount = officials.size,
                    doctorCount = doctors.size,
                    donorCount = bloodDonors.size,
                    shopCount = shops.size,
                    onNavigateToTab = onNavigateToTab
                )
            }

            // Urgent Notice / News Feed
            item {
                NoticeFeedSection(notices = notices)
            }

            // Emergency Contacts Directory
            item {
                EmergencyDirectorySection(
                    contacts = emergencyContacts,
                    context = context
                )
            }

            // Sanandabari & Char Amkhawa Union Overview
            item {
                SanandabariOverviewCard(
                    villageCount = villages.size,
                    eduCount = educationalInstitutes.size
                )
            }

            // DISCREET ADMIN LOGIN (এডমিন প্যানেল একটু আড়ালে)
            item {
                DiscreetAdminFooter(
                    onOpenAdmin = { viewModel.openAdminDialog() }
                )
            }
        }
    }
}

/**
 * Universal Search Result Card showing Name, Category, Phone, Location and 1-tap Call
 */
@Composable
fun UniversalSearchResultCard(
    result: UniversalSearchResult,
    context: Context,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("search_result_${result.id}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Surface(
                    color = result.categoryColor.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Text(
                        text = result.category,
                        color = result.categoryColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Text(
                    text = result.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = result.subtitle,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
                )

                if (result.location.isNotBlank()) {
                    Text(
                        text = result.location,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }

                if (result.phoneNumber.isNotBlank()) {
                    Text(
                        text = "মোবাইল: ${result.phoneNumber}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (result.phoneNumber.isNotBlank()) {
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { DialHelper.makePhoneCall(context, result.phoneNumber) },
                    colors = ButtonDefaults.buttonColors(containerColor = result.categoryColor),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Call",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("কল", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

/**
 * DISCREET ADMIN FOOTER (এডমিন প্যানেল লগইন সুবিধা যাতে সবাই সহজে না দেখে একটু আড়ালে থাকে)
 */
@Composable
fun DiscreetAdminFooter(
    onOpenAdmin: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "আমাদের সানন্দবাড়ী • চর আমখাওয়া ইউনিয়ন পরিষদ",
            fontSize = 11.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Text(
            text = "দেওয়ানগঞ্জ থানা • জামালপুর জেলা",
            fontSize = 10.sp,
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Discreet subtle link for Admin
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable(onClick = onOpenAdmin)
                .padding(horizontal = 10.dp, vertical = 6.dp)
                .testTag("discreet_admin_login_button"),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = Color.Gray.copy(alpha = 0.6f),
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "ইউপি প্রশাসন ও রক্ষণাবেক্ষণ প্রবেশদ্বার",
                fontSize = 11.sp,
                color = Color.Gray.copy(alpha = 0.8f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun HeroHeaderSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_union_banner),
                contentDescription = "Sanandabari Banner",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.88f)
                            ),
                            startY = 60f
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Surface(
                    color = Color(0xFF0A4D2E),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Text(
                        text = "চর আমখাওয়া (আধখাওয়া) ইউনিয়ন",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Text(
                    text = "আমাদের সানন্দবাড়ী",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "দেওয়ানগঞ্জ থানা • জামালপুর জেলা • জিনজিরাম নদী ঘাট",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

/**
 * ALL CATEGORIES IN ONE PLACE (সব গুলা ক্যাটাগরি এক জায়গায় রাখুন)
 */
@Composable
fun AllCategoriesHub(
    eduCount: Int,
    villageCount: Int,
    officialCount: Int,
    doctorCount: Int,
    donorCount: Int,
    shopCount: Int,
    onNavigateToTab: (Int) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp, 20.dp)
                    .background(UnionGreenPrimary, RoundedCornerShape(4.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "সকল ক্যাটাগরি ও সেবা কেন্দ্র",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))
            Surface(
                color = Color(0xFFE8F5E9),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "৮টি প্রধান বিভাগ",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = UnionGreenPrimary,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Row 1: শিক্ষাপ্রতিষ্ঠান & গ্রামসমূহ
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CategoryHubTile(
                title = "শিক্ষাপ্রতিষ্ঠান",
                subtitle = "কলেজ, হাইস্কুল, প্রাইমারি, মাদ্রাসা",
                badge = "$eduCount টি",
                icon = Icons.Default.School,
                color = Color(0xFF1565C0),
                modifier = Modifier.weight(1f),
                onClick = { onNavigateToTab(1) }
            )
            CategoryHubTile(
                title = "ইউনিয়নের গ্রামসমূহ",
                subtitle = "১-৯ নং ওয়ার্ডের সকল গ্রাম",
                badge = "$villageCount টি গ্রাম",
                icon = Icons.Default.HolidayVillage,
                color = Color(0xFF2E7D32),
                modifier = Modifier.weight(1f),
                onClick = { onNavigateToTab(2) }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Row 2: ইউপি পরিষদ ও কর্মী & ডাক্তার ও স্বাস্থ্যসেবা
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CategoryHubTile(
                title = "ইউপি পরিষদ ও কর্মী",
                subtitle = "চেয়ারম্যান, মেম্বার, সচিব, গ্রাম পুলিশ",
                badge = "$officialCount জন",
                icon = Icons.Default.AccountBalance,
                color = Color(0xFF00796B),
                modifier = Modifier.weight(1f),
                onClick = { onNavigateToTab(3) }
            )
            CategoryHubTile(
                title = "ডাক্তার ও স্বাস্থ্য",
                subtitle = "বিশেষজ্ঞ ডাক্তার, ক্লিনিক ও চেম্বার",
                badge = "$doctorCount জন",
                icon = Icons.Default.LocalHospital,
                color = Color(0xFFD32F2F),
                modifier = Modifier.weight(1f),
                onClick = { onNavigateToTab(4) }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Row 3: রক্তদাতা তালিকা & সানন্দবাড়ী বাজার ও দোকান
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CategoryHubTile(
                title = "জরুরি রক্তদাতা",
                subtitle = "সকল রক্তের গ্রুপ ও ডোনার",
                badge = "$donorCount জন",
                icon = Icons.Default.WaterDrop,
                color = Color(0xFFC2185B),
                modifier = Modifier.weight(1f),
                onClick = { onNavigateToTab(5) }
            )
            CategoryHubTile(
                title = "সানন্দবাড়ী বাজার",
                subtitle = "দোকানের তালিকা ও বণিক সমিতি",
                badge = "$shopCount+ দোকান",
                icon = Icons.Default.Storefront,
                color = Color(0xFFE65100),
                modifier = Modifier.weight(1f),
                onClick = { onNavigateToTab(6) }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Row 4: কৃষি পরামর্শ & জরুরি সেবা
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CategoryHubTile(
                title = "কৃষি পরামর্শ",
                subtitle = "ভুট্টা, বাদাম, ধান ও কৃষি কর্মকর্তা",
                badge = "সেবা চালু",
                icon = Icons.Default.Yard,
                color = Color(0xFF558B2F),
                modifier = Modifier.weight(1f),
                onClick = { onNavigateToTab(7) }
            )
            CategoryHubTile(
                title = "জরুরি পুলিশ ও সেবা",
                subtitle = "সানন্দবাড়ী তদন্ত কেন্দ্র, অ্যাম্বুলেন্স",
                badge = "২৪ ঘণ্টা",
                icon = Icons.Default.Phone,
                color = Color(0xFF37474F),
                modifier = Modifier.weight(1f),
                onClick = { onNavigateToTab(0) }
            )
        }
    }
}

@Composable
fun CategoryHubTile(
    title: String,
    subtitle: String,
    badge: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("hub_tile_$title"),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.08f)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(color, RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Surface(
                    color = color.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = badge,
                        color = color,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = Color.Gray,
                maxLines = 1,
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
fun NoticeFeedSection(notices: List<NoticeItem>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Campaign,
                contentDescription = "Notices",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "সানন্দবাড়ী জরুরি ঘোষণা ও নোটিশ",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape
            ) {
                Text(
                    text = "${notices.size} টি নোটিশ",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (notices.isEmpty()) {
            EmptyView(message = "বর্তমানে কোন জরুরি ঘোষণা নেই")
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                notices.forEach { notice ->
                    NoticeItemCard(notice = notice)
                }
            }
        }
    }
}

@Composable
fun NoticeItemCard(notice: NoticeItem) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { expanded = !expanded }
            .testTag("notice_card_${notice.id}"),
        colors = CardDefaults.cardColors(
            containerColor = if (notice.isUrgent) UnionEmergencyRedLight else MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (notice.isUrgent) {
                    Surface(
                        color = UnionEmergencyRed,
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = "জরুরি",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = notice.category,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = notice.date,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = "Expand",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = notice.title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = if (notice.isUrgent) UnionEmergencyRed else MaterialTheme.colorScheme.onSurface
            )

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Text(
                        text = notice.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "প্রচারক: ${notice.publisher}",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun EmergencyDirectorySection(
    contacts: List<EmergencyContact>,
    context: Context
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "Contacts",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "সানন্দবাড়ী জরুরি যোগাযোগ নম্বর",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            contacts.take(5).forEach { contact ->
                EmergencyContactRow(contact = contact, context = context)
            }
        }
    }
}

@Composable
fun EmergencyContactRow(
    contact: EmergencyContact,
    context: Context
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("contact_row_${contact.id}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contact.title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = contact.subtitle,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
                Text(
                    text = "${contact.phoneNumber} • ${contact.availableHours}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }

            Button(
                onClick = { DialHelper.makePhoneCall(context, contact.phoneNumber) },
                colors = ButtonDefaults.buttonColors(containerColor = UnionGreenPrimary),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                modifier = Modifier.testTag("call_btn_${contact.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call",
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("কল", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun SanandabariOverviewCard(villageCount: Int, eduCount: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "এক নজরে চর আমখাওয়া ইউনিয়ন ও সানন্দবাড়ী",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem(title = "ওয়ার্ড সংখ্যা", value = "৯ টি")
                StatItem(title = "গ্রাম", value = "$villageCount টি")
                StatItem(title = "জনসংখ্যা", value = "৩২,০০০+")
                StatItem(title = "শিক্ষাপ্রতিষ্ঠান", value = "$eduCount টি")
                StatItem(title = "ঐতিহাসিক হাট", value = "সানন্দবাড়ী")
            }
        }
    }
}

@Composable
fun StatItem(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = title,
            fontSize = 10.sp,
            color = Color.Gray
        )
    }
}
