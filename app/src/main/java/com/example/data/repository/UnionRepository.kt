package com.example.data.repository

import com.example.data.db.UnionDao
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
import kotlinx.coroutines.flow.Flow

class UnionRepository(private val dao: UnionDao) {

    val emergencyContacts: Flow<List<EmergencyContact>> = dao.getAllEmergencyContacts()
    val educationalInstitutes: Flow<List<EducationalInstitute>> = dao.getAllEducationalInstitutes()
    val villages: Flow<List<VillageInfo>> = dao.getAllVillages()
    val parishadOfficials: Flow<List<ParishadOfficial>> = dao.getAllParishadOfficials()
    val doctors: Flow<List<DoctorInfo>> = dao.getAllDoctors()
    val bloodDonors: Flow<List<BloodDonor>> = dao.getAllBloodDonors()
    val marketMembers: Flow<List<MarketMember>> = dao.getAllMarketMembers()
    val shops: Flow<List<ShopInfo>> = dao.getAllShops()
    val krishiAdvices: Flow<List<KrishiAdvice>> = dao.getAllKrishiAdvices()
    val notices: Flow<List<NoticeItem>> = dao.getAllNotices()
    val submissions: Flow<List<UserSubmission>> = dao.getAllSubmissions()

    suspend fun insertEmergencyContact(contact: EmergencyContact) = dao.insertEmergencyContact(contact)
    suspend fun deleteEmergencyContact(id: Long) = dao.deleteEmergencyContact(id)

    suspend fun insertEducationalInstitute(institute: EducationalInstitute) = dao.insertEducationalInstitute(institute)
    suspend fun deleteEducationalInstitute(id: Long) = dao.deleteEducationalInstitute(id)

    suspend fun insertVillage(village: VillageInfo) = dao.insertVillage(village)
    suspend fun deleteVillage(id: Long) = dao.deleteVillage(id)

    suspend fun insertParishadOfficial(official: ParishadOfficial) = dao.insertParishadOfficial(official)
    suspend fun deleteParishadOfficial(id: Long) = dao.deleteParishadOfficial(id)

    suspend fun insertDoctor(doctor: DoctorInfo) = dao.insertDoctor(doctor)
    suspend fun deleteDoctor(id: Long) = dao.deleteDoctor(id)

    suspend fun insertBloodDonor(donor: BloodDonor) = dao.insertBloodDonor(donor)
    suspend fun updateDonorAvailability(id: Long, isAvailable: Boolean) = dao.updateDonorAvailability(id, isAvailable)
    suspend fun deleteBloodDonor(id: Long) = dao.deleteBloodDonor(id)

    suspend fun insertMarketMember(member: MarketMember) = dao.insertMarketMember(member)
    suspend fun deleteMarketMember(id: Long) = dao.deleteMarketMember(id)

    suspend fun insertShop(shop: ShopInfo) = dao.insertShop(shop)
    suspend fun deleteShop(id: Long) = dao.deleteShop(id)

    suspend fun insertKrishiAdvice(advice: KrishiAdvice) = dao.insertKrishiAdvice(advice)
    suspend fun deleteKrishiAdvice(id: Long) = dao.deleteKrishiAdvice(id)

    suspend fun insertNotice(notice: NoticeItem) = dao.insertNotice(notice)
    suspend fun deleteNotice(id: Long) = dao.deleteNotice(id)

    suspend fun submitUserContribution(submission: UserSubmission) = dao.insertSubmission(submission)

    suspend fun approveSubmission(submission: UserSubmission) {
        when (submission.submissionType) {
            "EDUCATION" -> {
                dao.insertEducationalInstitute(
                    EducationalInstitute(
                        name = submission.titleOrName,
                        type = submission.category.ifBlank { "বিদ্যালয়" },
                        headName = submission.secondaryInfo.ifBlank { "প্রধান শিক্ষক" },
                        establishedYear = "প্রযোজ্য নয়",
                        villageOrLocation = submission.extraDetails.ifBlank { "সানন্দবাড়ী" },
                        phoneNumber = submission.phoneNumber,
                        isApproved = true
                    )
                )
            }
            "OFFICIAL" -> {
                dao.insertParishadOfficial(
                    ParishadOfficial(
                        name = submission.titleOrName,
                        roleType = submission.category.ifBlank { "ইউপি সেবাকর্মী" },
                        designation = submission.secondaryInfo.ifBlank { "সেবাকর্মী" },
                        wardOrArea = "ইউনিয়ন এলাকা",
                        villageAddress = submission.extraDetails.ifBlank { "সানন্দবাড়ী" },
                        phoneNumber = submission.phoneNumber,
                        isApproved = true
                    )
                )
            }
            "DOCTOR" -> {
                dao.insertDoctor(
                    DoctorInfo(
                        name = submission.titleOrName,
                        specialty = submission.category.ifBlank { "সাধারণ চিকিৎসক" },
                        degrees = submission.secondaryInfo.ifBlank { "এমবিবিএস" },
                        chamberOrHospital = submission.extraDetails.ifBlank { "সানন্দবাড়ী বাজার" },
                        visitingHours = "সকাল ৯টা - বিকাল ৫টা",
                        phoneNumber = submission.phoneNumber,
                        isApproved = true
                    )
                )
            }
            "BLOOD_DONOR" -> {
                dao.insertBloodDonor(
                    BloodDonor(
                        name = submission.titleOrName,
                        bloodGroup = submission.category.ifBlank { "O+" },
                        villageOrWard = submission.secondaryInfo.ifBlank { "সানন্দবাড়ী" },
                        phoneNumber = submission.phoneNumber,
                        lastDonationDate = submission.extraDetails.ifBlank { "প্রযোজ্য নয়" },
                        isAvailable = true,
                        isApproved = true
                    )
                )
            }
            "SHOP" -> {
                dao.insertShop(
                    ShopInfo(
                        shopName = submission.titleOrName,
                        ownerName = submission.secondaryInfo.ifBlank { "স্বত্বাধিকারী" },
                        category = submission.category.ifBlank { "মুদি ও জেনারেল" },
                        shopNumberOrLane = submission.extraDetails.ifBlank { "সানন্দবাড়ী বাজার" },
                        phoneNumber = submission.phoneNumber,
                        isApproved = true
                    )
                )
            }
            "EMERGENCY_CONTACT" -> {
                dao.insertEmergencyContact(
                    EmergencyContact(
                        title = submission.titleOrName,
                        subtitle = submission.secondaryInfo.ifBlank { "জরুরি সেবা" },
                        category = submission.category.ifBlank { "general" },
                        phoneNumber = submission.phoneNumber,
                        location = submission.extraDetails.ifBlank { "সানন্দবাড়ী, দেওয়ানগঞ্জ" },
                        isApproved = true
                    )
                )
            }
        }
        dao.updateSubmission(submission.copy(status = "APPROVED"))
    }

    suspend fun rejectSubmission(submission: UserSubmission) {
        dao.updateSubmission(submission.copy(status = "REJECTED"))
    }

    suspend fun deleteSubmission(id: Long) = dao.deleteSubmission(id)
}
