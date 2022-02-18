package com.soomapps.universalremotecontrol.db
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // https://developer.android.com/reference/android/arch/persistence/room/ColumnInfo
        /*
        database.execSQL("ALTER TABLE pin "
                + " ADD COLUMN is_location_accurate INTEGER")
         */
//        database.execSQL("ALTER TABLE fav_db "
//                + " ADD COLUMN is_location_accurate INTEGER NOT NULL DEFAULT 0")
//        database.execSQL("UPDATE fav_db "
//                + " SET is_location_accurate = 0 WHERE lat IS NULL")
//        database.execSQL("UPDATE fav_db "
//                + " SET is_location_accurate = 1 WHERE lat IS NOT NULL")
    }
}

val MIGRATION_2_3: Migration = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // https://developer.android.com/reference/android/arch/persistence/room/ColumnInfo
        /*
        database.execSQL("ALTER TABLE pin "
                + " ADD COLUMN is_location_accurate INTEGER")
         */
//        database.execSQL("ALTER TABLE fav_list "
//                + " ADD COLUMN is_location_accurate INTEGER NOT NULL DEFAULT 0")
//        database.execSQL("UPDATE fav_db "
//                + " SET is_location_accurate = 0 WHERE lat IS NULL")
//        database.execSQL("UPDATE fav_db "
//                + " SET is_location_accurate = 1 WHERE lat IS NOT NULL")
    }
}
