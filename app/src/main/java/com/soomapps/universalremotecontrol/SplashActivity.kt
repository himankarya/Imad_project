package com.soomapps.universalremotecontrol

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.soomapps.universalremotecontrol.utils.ConnectionDetector

class SplashActivity : AppCompatActivity() {
    private var mInterstitialAd: InterstitialAd? = null

    //  private var adBRequest:AdRequest?=null
//    private var mAdMobInterstitialAd:InterstitialAd?=null

    private val SPLASH_TIME_OUT: Int = 3000
//    private var mAdMobInterstitialAd: InterstitialAd? = null
//
//    private var adBRequest: AdRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        MobileAds.initialize(this,
//                resources.getString(R.string.app_id))

//        Handler().postDelayed({
//            // This method will be executed once the timer is over
//            // Start your app main activity
//            showFullScreenAd()
//            //showNextScreen();
//        },SPLASH_TIME_OUT.toLong())


//
//        adBRequest = AdRequest.Builder()
//                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                //.addTestDevice("0D18E7ADF186A5703273874B522EF74B")
//                .build()
//
//      //  Interstitial Ads
//        mAdMobInterstitialAd = InterstitialAd(this)
//        mAdMobInterstitialAd!!.adUnitId = getString(R.string.interstitial_full_screen)
//        mAdMobInterstitialAd!!.loadAd(adBRequest)
//        mAdMobInterstitialAd!!.adListener = object : AdListener() {
//            override fun onAdLoaded() {
//                // Code to be executed when an ad finishes loading.
//            }
//
//            override fun onAdFailedToLoad(errorCode: Int) {
//                // Code to be executed when an ad request fails.
//                // this method will be executed once the timer is over
//                // start your app main activity
//               // val intent = Intent(this@SplashActivity, MainActivity::class.java)
//              //  startActivity(intent)
//                // close this activity
//               // finish()
//            }
//
//            override fun onAdOpened() {
//                // Code to be executed when the ad is displayed.
//            }
//
//            override fun onAdLeftApplication() {
//                // Code to be executed when the user has left the app.
//            }
//
//            override fun onAdClosed() {
//                // Code to be executed when when the interstitial ad is closed.
//                // this method will be executed once the timer is over
//                // start your app main activity
//                val intent = Intent(this@SplashActivity, MainActivity::class.java)
//                startActivity(intent)
//                // close this activity
//                finish()
//            }
//        }

        //removing status bar
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        Handler().postDelayed({

            if (!ConnectionDetector.checkInternetConnection(this@SplashActivity)) {
              Log.d("ATATTAT","CALLED")
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                // close this activity
                finish()
            } else {
//                Log.d("ATATTAT1","CALLED")
//                if (mAdMobInterstitialAd!!.isLoaded) {
//                    Log.d("ATATTAT12","CALLED")
//                    mAdMobInterstitialAd!!.show()
//                }else{
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    // close this activity
                    finish()
//                }
            }


            // overridePendingTransition(R.anim.push_left_in_activity_start, R.anim.push_left_out_activity_start);
        }, SPLASH_TIME_OUT.toLong())

    }


//    fun loadFullAd() {
//        mInterstitialAd = InterstitialAd(this)
//
//        mInterstitialAd!!.setAdUnitId(getString(R.string.interstitial_full_screen))
//
//        if (!mInterstitialAd!!.isLoading() && !mInterstitialAd!!.isLoaded()) {
//
//            val builder = AdRequest.Builder()
//            /*.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);*/
//            mInterstitialAd!!.loadAd(builder.build())
//        }
//    }


//    fun showFullScreenAd() {
//        if (mInterstitialAd != null && mInterstitialAd!!.isLoaded()) {
//            mInterstitialAd!!.show()
//            mInterstitialAd!!.setAdListener(object : AdListener() {
//                override fun onAdClosed() {
//                    super.onAdClosed()
//                    showNextScreen()
//                }
//            })
//        } else {
//            showNextScreen()
//        }
//    }


//    fun showNextScreen() {
//
//        if (!ConnectionDetector.checkInternetConnection(this@SplashActivity)) {
//            Log.d("ATATTAT","CALLED")
//            val intent = Intent(this@SplashActivity, MainActivity::class.java)
//            startActivity(intent)
//            // close this activity
//            finish()
//        } else {
////                Log.d("ATATTAT1","CALLED")
////                if (mAdMobInterstitialAd!!.isLoaded) {
////                    Log.d("ATATTAT12","CALLED")
////                    mAdMobInterstitialAd!!.show()
////                }else{
//            val intent = Intent(this@SplashActivity, MainActivity::class.java)
//            startActivity(intent)
//            // close this activity
//            finish()
////                }
//        }
//
//
//        // overridePendingTransition(R.anim.push_left_in_activity_start, R.anim.push_left_out_activity_start);
//
//
//    }//    override fun onResume() {
////        super.onResume()
////        loadFullAd()
////    }


}

