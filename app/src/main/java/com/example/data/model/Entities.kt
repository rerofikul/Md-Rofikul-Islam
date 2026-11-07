package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emergency_contacts")
data class EmergencyContact(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val subtitle: String,
    val category: String, // ambulance, police, fire, electricity, parishad, hospital
    val phoneNumber: String,
    val availableHours: String = "২৪ ঘণ্টা চালু",
    val location: String = "সানন্দবাড়ী, দেওয়ানগঞ্জ",
    val isApproved: Boolean = true
)

@Entity(tableName = "educational_institutes")
data class EducationalInstitute(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val type: String, // কলেজ, উচ্চ মাধ্যমিক / মাধ্যমিক বিদ্যালয়, প্রাথমিক বিদ্যালয়, মাদ্রাসা
    val headName: String, // প্রধান শিক্ষক / অধ্যক্ষ / সুপার
    val establishedYear: String,
    val villageOrLocation: String,
    val phoneNumber: String,
    val totalStudentsOrInfo: String = "",
    val isApproved: Boolean = true
)

@Entity(tableName = "villages")
data class VillageInfo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val villageName: String,
    val wardNumber: String, // ১নং ওয়ার্ড, ২নং ওয়ার্ড, ...
    val householdCount: String,
    val approximatePopulation: String,
    val landmarksOrDescription: String,
    val isApproved: Boolean = true
)

@Entity(tableName = "parishad_officials")
data class ParishadOfficial(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val roleType: String, // চেয়ারম্যান, সচিব, ইউপি সদস্য (১-৯), সংরক্ষিত মহিলা সদস্য, ডিজিটাল সেন্টার উদ্যোক্তা, গ্রাম পুলিশ/দফাদার, স্বাস্থ্য ও প.প. সহকারী
    val designation: String,
    val wardOrArea: String,
    val villageAddress: String,
    val phoneNumber: String,
    val dutyTime: String = "সকাল ৯টা - বিকাল ৫টা",
    val isApproved: Boolean = true
)

@Entity(tableName = "doctors")
data class DoctorInfo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val specialty: String,
    val degrees: String,
    val chamberOrHospital: String,
    val visitingHours: String,
    val phoneNumber: String,
    val consultationFee: String = "বিনামূল্যে (সরকারি)",
    val location: String = "সানন্দবাড়ী বাজার",
    val isApproved: Boolean = true
)

@Entity(tableName = "krishi_advices")
data class KrishiAdvice(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val category: String,
    val summary: String,
    val details: String,
    val seasonOrMonth: String,
    val officerName: String,
    val officerPhone: String,
    val isApproved: Boolean = true
)

@Entity(tableName = "blood_donors")
data class BloodDonor(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val bloodGroup: String,
    val villageOrWard: String,
    val phoneNumber: String,
    val lastDonationDate: String = "প্রযোজ্য নয়",
    val isAvailable: Boolean = true,
    val isApproved: Boolean = true
)

@Entity(tableName = "market_members")
data class MarketMember(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val designation: String,
    val marketName: String = "ঐতিহাসিক সানন্দবাড়ী বাজার",
    val phoneNumber: String,
    val shopName: String = "",
    val isApproved: Boolean = true
)

@Entity(tableName = "shops")
data class ShopInfo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val shopName: String,
    val ownerName: String,
    val category: String,
    val marketName: String = "সানন্দবাড়ী বাজার",
    val shopNumberOrLane: String,
    val phoneNumber: String,
    val openingHours: String = "সকাল ৮টা - রাত ১০টা",
    val isApproved: Boolean = true
)

@Entity(tableName = "notices")
data class NoticeItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val category: String,
    val date: String,
    val isUrgent: Boolean = false,
    val publisher: String = "চর আমখাওয়া ইউনিয়ন পরিষদ",
    val isApproved: Boolean = true
)

@Entity(tableName = "submissions")
data class UserSubmission(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val submissionType: String, // "EDUCATION", "VILLAGE", "OFFICIAL", "DOCTOR", "BLOOD_DONOR", "SHOP", "EMERGENCY_CONTACT"
    val titleOrName: String,
    val secondaryInfo: String,
    val category: String,
    val phoneNumber: String,
    val extraDetails: String,
    val submittedBy: String = "এলাকাবাসী",
    val submittedDate: String,
    val status: String = "PENDING"
)
