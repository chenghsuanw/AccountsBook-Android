package com.example.accountsbook.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.accountsbook.R
import com.example.accountsbook.util.ContextRegistry
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object DatabaseHelper {

    private const val DB_RECORD = "db_record"
    private const val DB_CATEGORY = "db_category"

    private val recordDb: RecordDatabase by lazy {
        Room.databaseBuilder(
            ContextRegistry.applicationContext,
            RecordDatabase::class.java,
            DB_RECORD
        ).build()
    }

    private val categoryDb: CategoryDatabase by lazy {
        Room.databaseBuilder(
            ContextRegistry.applicationContext,
            CategoryDatabase::class.java,
            DB_CATEGORY
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                setDefaultCategories()
            }
        })
            .build()
    }

    val recordDao: RecordDao by lazy {
        recordDb.recordDao()
    }

    val categoryDao: CategoryDao by lazy {
        categoryDb.categoryDao()
    }

    private fun setDefaultCategories() {
        val defaultCategories = listOf(
            CategoryEntity("食物", R.drawable.category_food),
            CategoryEntity("購物", R.drawable.category_shopping),
            CategoryEntity("住宅", R.drawable.category_house),
            CategoryEntity("交通", R.drawable.category_transport),
            CategoryEntity("教育", R.drawable.category_educate),
            CategoryEntity("娛樂", R.drawable.category_fun),
            CategoryEntity("旅行", R.drawable.category_travel),
            CategoryEntity("醫療", R.drawable.category_hospital),
            CategoryEntity("投資", R.drawable.category_invest),
            CategoryEntity("轉帳", R.drawable.category_transfer)
        )
        defaultCategories.forEach {
            GlobalScope.launch {
                categoryDao.insert(it)
            }
        }
    }
}
