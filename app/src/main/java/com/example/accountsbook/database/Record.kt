package com.example.accountsbook.database

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(tableName = "records")
data class RecordEntity(
    @PrimaryKey @ColumnInfo(name = "timestamp") val timestamp: Long,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "isIncome") val isIncome: Boolean,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "icon") @DrawableRes val icon: Int,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "description") val description: String
)

@Dao
interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: RecordEntity)

    @Delete
    suspend fun delete(record: RecordEntity)

    @Query("SELECT * FROM records WHERE :date = date ORDER BY timestamp DESC")
    suspend fun getRecordsOnDate(date: String): List<RecordEntity>
}

@Database(entities = [RecordEntity::class], version = 1)
abstract class RecordDatabase : RoomDatabase() {

    abstract fun recordDao(): RecordDao
}
