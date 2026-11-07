package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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

@Dao
interface UnionDao {

    // Emergency Contacts
    @Query("SELECT * FROM emergency_contacts WHERE isApproved = 1 ORDER BY id ASC")
    fun getAllEmergencyContacts(): Flow<List<EmergencyContact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmergencyContact(contact: EmergencyContact): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmergencyContacts(contacts: List<EmergencyContact>)

    @Query("DELETE FROM emergency_contacts WHERE id = :id")
    suspend fun deleteEmergencyContact(id: Long)

    // Educational Institutes
    @Query("SELECT * FROM educational_institutes WHERE isApproved = 1 ORDER BY id ASC")
    fun getAllEducationalInstitutes(): Flow<List<EducationalInstitute>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEducationalInstitute(institute: EducationalInstitute): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEducationalInstitutes(institutes: List<EducationalInstitute>)

    @Query("DELETE FROM educational_institutes WHERE id = :id")
    suspend fun deleteEducationalInstitute(id: Long)

    // Villages
    @Query("SELECT * FROM villages WHERE isApproved = 1 ORDER BY wardNumber ASC, villageName ASC")
    fun getAllVillages(): Flow<List<VillageInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVillage(village: VillageInfo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVillages(villages: List<VillageInfo>)

    @Query("DELETE FROM villages WHERE id = :id")
    suspend fun deleteVillage(id: Long)

    // Parishad Officials & Staff
    @Query("SELECT * FROM parishad_officials WHERE isApproved = 1 ORDER BY id ASC")
    fun getAllParishadOfficials(): Flow<List<ParishadOfficial>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParishadOfficial(official: ParishadOfficial): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParishadOfficials(officials: List<ParishadOfficial>)

    @Query("DELETE FROM parishad_officials WHERE id = :id")
    suspend fun deleteParishadOfficial(id: Long)

    // Doctors
    @Query("SELECT * FROM doctors WHERE isApproved = 1 ORDER BY id ASC")
    fun getAllDoctors(): Flow<List<DoctorInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoctor(doctor: DoctorInfo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoctors(doctors: List<DoctorInfo>)

    @Query("DELETE FROM doctors WHERE id = :id")
    suspend fun deleteDoctor(id: Long)

    // Blood Donors
    @Query("SELECT * FROM blood_donors WHERE isApproved = 1 ORDER BY isAvailable DESC, id ASC")
    fun getAllBloodDonors(): Flow<List<BloodDonor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBloodDonor(donor: BloodDonor): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBloodDonors(donors: List<BloodDonor>)

    @Query("UPDATE blood_donors SET isAvailable = :isAvailable WHERE id = :id")
    suspend fun updateDonorAvailability(id: Long, isAvailable: Boolean)

    @Query("DELETE FROM blood_donors WHERE id = :id")
    suspend fun deleteBloodDonor(id: Long)

    // Market Members
    @Query("SELECT * FROM market_members WHERE isApproved = 1 ORDER BY id ASC")
    fun getAllMarketMembers(): Flow<List<MarketMember>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarketMembers(members: List<MarketMember>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarketMember(member: MarketMember): Long

    @Query("DELETE FROM market_members WHERE id = :id")
    suspend fun deleteMarketMember(id: Long)

    // Shops
    @Query("SELECT * FROM shops WHERE isApproved = 1 ORDER BY id ASC")
    fun getAllShops(): Flow<List<ShopInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShop(shop: ShopInfo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShops(shops: List<ShopInfo>)

    @Query("DELETE FROM shops WHERE id = :id")
    suspend fun deleteShop(id: Long)

    // Krishi Advices
    @Query("SELECT * FROM krishi_advices WHERE isApproved = 1 ORDER BY id ASC")
    fun getAllKrishiAdvices(): Flow<List<KrishiAdvice>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKrishiAdvices(advices: List<KrishiAdvice>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKrishiAdvice(advice: KrishiAdvice): Long

    @Query("DELETE FROM krishi_advices WHERE id = :id")
    suspend fun deleteKrishiAdvice(id: Long)

    // Notices & News Feed
    @Query("SELECT * FROM notices WHERE isApproved = 1 ORDER BY isUrgent DESC, id DESC")
    fun getAllNotices(): Flow<List<NoticeItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotice(notice: NoticeItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotices(notices: List<NoticeItem>)

    @Query("DELETE FROM notices WHERE id = :id")
    suspend fun deleteNotice(id: Long)

    // Submissions (Admin Verification)
    @Query("SELECT * FROM submissions ORDER BY status ASC, id DESC")
    fun getAllSubmissions(): Flow<List<UserSubmission>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubmission(submission: UserSubmission): Long

    @Update
    suspend fun updateSubmission(submission: UserSubmission)

    @Query("DELETE FROM submissions WHERE id = :id")
    suspend fun deleteSubmission(id: Long)
}
