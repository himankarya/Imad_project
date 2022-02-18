package com.soomapps.universalremotecontrol

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.*
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.google.android.material.navigation.NavigationView
import com.soomapps.universalremotecontrol.utils.AppConstants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import pub.devrel.easypermissions.EasyPermissions
import java.util.*

class  MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var dialog: Dialog
    private var mAdView: AdView? = null
    private var adBRequest: AdRequest? = null
    lateinit var editor: SharedPreferences.Editor
    var doubleBackToExitPressedOnce = false
    lateinit var sharedpreferences: SharedPreferences
    private var emailBTN: ImageView? = null
    private var policyBTN: ImageView? = null
    private var resulBTN: CardView? = null
    private var myDeviceBTN: CardView? = null
    internal var rating_value: Int = 0
   // private var nav_layout: LinearLayout? = null
   // private var params: LinearLayout.LayoutParams? = null
    private var frameLayout: CardView? = null
    private var frameLayoutNav: FrameLayout? = null

    lateinit var languageSharedpreference:SharedPreferences
    var pos:Int=0
    var lanSelect:Int=0
    var language:String? = ""
    var mLanguageCode:String? =""
    lateinit var myLocale: Locale

    private var handler : Handler?=null
    private var count:Int=0

    private lateinit var iv_star_1:ImageView
    private lateinit var iv_star_2:ImageView
    private lateinit var iv_star_3:ImageView
    private lateinit var iv_star_4:ImageView
    private lateinit var iv_star_5:ImageView


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        MobileAds.initialize(this, getString(R.string.app_id))

        frameLayout = findViewById<CardView>(R.id.fl_adplaceholder)

        val nView: View = nav_view.getHeaderView(0)
        frameLayoutNav  = nView.findViewById<FrameLayout>(R.id.fl_adplaceholder_nav)
        refreshAd()
        refreshDrawerAd()

        languageSharedpreference = getSharedPreferences("SELECT_LANGUAGE", 0)

        sharedpreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE)
        if (sharedpreferences.getInt("repeat_value", 0) <= 4) {
            increaseValue()
        }
       // val nView: View = nav_view.getHeaderView(0)
       // nav_layout = nView.findViewById(R.id.nav_layout)
       // params = nav_layout!!.layoutParams as LinearLayout.LayoutParams?

       /* if (ConnectionDetector.checkInternetConnection(this@MainActivity)) {
            // nav_layout!!.layoutParams.height = 326
           // params!!.height = resources.getDimension(R.dimen.view_height1).toInt()
          //  nav_layout!!.layoutParams = params
           // frameLayout!!.visibility = View.VISIBLE
        } else {
          //  params!!.height = resources.getDimension(R.dimen.view_height2).toInt()
           // nav_layout!!.layoutParams = params
           // frameLayout!!.visibility = View.GONE
        }*/


        //code for ads
        /* mAdView = findViewById<AdView>(R.id.adView) as AdView
         adBRequest = AdRequest.Builder()
                 //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                 //.addTestDevice("0D18E7ADF186A5703273874B522EF74B")
                 .build()
         mAdView!!.loadAd(adBRequest)
         if (ConnectionDetector.checkInternetConnection(this@MainActivity)) {
             mAdView!!.visibility = View.VISIBLE
         } else {
             mAdView!!.visibility = View.GONE
         }
 */
        // resulTV=findViewById(R.id.resulTV)
        resulBTN = findViewById(R.id.resulBTN)
        myDeviceBTN = findViewById(R.id.myDeviceBTN)
        emailBTN = findViewById(R.id.emailBTN)
        policyBTN = findViewById(R.id.policyBTN)
        storageTask()

        sharedpreferences = getSharedPreferences("mypref",
                Context.MODE_PRIVATE)

        if (sharedpreferences.getInt("repeat_value", 0) <= 4) {
            Log.d("EARSTDG", "CAlled: ${sharedpreferences.getInt("repeat_value", 0)}")
            increaseValue()
        }

        if (hasStoragePermissions()) {
            /*   val manager = getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager

               val builder = StringBuilder()

               builder.append("ConsumerIrManager.hasIrEmitter()=")
               builder.append(manager.hasIrEmitter())
               Log.d("MYTAGTV", "----------------")
               Log.d("MYTAGTV", builder.toString())
               Log.d("MYTAGTV", "----------------")

               if (manager.hasIrEmitter() == true) {
                   val ranges = manager.carrierFrequencies
                   for (range in ranges) {
                       builder.append(range.minFrequency)
                       builder.append("/")
                       builder.append(range.maxFrequency)
                       builder.append(" : ")
                   }
                   Log.d("MYTAGTVE", "$ranges")
               }*/
        } else {
            storageTask()
        }

        resulBTN!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, TVListActivity::class.java)
            startActivity(intent)
        })

        emailBTN!!.setOnClickListener(View.OnClickListener {

            sendEmail()
            /*    val email = Intent(Intent.ACTION_SEND)
            email.putExtra(Intent.EXTRA_EMAIL, arrayOf("contact@soomapps.com"))
            // email.putExtra(Intent.EXTRA_SUBJECT, "")
            // email.putExtra(Intent.EXTRA_TEXT, "message")
            email.type = "message/rfc822"
            startActivity(Intent.createChooser(email, "Choose an Email client :"))*/
        })

        policyBTN!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, InfoActivity::class.java)
            startActivity(intent)
        })

        myDeviceBTN!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, MyDeviceActivity::class.java)
            startActivity(intent)
        })

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        //for actual icon color
        nav_view.itemIconTintList = null

        thirdtimeADB()

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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.mainmenu, menu)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val iv = inflater.inflate(R.layout.iv_refresh, null) as ImageView
        val rotation = AnimationUtils.loadAnimation(this, R.anim.blinkanimation)
        // rotation.setRepeatCount(3);
        // rotation.setRepeatCount(3);
        iv.startAnimation(rotation)
        menu.findItem(R.id.rate_us).actionView = iv
        iv.setOnClickListener {
            rating_value = 0
            ShowRatingDialog()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button_chnl_list, so long
        // as you specify a parent activity in AndroidManifest.xml.

        val id = item.itemId

        if (id == R.id.rate_us) {
            rating_value = 0
            ShowRatingDialog()
            /* AlertDialog.Builder adb = new AlertDialog.Builder(this);
             adb.setMessage("Do you love this App?");
             adb.setPositiveButton("I like it!", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                     // page 1 will show
                     //increaseValue();
                     startActivity(new Intent(Main2Activity.this, RateUsActivity.class));
                 }
             });
             adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                     // page 2 will show
                     //increaseValue();
                     startActivity(new Intent(Main2Activity.this, FeedbackActivity.class));
                 }
             });
             adb.show();*/
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    fun ShowRatingDialog() {
        val dialog = Dialog(this@MainActivity)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.pop_up1)
        val ll_ok = dialog.findViewById(R.id.ll_ok) as LinearLayout
        val iv_star_1 = dialog.findViewById(R.id.iv_star_1) as ImageView
        val iv_star_2 = dialog.findViewById(R.id.iv_star_2) as ImageView
        val iv_star_3 = dialog.findViewById(R.id.iv_star_3) as ImageView
        val iv_star_4 = dialog.findViewById(R.id.iv_star_4) as ImageView
        val iv_star_5 = dialog.findViewById(R.id.iv_star_5) as ImageView
        val cancelIMG = dialog.findViewById(R.id.cancelIMG) as ImageView
        cancelIMG.setOnClickListener {
            dialog.dismiss()
        }
        /* final ImageButton cancel = (ImageButton) dialog.findViewById(R.id.cancel_bt);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });*/

        ll_ok.setOnClickListener {
            if (rating_value != 0) {
                dialog.dismiss()
                if (rating_value < 4) {
                    //open feedback screen
                    //startActivity(new Intent(Main2Activity.this, FeedbackActivity.class));
                    showDialog()
                } else {
                    // showDialog();
                    //rate on google play
                    val uri = Uri.parse("market://details?id=$packageName")
                    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
                    // To count with Play market backstack, After pressing back button_chnl_list,
                    // to taken back to our application, we need to add following flags to intent.
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                    try {
                        startActivity(goToMarket)
                    } catch (e: ActivityNotFoundException) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=$packageName")))
                    }
                }
            }
        }

        dynamicRatingbar()
        val rotation = AnimationUtils.loadAnimation(this, R.anim.blinkanimation)

        iv_star_1.setOnClickListener {
            iv_star_1.setBackgroundResource(R.drawable.rate_icon)
            iv_star_2.setBackgroundResource(R.drawable.not_fill_rate_icon)
            iv_star_3.setBackgroundResource(R.drawable.not_fill_rate_icon)
            iv_star_4.setBackgroundResource(R.drawable.not_fill_rate_icon)
            iv_star_5.setBackgroundResource(R.drawable.not_fill_rate_icon)
            rating_value = 1

            // iv_star_1.startAnimation(rotation)

            handler = Handler()
            handler!!.postDelayed({
                afterratingAction()
            },1000)

        }
        iv_star_2.setOnClickListener {
            iv_star_1.setBackgroundResource(R.drawable.rate_icon)
            iv_star_2.setBackgroundResource(R.drawable.rate_icon)
            iv_star_3.setBackgroundResource(R.drawable.not_fill_rate_icon)
            iv_star_4.setBackgroundResource(R.drawable.not_fill_rate_icon)
            iv_star_5.setBackgroundResource(R.drawable.not_fill_rate_icon)
            rating_value = 2


            /* iv_star_1.startAnimation(rotation)
           iv_star_2.startAnimation(rotation)
*/
            handler = Handler()
            handler!!.postDelayed({
                afterratingAction()
            },1000)


        }
        iv_star_3.setOnClickListener {
            iv_star_1.setBackgroundResource(R.drawable.rate_icon)
            iv_star_2.setBackgroundResource(R.drawable.rate_icon)
            iv_star_3.setBackgroundResource(R.drawable.rate_icon)
            iv_star_4.setBackgroundResource(R.drawable.not_fill_rate_icon)
            iv_star_5.setBackgroundResource(R.drawable.not_fill_rate_icon)
            rating_value = 3


            /*  iv_star_1.startAnimation(rotation)
           iv_star_2.startAnimation(rotation)
           iv_star_3.startAnimation(rotation)
*/
            handler = Handler()
            handler!!.postDelayed({
                afterratingAction()
            },1000)


        }
        iv_star_4.setOnClickListener {
            iv_star_1.setBackgroundResource(R.drawable.rate_icon)
            iv_star_2.setBackgroundResource(R.drawable.rate_icon)
            iv_star_3.setBackgroundResource(R.drawable.rate_icon)
            iv_star_4.setBackgroundResource(R.drawable.rate_icon)
            iv_star_5.setBackgroundResource(R.drawable.not_fill_rate_icon)
            rating_value = 4


            /* iv_star_1.startAnimation(rotation)
           iv_star_2.startAnimation(rotation)
           iv_star_3.startAnimation(rotation)
           iv_star_4.startAnimation(rotation)
*/
            handler = Handler()
            handler!!.postDelayed({
                afterratingAction()
            },1000)

        }
        iv_star_5.setOnClickListener {
            iv_star_1.setBackgroundResource(R.drawable.rate_icon)
            iv_star_2.setBackgroundResource(R.drawable.rate_icon)
            iv_star_3.setBackgroundResource(R.drawable.rate_icon)
            iv_star_4.setBackgroundResource(R.drawable.rate_icon)
            iv_star_5.setBackgroundResource(R.drawable.rate_icon)
            rating_value = 5

            handler = Handler()
            handler!!.postDelayed({
                afterratingAction()
            },1000)

        }

        dialog.show()
    }

    fun showDialog() {
        val dialog = Dialog(this@MainActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.contact_popup_layout)
        val ll_ok = dialog.findViewById(R.id.l_ok) as LinearLayout
        ll_ok.setOnClickListener {
            dialog.dismiss()
            sendEmail()
        }
        dialog.show()
    }

    private fun afterratingAction(){
        if (rating_value != 0) {
            dialog.dismiss()
            if (rating_value < 5) {
                //open feedback screen
                //startActivity(new Intent(Main2Activity.this, FeedbackActivity.class));
                showDialog()
            } else {
                // showDialog();
                //rate on google play
                if (rating_value==5){
                    Toast.makeText(this@MainActivity,R.string.ratingMessage,Toast.LENGTH_SHORT).show()
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=$packageName")))
                }else {

                    val uri = Uri.parse("market://details?id=$packageName")
                    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
                    // To count with Play market backstack, After pressing back button_chnl_list,
                    // to taken back to our application, we need to add following flags to intent.
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                    try {
                        startActivity(goToMarket)
                    } catch (e: ActivityNotFoundException) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=$packageName")))
                    }
                }
            }
        }
    }

    private fun refreshAd(){
        // refresh.setEnabled(false);

        val builder = AdLoader.Builder(this,getString(R.string.native_ad))

        builder.forUnifiedNativeAd { unifiedNativeAd ->
            // OnUnifiedNativeAdLoadedListener implementation.
            val adView = layoutInflater
                    .inflate(R.layout.ad_unified_main, null) as UnifiedNativeAdView
            populateUnifiedNativeAdView(unifiedNativeAd, adView)
            frameLayout!!.removeAllViews()
            frameLayout!!.addView(adView)
        }

        val videoOptions = VideoOptions.Builder()
                .setStartMuted(true)
                .build()

        val adOptions = NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build()

        builder.withNativeAdOptions(adOptions)

        val adLoader = builder.withAdListener(object : AdListener() {

            override fun onAdLoaded() {
                Handler().postDelayed(Runnable {
                frameLayout!!.visibility = View.VISIBLE
                },1000)
                super.onAdLoaded()
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                frameLayout!!.visibility = View.GONE
                // refresh.setEnabled(true);
                /* Toast.makeText(ScanResultActivity.this, "Failed to load native ad: "
                        + errorCode, Toast.LENGTH_SHORT).show();*/
            }
        }).build()

        adLoader.loadAd(AdRequest.Builder().build())

    }

    private fun refreshDrawerAd() {

        val builder = AdLoader.Builder(this, getString(R.string.native_ad))

        builder.forUnifiedNativeAd { unifiedNativeAd ->
            val adView = layoutInflater
                    .inflate(R.layout.ad_unified_drawer, null) as UnifiedNativeAdView
            populateUnifiedNativeAdView(unifiedNativeAd, adView)
            frameLayoutNav!!.removeAllViews()
            frameLayoutNav!!.addView(adView)
        }

        val videoOptions = VideoOptions.Builder()
                .setStartMuted(true)
                .build()

        val adOptions = NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build()

        builder.withNativeAdOptions(adOptions)

        val adLoader = builder.withAdListener(object : AdListener() {

            // params = nav_layout!!.layoutParams

            override fun onAdLoaded() {
             //   params!!.height = resources.getDimension(R.dimen.view_height1).toInt()
              //  nav_layout!!.layoutParams = params
                frameLayoutNav!!.visibility=View.VISIBLE
                super.onAdLoaded()
            }


            override fun onAdFailedToLoad(errorCode: Int) {
                frameLayoutNav!!.visibility=View.GONE
               // params!!.height = resources.getDimension(R.dimen.view_height2).toInt()
                //nav_layout!!.layoutParams = params

            }
        }).build()

        adLoader.loadAd(AdRequest.Builder().build())

        //videoStatus.setText("");
    }


    private fun populateUnifiedNativeAdView(nativeAd: UnifiedNativeAd, adView: UnifiedNativeAdView) {

        // Set other ad assets.
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

        // The headline is guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            adView.bodyView.visibility = View.INVISIBLE
        } else {
            adView.bodyView.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            adView.callToActionView.visibility = View.INVISIBLE
        } else {
            adView.callToActionView.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            adView.iconView.visibility = View.GONE
        } else {
            (adView.iconView as ImageView).setImageDrawable(
                    nativeAd.icon.drawable)
            adView.iconView.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            adView.priceView.visibility = View.GONE
        } else {
            adView.priceView.visibility = View.GONE
            (adView.priceView as TextView).text = nativeAd.price
        }

        if (nativeAd.store == null) {
            adView.storeView.visibility = View.GONE
        } else {
            adView.storeView.visibility = View.GONE
            (adView.storeView as TextView).text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            adView.starRatingView.visibility = View.GONE
        } else {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
            adView.starRatingView.visibility = View.GONE
        }

        if (nativeAd.advertiser == null) {
            adView.advertiserView.visibility = View.GONE
        } else {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView.visibility = View.GONE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad. The SDK will populate the adView's MediaView
        // with the media content from this native ad.
        adView.setNativeAd(nativeAd)

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        val vc = nativeAd.videoController

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {
            /* videoStatus.setText(String.format(Locale.getDefault(),
                    "Video status: Ad contains a %.2f:1 video asset.",
                    vc.getAspectRatio()));*/

            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(object : VideoController.VideoLifecycleCallbacks() {
                override fun onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    /* refresh.setEnabled(true);
                    videoStatus.setText("Video status: Video playback has ended.");*/
                    super.onVideoEnd()
                }
            })
        } else {
            /*videoStatus.setText("Video status: Ad does not contain a video asset.");
            refresh.setEnabled(true);*/
        }
    }

    private fun storageTask() {
        if (!hasStoragePermissions()) {
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs of IR sensor permission.",
                    111,
                    Manifest.permission.TRANSMIT_IR)
        }
    }

    private fun hasStoragePermissions(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.TRANSMIT_IR)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            111 ->
                // Toast.makeText(getApplicationContext(), "ponPermissionsGranted", Toast.LENGTH_LONG).show();
                EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
            System.exit(0)
            return
        } else {
            if (sharedpreferences.getInt("repeat_value", 0) == 3) {
                increaseValue()
                rating_value = 0
                ShowRatingDialog()
                /*   AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setMessage("Do you love this App?");
                adb.setPositiveButton("I like it!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // page 1 will show
                        increaseValue();
                        startActivity(new Intent(Main2Activity.this, RateUsActivity.class));
                    }
                });
                adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // page 2 will show
                        increaseValue();
                        startActivity(new Intent(Main2Activity.this, FeedbackActivity.class));
                    }
                });
                adb.show();*/

            } else {
                //  drawer = findViewById(R.id.drawer_layout) as DrawerLayout
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    this.doubleBackToExitPressedOnce = true
                    Toast.makeText(this, getString(R.string.back_to_exit), Toast.LENGTH_SHORT).show()
                    Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
                }
            }
        }

    }

    /* override fun onCreateOptionsMenu(menu: Menu): Boolean {
         // Inflate the menu; this adds items to the action bar if it is present.
         menuInflater.inflate(R.menu.main, menu)
         return true
     }

     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         // Handle action bar item clicks here. The action bar will
         // automatically handle clicks on the Home/Up button_chnl_list, so long
         // as you specify a parent activity in AndroidManifest.xml.
         when (item.itemId) {
             R.id.action_language -> return true
             else -> return super.onOptionsItemSelected(item)
         }
     }*/

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            /* R.id.navigation_home -> {
                 drawer_layout.closeDrawer(GravityCompat.START)
                 supportActionBar!!.title = title
                 return true
             }*/
            R.id.nav_language ->{
                lanSelect = languageSharedpreference.getInt("LANGUAGE", 4)

                languageAlertDialog(lanSelect)
            }
            R.id.navigation_rate -> {

                ShowRatingDialog()


            }
            R.id.navigation_policy -> {

                val policy = Intent(this@MainActivity, PolicyActivity::class.java)
                startActivity(policy)
                drawer_layout.closeDrawer(GravityCompat.START)
                supportActionBar!!.title = title
                return true

            }
            R.id.navigation_more -> {

                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(AppConstants.developer_id_link)
                startActivity(i)

                /* val _intent = Intent(Intent.ACTION_VIEW)
                 _intent.data = Uri.parse(getString(R.string.moreApps))
                 startActivity(_intent)*/
                drawer_layout.closeDrawer(GravityCompat.START)
                supportActionBar!!.title = title
                return true

            }

            R.id.navigation_share -> {

                try {
                    val i = Intent(Intent.ACTION_SEND)
                    i.setType("text/plain")
                    i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name))
                    var sAux = getResources().getString(R.string.download_this) + "\n\n"
                    sAux = sAux + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n"
                    i.putExtra(Intent.EXTRA_TEXT, sAux)
                    startActivity(Intent.createChooser(i, "Choose one"))
                } catch (e: Exception) {
                    //e.toString();
                }
                /*  val shareIntent = Intent()
                  shareIntent.action = Intent.ACTION_SEND
                  shareIntent.type="text/plain"
                  shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Please Download this amazing Application \n URLxxxxxxxxxxx")
                  startActivity(Intent.createChooser(shareIntent,"Select App.."))
          */        drawer_layout.closeDrawer(GravityCompat.START)
                supportActionBar!!.title = title
                return true

            }

            /* R.id.navigation_exit -> {

                 showExitDialog()
                 *//* val homeIntent = Intent(Intent.ACTION_MAIN)
                 homeIntent.addCategory(Intent.CATEGORY_HOME)
                 homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                 startActivity(homeIntent)*//*
                drawer_layout.closeDrawer(GravityCompat.START)
                supportActionBar!!.title = title
                return true

            }*/

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun languageAlertDialog(lanSelect: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getResources().getString(R.string.change_language));

        val productsearch = arrayOf("Arabic", "Croatian", "Czech", "Dutch", "English", "Filipino", "French", "German", "Indonesian", "Italian", "Korean", "Malay", "Polish", "Portuguese", "Russian", "Serbian", "Slovak", "Slovenian", "Spanish", "Swedish", "Thai", "Turkish", "Vietnamese")

        val checkitem = lanSelect
        builder.setSingleChoiceItems(productsearch, checkitem) { dialog, which ->
            // user checked an item
            Log.v("Switch State=5", "" + checkitem)
            //Toast.makeText(getApplicationContext(),"Group Name = "+which+""+productsearch[which], Toast.LENGTH_SHORT).show();
            language = productsearch[which]
            pos = which
        }

        builder.setPositiveButton(getResources().getString(R.string.ok)
        , DialogInterface.OnClickListener { dialog, which ->
            if (language == "Arabic")
                mLanguageCode = "ar"
            else if (language == "Croatian")
                mLanguageCode = "hr"
            else if (language == "Czech")
                mLanguageCode = "cs"
            else if (language == "Dutch")
                mLanguageCode = "nl"
            else if (language == "English")
                mLanguageCode = "en"
            else if (language == "Filipino")
                mLanguageCode = "fil"
            else if (language == "French")
                mLanguageCode = "fr"
            else if (language == "German")
                mLanguageCode = "de"
            else if (language == "Indonesian")
                mLanguageCode = "in"
            else if (language == "Italian")
                mLanguageCode = "it"
            else if (language == "Korean")
                mLanguageCode = "ko"
            else if (language == "Malay")
                mLanguageCode = "ms"
            else if (language == "Polish")
                mLanguageCode = "pl"
            else if (language == "Portuguese")
                mLanguageCode = "pt"
            else if (language == "Russian")
                mLanguageCode = "ru"
            else if (language == "Serbian")
                mLanguageCode = "sr"
            else if (language == "Slovak")
                mLanguageCode = "sk"
            else if (language == "Slovenian")
                mLanguageCode = "sl"
            else if (language == "Spanish")
                mLanguageCode = "es"
            else if (language == "Swedish")
                mLanguageCode = "sv"
            else if (language == "Thai")
                mLanguageCode = "th"
            else if (language == "Turkish")
                mLanguageCode = "tr"
            else if (language == "Vietnamese")
                mLanguageCode = "vi"
            else if (language == "") {
                mLanguageCode = languageSharedpreference.getString("LANGUAGECODE", "en")
                language = languageSharedpreference.getString("LANGUAGES", "English")
                pos = languageSharedpreference.getInt("LANGUAGE", 4)
            }
            setpreforLanguage(pos, this!!.language!!, this!!.mLanguageCode!!)
            getLocale()
            dialog.dismiss()
        })
        builder.setNegativeButton(resources.getString(R.string.cancel), null)

        val dialog = builder.create()
        dialog.show()
    }

    fun getLocale() {
        val lang = languageSharedpreference.getString("LANGUAGECODE", "en")
        //  Toast.makeText(getApplicationContext(),"shared lan code = "+lang, Toast.LENGTH_SHORT).show();

        myLocale = Locale(lang)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        val refresh = Intent(this@MainActivity, MainActivity::class.java)
        startActivity(refresh)
        finish()
    }
    fun setpreforLanguage(bv: Int, lan: String, lanCode: String) {
        val editor = languageSharedpreference.edit()
        editor.putInt("LANGUAGE", bv)
        editor.putString("LANGUAGES", lan)
        editor.putString("LANGUAGECODE", lanCode)
        editor.commit()
    }

    fun showExitDialog() {
        val adb = AlertDialog.Builder(this)
        //adb.setTitle(getResources().getString(R.string.exit_desc));
        adb.setMessage(getResources().getString(R.string.exit_desc))
        adb.setPositiveButton(getResources().getString(R.string.exit_yes), object : DialogInterface.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun onClick(dialog: DialogInterface, which: Int) {
                finishAffinity()
                System.exit(0)
            }
        })
        adb.setNegativeButton(getResources().getString(R.string.exit_no), object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                dialog.dismiss()
            }
        })
        adb.show()
    }


    fun showRateDialog() {
        val adb = AlertDialog.Builder(this)
        val title = SpannableString(getResources().getString(R.string.rate_us_title))
        title.setSpan(ForegroundColorSpan(getResources().getColor(R.color.title_color)), 0, title.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        adb.setTitle(title)
        adb.setMessage(getResources().getString(R.string.rate_us_message))
        adb.setPositiveButton(getResources().getString(R.string.rate_us_ok), object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                val uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName() + "&hl=en")
                val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
                try {
                    startActivity(myAppLinkToMarket)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this@MainActivity, getString(R.string.no_app), Toast.LENGTH_LONG).show()
                }
            }
        })
        adb.setNegativeButton(getResources().getString(R.string.rate_us_cancel), object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                dialog.dismiss()
            }
        })
        adb.show()
    }


    @SuppressLint("CommitPrefEdits")
    fun increaseValue() {
        editor = sharedpreferences.edit()
        editor.putInt("repeat_value",
                sharedpreferences.getInt("repeat_value", 0) + 1)
        editor.commit()
    }

    private fun thirdtimeADB() {
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        count = sharedPreference.getInt("username",count)

        if (count == 0) {
            count += 1

            editor.putInt("username",count)
            editor.commit()

        } else {

            count += 1
            editor.putInt("username",count)
            editor.commit()

            count = sharedPreference.getInt("username",count)

            if (count==3){
                ShowRatingDialog()
            }
        }
    }

    private fun dynamicRatingbar(){
        val rotation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        val interpolator = MyBounceInterpolator(10.0, 10.0)
        rotation.setInterpolator(interpolator)

        handler = Handler()

        handler!!.postDelayed({
            iv_star_1.startAnimation(rotation)
            iv_star_1.setBackgroundResource(R.drawable.rate_icon)

        },500)

        handler!!.postDelayed({
            iv_star_2.startAnimation(rotation)

            iv_star_2.setBackgroundResource(R.drawable.rate_icon)

        },1000)

        handler!!.postDelayed({
            iv_star_3.startAnimation(rotation)
            iv_star_3.setBackgroundResource(R.drawable.rate_icon)


        },1500)

        handler!!.postDelayed({
            iv_star_4.startAnimation(rotation)
            iv_star_4.setBackgroundResource(R.drawable.rate_icon)


        },2000)

        handler!!.postDelayed({
            iv_star_5.startAnimation(rotation)
            iv_star_5.setBackgroundResource(R.drawable.rate_icon)

            //  afterratingAction()
            // rating_value = 5


        },2500)

        handler!!.postDelayed({
            iv_star_5.startAnimation(rotation)
            iv_star_1.setBackgroundResource(R.drawable.not_fill_rate_icon)
            iv_star_2.setBackgroundResource(R.drawable.not_fill_rate_icon)
            iv_star_3.setBackgroundResource(R.drawable.not_fill_rate_icon)
            iv_star_4.setBackgroundResource(R.drawable.not_fill_rate_icon)
            iv_star_5.setBackgroundResource(R.drawable.not_fill_rate_icon)

        },4000)
    }


    private fun dynamicRatingbar2(){
        val rotation = AnimationUtils.loadAnimation(this, R.anim.ringleblinkanimation)

        if(rating_value==5) {

            handler = Handler()

            handler!!.postDelayed({
                iv_star_1.startAnimation(rotation)
                iv_star_1.setBackgroundResource(R.drawable.rate_icon)


            }, 200)


            handler!!.postDelayed({
                iv_star_2.startAnimation(rotation)
                iv_star_2.setBackgroundResource(R.drawable.rate_icon)


            }, 400)

            handler!!.postDelayed({
                iv_star_3.startAnimation(rotation)
                iv_star_3.setBackgroundResource(R.drawable.rate_icon)


            }, 600)

            handler!!.postDelayed({
                iv_star_4.startAnimation(rotation)
                iv_star_4.setBackgroundResource(R.drawable.rate_icon)


            }, 800)

            handler!!.postDelayed({
                iv_star_5.startAnimation(rotation)
                iv_star_5.setBackgroundResource(R.drawable.rate_icon)

                //  afterratingAction()
                // rating_value = 5

            }, 1000)

            handler!!.postDelayed({
                iv_star_5.startAnimation(rotation)
                iv_star_1.setBackgroundResource(R.drawable.rate_icon)
                iv_star_2.setBackgroundResource(R.drawable.rate_icon)
                iv_star_3.setBackgroundResource(R.drawable.rate_icon)
                iv_star_4.setBackgroundResource(R.drawable.rate_icon)
                iv_star_5.setBackgroundResource(R.drawable.rate_icon)

            }, 600)

        }
    }

}