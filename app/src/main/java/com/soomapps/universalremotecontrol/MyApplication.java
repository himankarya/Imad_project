package com.soomapps.universalremotecontrol;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

import com.soomapps.universalremotecontrol.utils.LanguageHelperr;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(
        mailTo = "soomappscrashes@gmail.com",
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.app_name)
public class MyApplication extends Application {

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onCreate() {
        ACRA.init(this);
        super.onCreate();
        String ul = LanguageHelperr.getUserLanguage(this);
        Log.d("ulll",""+ul);
        // if null the language doesn't need to be changed as the user has not chosen one.
        if (ul != null) {
            LanguageHelperr.updateLanguage(this, ul);
            Log.d("ghghh",""+ul);
        }
      //  initLanguage();
//        if (BuildConfig.DEBUG) {
//            //initialise reporter with external path
//            CrashReporter.initialize(this);
//        }
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        MultiDex.install(this);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        String ul = LanguageHelperr.getUserLanguage(this);
        Log.d("ulll",""+ul);
        // if null the language doesn't need to be changed as the user has not chosen one.
        if (ul != null) {
            LanguageHelperr.updateLanguage(this, ul);
            Log.d("ghghh",""+ul);
        }
       // initLanguage();
    }

    private void initLanguage() {

    }
}
