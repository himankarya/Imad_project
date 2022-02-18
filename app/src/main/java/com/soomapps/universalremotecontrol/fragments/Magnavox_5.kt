package com.soomapps.universalremotecontrol.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.hardware.ConsumerIrManager
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import com.soomapps.universalremotecontrol.R
//import com.example.tvremote.R
//import com.example.tvremote.SingleRemoteActivity
import pub.devrel.easypermissions.EasyPermissions
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.*

class Magnavox_5 : Fragment(), View.OnClickListener {


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onClick(p0: View?) {

        prefs = activity!!.getSharedPreferences("SwitchButton", Context.MODE_PRIVATE)
        if (prefs!!.getBoolean("NameOfThingToSave", true)) {
            irSend(p0!!)
        } else{
            transmitSignal(p0!!)
        }
    }

    var irService: Any? = null
    var mMethod: Method? = null
    var sParseArray: SparseArray<IRCommand>? = null
    var mIRManager: ConsumerIrManager? = null
    var isIRSensorAvailable: Boolean? = null
    var viewFlipperBTN: Button? = null
    var viewFlipper: ViewFlipper? = null
    var count: Int = 0
    private lateinit var v: Vibrator
    val PREFS_NAME = "SwitchButton"
    var prefs: SharedPreferences? = null

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.activity_remote, container, false)
        prefs = activity!!.getSharedPreferences("SwitchButton", Context.MODE_PRIVATE)

        initIRManager()
