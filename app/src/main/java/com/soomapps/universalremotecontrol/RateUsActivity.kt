package com.soomapps.universalremotecontrol

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.soomapps.universalremotecontrol.utils.ConnectionDetector

class RateUsActivity : AppCompatActivity() {

    internal var adView: AdView? = null
    private var adBRequest: AdRequest? = null
    internal lateinit var rl_contact_us: RelativeLayout

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_us)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        title = resources.getString(R.string.thanks_for_the_love)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        rl_contact_us = findViewById<View>(R.id.rl_contact_us) as RelativeLayout
        // adView = findViewById<View>(R.id.adView) as AdView
        //code for ads
        adView = findViewById<AdView>(R.id.adView) as AdView
        adBRequest = AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("0D18E7ADF186A5703273874B522EF74B")
                .build()
        adView!!.loadAd(adBRequest)

        if (ConnectionDetector.checkInternetConnection(this@RateUsActivity)) {
            adView!!.visibility = View.VISIBLE
        } else {
            adView!!.visibility = View.GONE
        }

        rl_contact_us.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    // PRESSED
                    rl_contact_us.background = resources.getDrawable(R.drawable.abc_btn_check_to_on_mtrl_015)
                    return@OnTouchListener true // if you want to handle the touch event
                }
                MotionEvent.ACTION_UP -> {
                    // RELEASED
                    rl_contact_us.background = resources.getDrawable(R.drawable.final_button)
                    rateUs()
                    return@OnTouchListener true // if you want to handle the touch event
                }
            }
            false
        })

    }

    override fun onPause() {
        super.onPause()
        if (adView != null) {
            adView!!.pause()
        }
    }

    public override fun onResume() {
        super.onResume()
        if (adView != null) {
            adView!!.resume()
        }
    }

    public override fun onDestroy() {
        if (adView != null) {
            adView!!.destroy()
        }
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    protected fun rateUs() {
        val uri = Uri.parse("https://play.google.com/store/apps/details?id=$packageName&hl=en")
        val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
        try {
            startActivity(myAppLinkToMarket)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this@RateUsActivity, "unable to find app", Toast.LENGTH_LONG).show()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        /*finishAffinity();
        System.exit(0);*/
    }
}
