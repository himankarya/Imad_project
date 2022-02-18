package com.soomapps.universalremotecontrol.db
import androidx.room.Database
import androidx.room.RoomDatabase
import com.soomapps.universalremotecontrol.dto.DataModel

@Database(entities = arrayOf(DataModel::class), version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
