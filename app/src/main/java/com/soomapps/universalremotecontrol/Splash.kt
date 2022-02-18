package com.soomapps.universalremotecontrol

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.soomapps.universalremotecontrol.utils.AppConstants
import com.soomapps.universalremotecontrol.utils.LanguageHelperr
import java.util.*

class Splash : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Int = 12000
    private var mInterstitialAd: InterstitialAd? = null
    private var splashImage: ImageView? = null
    private var animation: Animation? = null
    private var handler: Handler? = null
    private var splashTextt: TextView? = null
    private var spinkit: SpinKitView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashImage = findViewById<ImageView>(R.id.splashImage)
        //  animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in_splash_img)
        //  splashImage?.startAnimation(animation)

        val r = Random()
        val i1 = r.nextInt(12000 - 2000 + 1) + 2000


        splashTextt = findViewById<TextView>(R.id.splashTextt)
        splashTextt!!.setText("MyRem © SoomApps")

//        Handler().postDelayed({
//            splashTextt = findViewById<TextView>(R.id.splashTextt)
//            splashTextt!!.setText("MyRem © SoomApps")
//            animation = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_up_splash_text)
//            splashTextt!!.startAnimation(animation)
//
//
//        }, 2000)

        spinkit = findViewById<SpinKitView>(R.id.spin_kit)

//        val doubleBounce = com.soomapps.universalremotecontrol.DoubleBounce()
//        doubleBounce.setBounds(0, 0, 100, 100)
//        doubleBounce.color = (Color.parseColor("#E0E2E6"))
//        spinkit!!.setIndeterminateDrawable(doubleBounce)

        MobileAds.initialize(this, "ca-app-pub-3424164443057663~2794304363")
        // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd!!.setAdUnitId("ca-app-pub-3424164443057663/8985567374")
        mInterstitialAd!!.loadAd(AdRequest.Builder().build())
        mInterstitialAd!!.adListener = object : AdListener() {

            override fun onAdClosed() {
                super.onAdClosed()
                nextFunc()
            }
        }

        if (AppConstants.checkInternetConnection(this@Splash)) {
            Handler().postDelayed({
                val doubleBounce = com.soomapps.universalremotecontrol.DoubleBounce()
                doubleBounce.setBounds(0, 0, 100, 100)
                doubleBounce.color = (Color.parseColor("#E0E2E6"))
                spinkit!!.setIndeterminateDrawable(doubleBounce)
            }, 200)
            Handler().postDelayed({
                showFullScreenAd()
            }, i1.toLong())
        } else {
            Handler().postDelayed({
                val doubleBounce = com.soomapps.universalremotecontrol.DoubleBounce()
                doubleBounce.setBounds(0, 0, 100, 100)
                doubleBounce.color = (Color.parseColor("#E0E2E6"))
                spinkit!!.setIndeterminateDrawable(doubleBounce)
                //showNextScreen();
            }, 200)
            Handler().postDelayed({
                nextFunc()
            }, 4000)
        }


//        if (ConnectionDetector.checkInternetConnection(this@Splash)) {
//
//            Handler().postDelayed({
//                if (mInterstitialAd!!.isLoaded) {
//                    mInterstitialAd!!.show()
//
//                } else {
//                    nextFunc()
//                }
//
//            }, 5000)
//
//        } else {
//
//            Handler().postDelayed({
//                nextFunc()
//            }, 3000)
//
//       }
        val pref: SharedPreferences =
            applicationContext.getSharedPreferences("MyPref", 0)  // 0 - for private mode
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putString("star", "1")
        editor.commit()

//        val ul = LanguageHelperr.getUserLanguage(this)
//
//            val intent = Intent(this@Splash, Main2Activity::class.java)
//            intent.putExtra("himank",ul)
//            startActivity(intent)
//
//        Log.d("splash_screen", "" + ul)

    }

    private fun nextFunc() {
//        val i = Intent(this, Main2Activity::class.java)
//        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(i)

        val ul = LanguageHelperr.getUserLanguage(this)

        val intent = Intent(this@Splash, Main2Activity::class.java)
        intent.putExtra("himank", ul)
        startActivity(intent)
        Log.d("splash_screen", "" + ul)


//        val intent = Intent(this@Splash, Main2Activity::class.java)
//        startActivity(intent)
        //finish()
        // close this activity
    }

    fun showFullScreenAd() {
        if (mInterstitialAd != null && mInterstitialAd!!.isLoaded) {
            mInterstitialAd!!.show()
            mInterstitialAd!!.adListener = object : AdListener() {
                override fun onAdClosed() {
                    super.onAdClosed()
                    nextFunc()
                }
            }
        } else {
            Handler().postDelayed({
                nextFunc()
                //showNextScreen();
            }, SPLASH_TIME_OUT.toLong())

//            showNextScreen();
        }
    }

//    fun showFullScreenAd() {
//        val application = application
//        (application as MyApplication)
//        application.showAdIfAvailable(
//            this@Splash,
//            object : MyApplication.OnShowAdCompleteListener {
//                override fun onShowAdComplete() {
//                    nextFunc()
//                }
//            })
//        if (application !is MyApplication) {
//            //  Log.e(LOG_TAG, "Failed to cast application to MyApplication.");
//            nextFunc()
//            return
//        } else if (application !is MyApplication) {
//            Handler().postDelayed({
//                nextFunc()
//                //showNextScreen();
//            }, SPLASH_TIME_OUT.toLong())
//
////            showNextScreen();
//        }
//    }

}


