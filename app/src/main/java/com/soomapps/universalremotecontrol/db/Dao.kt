package com.soomapps.universalremotecontrol.db

import androidx.room.*
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.soomapps.universalremotecontrol.dto.DataModel
import java.io.Serializable

@Dao
interface FavoriteDao {

    @Insert
    abstract fun insertMultipleRecord(vararg favorite: DataModel)

    @Insert
    abstract fun insertMultipleListRecord(favorite: List<DataModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertOnlySingleRecord(favorite: DataModel)

    @Query("SELECT * FROM fav_list")
    abstract fun fetchAllData(): List<DataModel>

    @Query("SELECT * FROM fav_list WHERE id =:id")
    abstract fun getSingleRecord(id: Int): DataModel

    @Query("SELECT * FROM fav_list WHERE title =:title")
    abstract fun isEntryPresent(title: String): DataModel

    @Query("SELECT * FROM fav_list WHERE title =:title")
    abstract fun isExist(title: String): Boolean

    //{
    // db = this.getReadableDatabase()
    //  cur = db.rawQuery("SELECT * FROM $USER_TABLE WHERE email_id = '$strEmailAdd'", null)
    //  val exist = cur.getCount() > 0
    //  cur.close()
    //  db.close()
    //  return exist
    // }

    @Update
    abstract fun updateRecord(favorite: DataModel)

    @Delete
    abstract fun deleteRecord(favorite: DataModel)

}
/*
@Entity(tableName = "fav_list")
data class DataModel(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0,
        //@ColumnInfo(name = "title") var title: String = "",
        //@ColumnInfo(name = "content") var content: String = "",
        //@ColumnInfo(name = "answer") var answer: String = "",
                     @ColumnInfo(name = "title") var title: String = "",
                     @ColumnInfo(name = "totalFragments") var totalFragments: Int) : Serializable {
   // constructor(naviteAdvaceAd: UnifiedNativeAdView) : this()
}*/
