package com.soomapps.universalremotecontrol

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.soomapps.universalremotecontrol.utils.ConnectionDetector

class FeedbackActivity : AppCompatActivity() {

    internal var mAdView: AdView? = null
    private var adBRequest: AdRequest? = null

    internal lateinit var rl_contact_us: RelativeLayout

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        title = resources.getString(R.string.leave_feedback)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        rl_contact_us = findViewById<View>(R.id.rl_contact_us) as RelativeLayout
        //code for ads
        mAdView = findViewById<AdView>(R.id.adView) as AdView
        adBRequest = AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("0D18E7ADF186A5703273874B522EF74B")
                .build()
        mAdView!!.loadAd(adBRequest)
        if (ConnectionDetector.checkInternetConnection(this@FeedbackActivity)) {
            mAdView!!.visibility = View.VISIBLE
        } else {
            mAdView!!.visibility = View.GONE
        }

        rl_contact_us.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    // PRESSED
                    rl_contact_us.background = resources.getDrawable(R.drawable.final_button_pressed)
                    return@OnTouchListener true // if you want to handle the touch event
                }
                MotionEvent.ACTION_UP -> {
                    // RELEASED
                    rl_contact_us.background = resources.getDrawable(R.drawable.final_button)
                    sendEmail()
                    return@OnTouchListener true // if you want to handle the touch event
                }
            }
            false
        })
    }

    override fun onPause() {
        super.onPause()
        if (mAdView != null) {
            mAdView!!.pause()
        }
    }

    public override fun onResume() {
        super.onResume()
        if (mAdView != null) {
            mAdView!!.resume()
        }
    }

    public override fun onDestroy() {
        if (mAdView != null) {
            mAdView!!.destroy()
        }
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    protected fun sendEmail() {
        val mailto = "mailto:contact@soomapps.com" +
                "?cc=" + "" +
                "&subject=" + Uri.encode("") +
                "&body=" + Uri.encode("")

        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse(mailto)

        try {
            startActivity(emailIntent)
        } catch (e: ActivityNotFoundException) {
            //TODO: Handle case where no email app is available
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        /*   finishAffinity();
        System.exit(0);*/
    }
}
