package com.example.accountsbook.database

import androidx.room.Room
import com.example.accountsbook.util.ContextRegistry

object DatabaseHelper {

    private const val DB_RECORD = "db_record"

    private val recordDb: RecordDatabase by lazy {
        Room.databaseBuilder(
            ContextRegistry.applicationContext,
            RecordDatabase::class.java,
            DB_RECORD
        ).build()
    }

    val recordDao: RecordDao by lazy {
        recordDb.recordDao()
    }
}
