package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        EmergencyContact::class,
        EducationalInstitute::class,
        VillageInfo::class,
        ParishadOfficial::class,
        DoctorInfo::class,
        KrishiAdvice::class,
        BloodDonor::class,
        MarketMember::class,
        ShopInfo::class,
        NoticeItem::class,
        UserSubmission::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun unionDao(): UnionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sanandabari_union_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    val dao = database.unionDao()
                    // Prepopulate initial Sanandabari data
                    dao.insertEmergencyContacts(PrepopulateData.emergencyContacts)
                    dao.insertEducationalInstitutes(PrepopulateData.educationalInstitutes)
                    dao.insertVillages(PrepopulateData.villages)
                    dao.insertParishadOfficials(PrepopulateData.parishadOfficials)
                    dao.insertDoctors(PrepopulateData.doctors)
                    dao.insertBloodDonors(PrepopulateData.bloodDonors)
                    dao.insertMarketMembers(PrepopulateData.marketMembers)
                    dao.insertShops(PrepopulateData.shops)
                    dao.insertKrishiAdvices(PrepopulateData.krishiAdvices)
                    dao.insertNotices(PrepopulateData.notices)
                    for (sub in PrepopulateData.sampleSubmissions) {
                        dao.insertSubmission(sub)
                    }
                }
            }
        }
    }
}
