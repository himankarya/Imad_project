package com.soomapps.universalremotecontrol.dto;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import java.io.Serializable;

@Entity(tableName = "fav_list")
public class DataModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id = 0;

    @ColumnInfo(name = "title")
    String title = "";

    @ColumnInfo(name = "totalFragments")
    int totalFragments;

    // new field
    @ColumnInfo(name = "is_location_accurate")
    int isLocationAccurate = 0;

    @Ignore
    private UnifiedNativeAdView nativeAppInstallAd;

    public DataModel(int id, String title, int totalFragments , int isLocationAccurate) {
        this.id = id;
        this.title = title;
        this.totalFragments = totalFragments;
        this.isLocationAccurate=isLocationAccurate;
    }

    public DataModel(UnifiedNativeAdView nativeAppInstallAd) {
        this.nativeAppInstallAd = nativeAppInstallAd;
    }

    public UnifiedNativeAdView getNativeAppInstallAd() {
        return nativeAppInstallAd;
    }

    public void setNativeAppInstallAd(UnifiedNativeAdView nativeAppInstallAd) {
        this.nativeAppInstallAd = nativeAppInstallAd;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTotalFragments(int totalFragments) {
        this.totalFragments = totalFragments;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getTotalFragments() {
        return totalFragments;
    }

    public int getIsLocationAccurate() {
        return isLocationAccurate;
    }

    public void setIsLocationAccurate(int isLocationAccurate) {
        this.isLocationAccurate = isLocationAccurate;
    }
}
