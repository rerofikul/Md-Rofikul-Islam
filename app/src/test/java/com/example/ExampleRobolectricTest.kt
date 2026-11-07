package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.db.AppDatabase
import com.example.data.model.BloodDonor
import com.example.data.model.DoctorInfo
import com.example.data.model.EducationalInstitute
import com.example.data.model.ParishadOfficial
import com.example.data.model.UserSubmission
import com.example.data.model.VillageInfo
import com.example.data.repository.UnionRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    private lateinit var database: AppDatabase
    private lateinit var repository: UnionRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = UnionRepository(database.unionDao())
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("আমাদের সানন্দবাড়ী", appName)
    }

    @Test
    fun `insert educational institute and retrieve`() = runBlocking {
        val institute = EducationalInstitute(
            name = "সানন্দবাড়ী ডিগ্রি কলেজ",
            type = "কলেজ",
            headName = "অধ্যক্ষ: জনাব মোঃ সিরাজুল ইসলাম",
            establishedYear = "১৯৮৮",
            villageOrLocation = "সানন্দবাড়ী, দেওয়ানগঞ্জ",
            phoneNumber = "01712987654"
        )
        repository.insertEducationalInstitute(institute)
        val list = repository.educationalInstitutes.first()
        assertEquals(1, list.size)
        assertEquals("সানন্দবাড়ী ডিগ্রি কলেজ", list[0].name)
    }

    @Test
    fun `insert village and retrieve`() = runBlocking {
        val village = VillageInfo(
            villageName = "সানন্দবাড়ী",
            wardNumber = "১নং ওয়ার্ড",
            householdCount = "৮৫০ টি",
            approximatePopulation = "৪,৫০০ জন",
            landmarksOrDescription = "সানন্দবাড়ী বাজার ও নদী ঘাট"
        )
        repository.insertVillage(village)
        val villages = repository.villages.first()
        assertEquals(1, villages.size)
        assertEquals("সানন্দবাড়ী", villages[0].villageName)
    }

    @Test
    fun `insert parishad official and retrieve`() = runBlocking {
        val official = ParishadOfficial(
            name = "জনাব মোঃ আব্দুল আজিজ আকন্দ",
            roleType = "চেয়ারম্যান",
            designation = "চেয়ারম্যান, চর আমখাওয়া ইউনিয়ন পরিষদ",
            wardOrArea = "সমগ্র ইউনিয়ন",
            villageAddress = "সানন্দবাড়ী",
            phoneNumber = "01712345678"
        )
        repository.insertParishadOfficial(official)
        val officials = repository.parishadOfficials.first()
        assertEquals(1, officials.size)
        assertEquals("জনাব মোঃ আব্দুল আজিজ আকন্দ", officials[0].name)
    }

    @Test
    fun `submit user contribution and approve as education institute`() = runBlocking {
        val submission = UserSubmission(
            id = 1,
            submissionType = "EDUCATION",
            titleOrName = "সানন্দবাড়ী মডেল একাডেমি",
            secondaryInfo = "প্রধান শিক্ষক: জনাব রফিকুল আলম",
            category = "বিদ্যালয়",
            phoneNumber = "01700000000",
            extraDetails = "সানন্দবাড়ী বাজার রোড",
            submittedBy = "এলাকাবাসী",
            submittedDate = "আজ",
            status = "PENDING"
        )
        repository.submitUserContribution(submission)
        val pendingSubmissions = repository.submissions.first()
        assertEquals(1, pendingSubmissions.size)
        assertEquals("PENDING", pendingSubmissions[0].status)

        // Approve submission
        repository.approveSubmission(pendingSubmissions[0])

        // Verify it was added to educational institutes
        val institutes = repository.educationalInstitutes.first()
        assertEquals(1, institutes.size)
        assertEquals("সানন্দবাড়ী মডেল একাডেমি", institutes[0].name)

        // Verify submission status changed to APPROVED
        val updatedSubmissions = repository.submissions.first()
        assertEquals("APPROVED", updatedSubmissions[0].status)
    }
}