//        refreshAd(view)

        v= context!!.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        this.isIRSensorAvailable = mIRManager!!.hasIrEmitter() //applicationContext.packageManager.hasSystemFeature("android.hardware.consumerir")
        Log.d("MYLOGTAG", isIRSensorAvailable.toString())//manager.hasIrEmitter()
        // this.f2153g = getSharedPreferences("noAds_KEY_SP", 0).getInt("noAds_KEY", 0)
        //  this.f2164r = FirebaseAnalytics.getInstance(this);
        this.count = 0


        // m3251a(true, true)
        // m3247c();
        // this.f2154h.m5853a(new C29121(this));
        // button_chnl_list.setOnClickListener(new C09402(this));
        this.viewFlipperBTN = view.findViewById(R.id.view_flipper_button);
        this.viewFlipper = view.findViewById(R.id.viewflipper);
        // this.f2161o = AnimationUtils.loadAnimation(this, 17432576);
        // this.f2162p = AnimationUtils.loadAnimation(this, 17432577);
        // this.viewFlipper!!.setInAnimation(this.f2161o);
        // this.viewFlipper!!.setOutAnimation(this.f2162p);

        (view.findViewById(R.id.mute) as Button).setOnClickListener(this)
        (view.findViewById(R.id.powerOnOff) as Button).setOnClickListener(this)
        (view.findViewById(R.id.buttonAV) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button0) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button1) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button2) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button3) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button4) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button5) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button6) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button7) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button8) as Button).setOnClickListener(this)
        (view.findViewById(R.id.button9) as Button).setOnClickListener(this)
        (view.findViewById(R.id.index) as Button).setOnClickListener(this)
        (view.findViewById(R.id.Ch_list) as Button).setOnClickListener(this)
        (view.findViewById(R.id.Ok_Up) as Button).setOnClickListener(this)
        (view.findViewById(R.id.volume_UP) as Button).setOnClickListener(this)
        (view.findViewById(R.id.channel_UP) as Button).setOnClickListener(this)
        (view.findViewById(R.id.Ok_left) as Button).setOnClickListener(this)
        (view.findViewById(R.id.Ok) as Button).setOnClickListener(this)
        (view.findViewById(R.id.Ok_right) as Button).setOnClickListener(this)
        (view.findViewById(R.id.volume_DOWN) as Button).setOnClickListener(this)
        (view.findViewById(R.id.channel_DOWN) as Button).setOnClickListener(this)
        (view.findViewById(R.id.OK_Down) as Button).setOnClickListener(this)
        (view.findViewById(R.id.menu_full) as Button).setOnClickListener(this)
        (view.findViewById(R.id.red) as Button).setOnClickListener(this)
        (view.findViewById(R.id.green) as Button).setOnClickListener(this)
        (view.findViewById(R.id.blue) as Button).setOnClickListener(this)
        (view.findViewById(R.id.yellow) as Button).setOnClickListener(this)



        this.viewFlipperBTN!!.setOnClickListener(FlipListener())
        this.sParseArray = SparseArray()

        this.sParseArray!!.put(R.id.powerOnOff, convertStringToArray("0000 0073 0000 0015 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0010 0010 0020 0010 0010 0010 0BD1"));
        this.sParseArray!!.put(R.id.mute, convertStringToArray("0000 0073 0000 0014 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0010 0010 0020 0020 0BE1"));
        this.sParseArray!!.put(R.id.buttonAV, convertStringToArray("0000 0073 0000 0015 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0010 0010 0010 0010 0020 0010 0010 0010 0010 0010 0BD1"));
        this.sParseArray!!.put(R.id.button1, convertStringToArray("0000 0073 0000 0015 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0BE1"));
        this.sParseArray!!.put(R.id.button2, convertStringToArray("0000 0073 0000 0015 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0020 0010 0BD1"));
        this.sParseArray!!.put(R.id.button3, convertStringToArray("0000 0073 0000 0015 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0010 0010 0BE1"));
        this.sParseArray!!.put(R.id.button4, convertStringToArray("0000 0073 0000 0015 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0BD1"));
        this.sParseArray!!.put(R.id.button5, convertStringToArray("0000 0073 0000 0014 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0020 0020 0BE1"));
        this.sParseArray!!.put(R.id.button6, convertStringToArray("0000 0073 0000 0015 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0010 0010 0020 0010 0BD1"));
        this.sParseArray!!.put(R.id.button7, convertStringToArray("0000 0073 0000 0015 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0010 0010 0010 0010 0BE1"));
        this.sParseArray!!.put(R.id.button8, convertStringToArray("0000 0073 0000 0015 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0BD1"));
        this.sParseArray!!.put(R.id.button9, convertStringToArray("0000 0073 0000 0014 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0020 0010 0010 0020 0BE1"));
        this.sParseArray!!.put(R.id.button0, convertStringToArray("0000 0073 0000 0016 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0BD1"));
        this.sParseArray!!.put(R.id.volume_UP, convertStringToArray("0000 0073 0000 0015 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0BD1"));
        this.sParseArray!!.put(R.id.volume_DOWN, convertStringToArray("0000 0073 0000 0014 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0020 0BE1"));
        this.sParseArray!!.put(R.id.channel_UP, convertStringToArray("0000 0073 0000 0015 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0BD1"));
        this.sParseArray!!.put(R.id.channel_DOWN, convertStringToArray("0000 0073 0000 0014 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0020 0BE1"));
        this.sParseArray!!.put(R.id.menu_full, convertStringToArray("0000 0073 0000 0013 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0020 0020 0020 0020 0020 0010 0010 0010 0BD1"));
        this.sParseArray!!.put(R.id.Ok, convertStringToArray("0000 0073 0000 0014 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0020 0020 0010 0010 0010 0010 0020 0010 0010 0010 0BD1"));
        this.sParseArray!!.put(R.id.Ok_left, convertStringToArray("0000 0073 0000 0013 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0020 0020 0010 0010 0020 0020 0020 0010 0BD1"));
        this.sParseArray!!.put(R.id.Ok_right, convertStringToArray("0000 0073 0000 0013 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0020 0020 0010 0010 0020 0020 0010 0010 0BE1"));
        this.sParseArray!!.put(R.id.Ok_Up, convertStringToArray("0000 0073 0000 0014 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0020 0020 0010 0010 0020 0010 0010 0010 0010 0010 0BD1"));
        this.sParseArray!!.put(R.id.OK_Down, convertStringToArray("0000 0073 0000 0013 0060 0020 0010 0020 0010 0010 0010 0010 0010 0020 0020 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0010 0020 0020 0020 0010 0010 0020 0010 0010 0020 0BE1"));


        return view
    }


    internal inner class FlipListener() : View.OnClickListener {

        override fun onClick(view: View) {
            viewFlipper!!.showNext()
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun transmitSignal(view: View) {

        val str: IRCommand? =this.sParseArray!!.get(view.id)
        //  val str = this.sParseArray!!.get(view.id).toString()
        Log.d("MYLOGTAGR", "$str")
        if (str != null && this.isIRSensorAvailable!!) {
            // val split = str.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            /* val split: Array<String> = str.split(" ").toTypedArray()
             Log.d("MYLOGTASR12", "${split.size}")

             // val split = str.split(",")

             val iArr = IntArray(split.size - 1)

             Log.d("MYLOGTASRO", "${iArr.size}")
             var i = 0
             while (i < iArr.size) {
                 val i2 = i + 1
                 iArr[i] = Integer.parseInt(split[i2], 16)
                 Log.d("PATTERN_TAG", Integer.toString(iArr[i]))
                 i = i2
             }
             Log.d("MYLOGTlO1", "${split[0]}")
             Log.d("MYLOGTlO", "$iArr")*/
            //IR.transmit(frequency(int), pattern(int array));
            try {

                android.util.Log.d("RemoteTAG11", "frequency: " + str.freq)
                android.util.Log.d("RemoteTAG12", "pattern: " + Arrays.toString(str.pattern))

                this.mIRManager!!.transmit(str.freq, str.pattern)
                // this.mIRManager!!.transmit(Integer.parseInt(split[0]), iArr)
                // Log.d("FREQ_TAG", split[0])
            } catch (e: Exception) {
                Log.d("FREQ_TAG", "$e")
                e.printStackTrace()
            }

        }
    }


    fun invokeSystemService(view: View) {
        if (this.sParseArray!!.get(view.id) as String != null && this.isIRSensorAvailable!!) {
            try {
                this.mMethod!!.invoke(this.irService, arrayOf<Any>(String::class.java))
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e2: IllegalAccessException) {
                e2.printStackTrace()
            } catch (e3: InvocationTargetException) {
                e3.printStackTrace()
            }

        }
    }

/*fun m3248d() {
    if (this.f2154h.m5859a()) {
        this.f2154h.m5860b()
    }
}*/


    /* renamed from: a */
    private fun convertStringToArray(irData: String): IRCommand {
        val list = ArrayList(Arrays.asList(*irData.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()))
        list.removeAt(0) // dummy
        var frequency = Integer.parseInt(list.removeAt(0), 16) // frequency
        list.removeAt(0) // seq1
        list.removeAt(0) // seq2

        frequency = (1000000 / (frequency * 0.241246)).toInt()
        val pulses = 1000000 / frequency
        var count: Int

        val pattern = IntArray(list.size)
        for (i in list.indices) {
            count = Integer.parseInt(list[i], 16)
            pattern[i] = count * pulses
        }

        return IRCommand(frequency, pattern)
    }
    inner class IRCommand constructor(val freq: Int, val pattern: IntArray)




    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun initIRManager() {
        //  this.mIRManager = getSystemService("consumer_ir") as ConsumerIrManager
        this.mIRManager = context!!.getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager

        // mIRManager!!.hasIrEmitter()

        //  Log.d("MYLOGTAG898", "${mIRManager!!.hasIrEmitter()}")
    }


    @SuppressLint("WrongConstant")
    fun initIRService() {
        irService = context!!.getSystemService("irda")
        // f2147a.javaClass.getM
        try {
            this.mMethod = this.irService!!.javaClass.getMethod("write_irsend", *arrayOf<Class<*>>(String::class.java))
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }

    }



    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun irSend(view: View) {

        v.vibrate(100)
        if (hasStoragePermissions()) {
            Log.d("MYLOGTAG", "CALLED")
            // this.count++
            if (Build.VERSION.SDK_INT >= 19) {
                transmitSignal(view)
                /* if (this.count === 7) {
                     this.count = 0
                     val bundle = Bundle()
                     bundle.putString("group_id", "Remote_Button_Clicks")
                     val stringBuilder = StringBuilder()
                     stringBuilder.append(localClassName)
                     stringBuilder.append("_SRC")
                     bundle.putString("item_id", stringBuilder.toString())
                     //  this.f2164r.logEvent("join_group", bundle)
                     return
                 }*/
            }
            // invokeSystemService(view)
        } else {
            storageTask()
        }
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun storageTask() {
        if (!hasStoragePermissions()) {
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to save your  pictures in storage.",
                    111,
                    Manifest.permission.TRANSMIT_IR)
        }
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun hasStoragePermissions(): Boolean {
        return EasyPermissions.hasPermissions(context, Manifest.permission.TRANSMIT_IR)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            111 ->
                // Toast.makeText(getApplicationContext(), "ponPermissionsGranted", Toast.LENGTH_LONG).show();
                EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun refreshAd(view:View) {
        // refresh.setEnabled(false);

        val builder = AdLoader.Builder(context, getString(R.string.native_ad))

        builder.forUnifiedNativeAd { unifiedNativeAd ->
            // OnUnifiedNativeAdLoadedListener implementation.
            val frameLayout = view.findViewById<FrameLayout>(R.id.fl_adplaceholder)
            val adView = layoutInflater
                    .inflate(R.layout.ad_unified_remote, null) as UnifiedNativeAdView
            populateUnifiedNativeAdView(unifiedNativeAd, adView)
            frameLayout.removeAllViews()
            frameLayout.addView(adView)
        }

        val videoOptions = VideoOptions.Builder()
                .setStartMuted(true)
                .build()

        val adOptions = NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build()

        builder.withNativeAdOptions(adOptions)

        val adLoader = builder.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(errorCode: Int) {
                // refresh.setEnabled(true);
                /* Toast.makeText(ScanResultActivity.this, "Failed to load native ad: "
                        + errorCode, Toast.LENGTH_SHORT).show();*/
            }
        }).build()

        adLoader.loadAd(AdRequest.Builder().build())

        //videoStatus.setText("");
    }


    private fun populateUnifiedNativeAdView(nativeAd: UnifiedNativeAd, adView: UnifiedNativeAdView) {
        // Set the media view. Media content will be automatically populated in the media view once
        // adView.setNativeAd() is called.
        /* MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);
*/
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

}