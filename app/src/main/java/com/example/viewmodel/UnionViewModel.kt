package com.example.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.model.BloodDonor
import com.example.data.model.DoctorInfo
import com.example.data.model.EducationalInstitute
import com.example.data.model.EmergencyContact
import com.example.data.model.KrishiAdvice
import com.example.data.model.MarketMember
import com.example.data.model.NoticeItem
import com.example.data.model.ParishadOfficial
import com.example.data.model.ShopInfo
import com.example.data.model.UserSubmission
import com.example.data.model.VillageInfo
import com.example.data.repository.UnionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UnionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UnionRepository
    private val prefs = application.getSharedPreferences("sanandabari_prefs", Context.MODE_PRIVATE)

    val emergencyContacts: StateFlow<List<EmergencyContact>>
    val educationalInstitutes: StateFlow<List<EducationalInstitute>>
    val villages: StateFlow<List<VillageInfo>>
    val parishadOfficials: StateFlow<List<ParishadOfficial>>
    val doctors: StateFlow<List<DoctorInfo>>
    val bloodDonors: StateFlow<List<BloodDonor>>
    val marketMembers: StateFlow<List<MarketMember>>
    val shops: StateFlow<List<ShopInfo>>
    val krishiAdvices: StateFlow<List<KrishiAdvice>>
    val notices: StateFlow<List<NoticeItem>>
    val submissions: StateFlow<List<UserSubmission>>

    // Navigation and UI state
    // Tabs: 0: হোম ও ক্যাটাগরি, 1: শিক্ষাপ্রতিষ্ঠান, 2: গ্রামসমূহ, 3: ইউপি পরিষদ ও কর্মী, 4: ডাক্তার ও স্বাস্থ্য, 5: রক্তদাতা, 6: সানন্দবাড়ী বাজার, 7: কৃষি
    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Filters
    private val _selectedEducationType = MutableStateFlow("সকল")
    val selectedEducationType: StateFlow<String> = _selectedEducationType.asStateFlow()

    private val _selectedOfficialRole = MutableStateFlow("সকল")
    val selectedOfficialRole: StateFlow<String> = _selectedOfficialRole.asStateFlow()

    private val _selectedVillageWard = MutableStateFlow("সকল ওয়ার্ড")
    val selectedVillageWard: StateFlow<String> = _selectedVillageWard.asStateFlow()

    private val _selectedBloodGroup = MutableStateFlow("সকল")
    val selectedBloodGroup: StateFlow<String> = _selectedBloodGroup.asStateFlow()

    private val _selectedShopCategory = MutableStateFlow("সকল")
    val selectedShopCategory: StateFlow<String> = _selectedShopCategory.asStateFlow()

    private val _selectedDoctorSpecialty = MutableStateFlow("সকল")
    val selectedDoctorSpecialty: StateFlow<String> = _selectedDoctorSpecialty.asStateFlow()

    private val _marketSubTab = MutableStateFlow(0) // 0 = Shops, 1 = Market Committee
    val marketSubTab: StateFlow<Int> = _marketSubTab.asStateFlow()

    // Modals
    private val _showAddDialog = MutableStateFlow(false)
    val showAddDialog: StateFlow<Boolean> = _showAddDialog.asStateFlow()

    private val _showAdminDialog = MutableStateFlow(false)
    val showAdminDialog: StateFlow<Boolean> = _showAdminDialog.asStateFlow()

    private val _isAdminLoggedIn = MutableStateFlow(false)
    val isAdminLoggedIn: StateFlow<Boolean> = _isAdminLoggedIn.asStateFlow()

    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        repository = UnionRepository(database.unionDao())

        emergencyContacts = repository.emergencyContacts.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        educationalInstitutes = repository.educationalInstitutes.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        villages = repository.villages.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        parishadOfficials = repository.parishadOfficials.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        doctors = repository.doctors.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        bloodDonors = repository.bloodDonors.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        marketMembers = repository.marketMembers.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        shops = repository.shops.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        krishiAdvices = repository.krishiAdvices.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        notices = repository.notices.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
        submissions = repository.submissions.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
        )
    }

    fun setSelectedTab(tabIndex: Int) {
        _selectedTab.value = tabIndex
        _searchQuery.value = ""
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedEducationType(type: String) {
        _selectedEducationType.value = type
    }

    fun setSelectedOfficialRole(role: String) {
        _selectedOfficialRole.value = role
    }

    fun setSelectedVillageWard(ward: String) {
        _selectedVillageWard.value = ward
    }

    fun setSelectedBloodGroup(group: String) {
        _selectedBloodGroup.value = group
    }

    fun setSelectedShopCategory(category: String) {
        _selectedShopCategory.value = category
    }

    fun setSelectedDoctorSpecialty(specialty: String) {
        _selectedDoctorSpecialty.value = specialty
    }

    fun setMarketSubTab(index: Int) {
        _marketSubTab.value = index
    }

    fun openAddDialog() {
        _showAddDialog.value = true
    }

    fun closeAddDialog() {
        _showAddDialog.value = false
    }

    fun openAdminDialog() {
        _showAdminDialog.value = true
    }

    fun closeAdminDialog() {
        _showAdminDialog.value = false
    }

    fun clearUserMessage() {
        _userMessage.value = null
    }

    // Admin Authentication (default PIN: 1234)
    fun verifyAdminPin(pin: String): Boolean {
        val savedPin = prefs.getString("admin_pin", "1234") ?: "1234"
        val isValid = pin.trim() == savedPin || pin.trim() == "১২৩৪" || pin.trim() == "1234"
        if (isValid) {
            _isAdminLoggedIn.value = true
            _userMessage.value = "অ্যাডমিন হিসেবে সফলভাবে প্রবেশ করেছেন"
        } else {
            _userMessage.value = "ভুল পিন কোড! পুনরায় চেষ্টা করুন"
        }
        return isValid
    }

    fun logoutAdmin() {
        _isAdminLoggedIn.value = false
        _userMessage.value = "অ্যাডমিন প্যানেল থেকে লগআউট হয়েছেন"
    }

    fun updateAdminPin(newPin: String) {
        if (newPin.length >= 4) {
            prefs.edit().putString("admin_pin", newPin).apply()
            _userMessage.value = "অ্যাডমিন পিন সফলভাবে পরিবর্তন করা হয়েছে"
        } else {
            _userMessage.value = "পিন কমপক্ষে ৪ সংখ্যার হতে হবে"
        }
    }

    // User Submissions
    fun submitUserContribution(
        type: String,
        titleOrName: String,
        secondary: String,
        category: String,
        phone: String,
        details: String,
        submittedBy: String
    ) {
        viewModelScope.launch {
            val dateFormat = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
            val currentDate = dateFormat.format(Date())

            val submission = UserSubmission(
                submissionType = type,
                titleOrName = titleOrName,
                secondaryInfo = secondary,
                category = category,
                phoneNumber = phone,
                extraDetails = details,
                submittedBy = submittedBy.ifBlank { "নাগরিক" },
                submittedDate = currentDate,
                status = "PENDING"
            )
            repository.submitUserContribution(submission)
            _showAddDialog.value = false
            _userMessage.value = "আপনার তথ্যটি সফলভাবে জমা দেওয়া হয়েছে। অ্যাডমিন কর্তৃক যাচাইয়ের পর এটি প্রদর্শিত হবে।"
        }
    }

    // Admin Actions
    fun approveSubmission(submission: UserSubmission) {
        viewModelScope.launch {
            repository.approveSubmission(submission)
            _userMessage.value = "তথ্যটি সফলভাবে অনুমোদন দেওয়া হয়েছে এবং মূল তালিকায় যুক্ত হয়েছে!"
        }
    }

    fun rejectSubmission(submission: UserSubmission) {
        viewModelScope.launch {
            repository.rejectSubmission(submission)
            _userMessage.value = "আবেদনটি বাতিল করা হয়েছে।"
        }
    }

    fun deleteSubmission(id: Long) {
        viewModelScope.launch {
            repository.deleteSubmission(id)
            _userMessage.value = "আবেদনটি মুছে ফেলা হয়েছে।"
        }
    }

    private val _fcmToken = MutableStateFlow<String?>(null)
    val fcmToken: StateFlow<String?> = _fcmToken.asStateFlow()

    fun loadFcmToken() {
        val cached = prefs.getString("fcm_device_token", null)
        if (!cached.isNullOrBlank()) {
            _fcmToken.value = cached
        }
        com.example.util.NotificationHelper.fetchFcmToken(getApplication()) { token ->
            _fcmToken.value = token
            prefs.edit().putString("fcm_device_token", token).apply()
        }
    }

    fun postNotice(
        title: String,
        description: String,
        category: String,
        isUrgent: Boolean,
        publisher: String,
        sendPushNotification: Boolean = true,
        context: Context? = null
    ) {
        viewModelScope.launch {
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val notice = NoticeItem(
                title = title,
                description = description,
                category = category.ifBlank { "জরুরি ঘোষণা" },
                date = dateFormat.format(Date()),
                isUrgent = isUrgent,
                publisher = publisher.ifBlank { "চর আমখাওয়া ইউনিয়ন পরিষদ" },
                isApproved = true
            )
            repository.insertNotice(notice)

            // Trigger system push notification if enabled
            if (sendPushNotification && context != null) {
                com.example.util.NotificationHelper.showPushNotification(
                    context = context,
                    title = title,
                    message = description,
                    category = category,
                    isUrgent = isUrgent
                )
                _userMessage.value = "নতুন ঘোষণা সফলভাবে প্রচার করা হয়েছে এবং পুশ নোটিফিকেশন পাঠানো হয়েছে!"
            } else {
                _userMessage.value = "নতুন ঘোষণা সফলভাবে প্রচার করা হয়েছে!"
            }
        }
    }

    fun deleteNotice(id: Long) {
        viewModelScope.launch {
            repository.deleteNotice(id)
            _userMessage.value = "ঘোষণা মুছে ফেলা হয়েছে"
        }
    }

    fun toggleDonorAvailability(donor: BloodDonor) {
        viewModelScope.launch {
            repository.updateDonorAvailability(donor.id, !donor.isAvailable)
            _userMessage.value = if (!donor.isAvailable) "রক্তদাতা এখন এভেইলেবল" else "রক্তদাতা এখন আনএভেইলেবল"
        }
    }
}
