package com.soomapps.universalremotecontrol

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.*
import com.soomapps.universalremotecontrol.adapters.RecyclerViewAdapter
import com.soomapps.universalremotecontrol.db.AppDatabase
import com.soomapps.universalremotecontrol.db.MIGRATION_1_2
import com.soomapps.universalremotecontrol.db.MIGRATION_2_3
import com.soomapps.universalremotecontrol.dto.DataModel
import com.soomapps.universalremotecontrol.utils.ConnectionDetector
import com.soomapps.universalremotecontrol.utils.RecyclerItemClickListenerMain
import kotlin.concurrent.thread


class TVListActivity : AppCompatActivity() {

    private var mDataset: java.util.ArrayList<DataModel>? = null
    private lateinit var roomDatabase: AppDatabase
    private var recyclerView: RecyclerView? = null
    private lateinit var searchView: SearchView
    private var mLayoutManager: LinearLayoutManager? = null
    private var adBRequest: AdRequest? = null
    private var mAdMobInterstitialAd: InterstitialAd? = null
    internal var beanObjectList: MutableList<Any>? = null

    var menuItem: ArrayList<DataModel>? = null
    // The AdLoader used to load ads.
    var adLoader: AdLoader? = null
    // List of native ads that have been successfully loaded.
    var mNativeAds: UnifiedNativeAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvlist)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.elevation = 0f
        supportActionBar!!.title = getString(R.string.select_your_tv_brand)

        //refreshAd()
        //creating room database for favorite
        roomDatabase = Room.databaseBuilder(applicationContext,
                AppDatabase::class.java, "fav_db")
                .fallbackToDestructiveMigration()
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()

        //Interstitial Ads
        mAdMobInterstitialAd = InterstitialAd(this@TVListActivity)
        adBRequest = AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("0D18E7ADF186A5703273874B522EF74B")
                .build()
        mAdMobInterstitialAd!!.adUnitId = getString(R.string.interstitial_full_screen)
        mAdMobInterstitialAd!!.loadAd(adBRequest)

        searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.searchView) as SearchView

        val searchEditText = searchView.findViewById(R.id.search_src_text) as EditText
        searchEditText.setTextColor(resources.getColor(R.color.white))
        searchEditText.setHintTextColor(resources.getColor(R.color.white))
        searchEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, 0, 0)

//        val searchSubmit = searchView.findViewById(android.support.v7.appcompat.R.id.search_go_btn) as ImageView
//        searchSubmit.setColorFilter (Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP)
//        searchSubmit.setImageResource(R.drawable.ic_search)

        recyclerView = findViewById<RecyclerView>(R.id.recycleView) as RecyclerView

        //hideSoftKeyboard
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchView.windowToken, 0)
        searchView.setIconifiedByDefault(false)
        searchView.setFocusable(false)

        mDataset = ArrayList<DataModel>()
        prepareDataList()
        menuItem = beanObjectList as ArrayList<DataModel>

        //ads in list
        //loadNativeAds()
        mDataset!!.addAll(menuItem!!)

        //setup recyclerView
        // 1. get a reference to recyclerView
        // 2. set layoutManger
        mLayoutManager = LinearLayoutManager(this@TVListActivity)

        recyclerView!!.layoutManager = mLayoutManager
        // smooth scrolling
        recyclerView!!.isNestedScrollingEnabled = false
        //mLayoutManager!!.reverseLayout = true
        //  mLayoutManager!!.stackFromEnd = true
        // recyclerView!!.stopScroll()
        // 5. set item animator to DefaultAnimator
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.addOnItemTouchListener(
                RecyclerItemClickListenerMain(this@TVListActivity, recyclerView!!, object : RecyclerItemClickListenerMain.OnItemClickListener {

                    override fun onItemLongClick(view: View?, position: Int) {
                    }
                    @RequiresApi(Build.VERSION_CODES.M)
                    override fun onItemClick(view: View?, position: Int) {

                        if ((beanObjectList!![position]) is UnifiedNativeAd) {
                        } else {
                            if (view is ImageView) {

                                //First Crash happened here.
                                //STACK_TRACE=kotlin.TypeCastException: null cannot be cast to non-null type android.widget.ImageView
                                val imageView: ImageView = view.findViewById<ImageView>(R.id.favIV) as ImageView

                                thread {
                                    val menuItem = beanObjectList!![position] as DataModel
                                    val isExist = roomDatabase.favoriteDao().isExist(menuItem.title)

                                    if (!isExist) {

                                        roomDatabase.favoriteDao().insertOnlySingleRecord(menuItem)

                                        runOnUiThread {
                                            Toast.makeText(applicationContext, getString(R.string.added_to_fav), Toast.LENGTH_SHORT).show()
                                            imageView.setImageResource(R.drawable.filled_favourite_new)
                                            //   recyclerView!!.adapter!!.notifyDataSetChanged()
                                            //  isFav = true
                                            // resultTextView.text = FlipTableConverters.fromIterable(customers, Customers::class.java)
                                        }

                                        // }

                                    } else {
                                        //thread {

                                        /* if (position == 0) {
                                             roomDatabase.favoriteDao().updateRecord(dataModel!![position])

                                         } else {*/
                                        roomDatabase.favoriteDao().deleteRecord(menuItem)

                                        runOnUiThread {
                                            Toast.makeText(applicationContext, "Removed as Favorite", Toast.LENGTH_SHORT).show()
                                            imageView.setImageResource(R.drawable.border_favourite_new)
                                        }
                                    }

                                }
                            } else {

                                if (ConnectionDetector.checkInternetConnection(this@TVListActivity)) {
                                    if (mAdMobInterstitialAd!!.isLoaded) {
                                        mAdMobInterstitialAd!!.show()
                                    }else{
                                        mAdMobInterstitialAd!!.loadAd(adBRequest)
                                        val menuItem = beanObjectList!![position] as DataModel

                                        if ((menuItem.totalFragments) > 1 && (menuItem.totalFragments) != 0) {
                                            val intent = Intent(this@TVListActivity, MultipleRemoteActivity::class.java)
                                            val dataOnThePosition = menuItem
                                            intent.putExtra("Single", dataOnThePosition)
                                            intent.putExtra("Position", position)
                                            intent.putExtra("Object", menuItem)
                                            startActivity(intent)
//                                                Handler().postDelayed({
//
//                                                }, 5)
                                        } else if ((menuItem.totalFragments) == 0) {
                                            val intent = Intent(this@TVListActivity, WifiTVRemoteActivity::class.java)
                                            val dataOnThePosition = menuItem
                                            intent.putExtra("Single", dataOnThePosition)
                                            intent.putExtra("Position", position)
                                            intent.putExtra("Object", menuItem)
                                            startActivity(intent)
                                        } else {
                                            val intent = Intent(this@TVListActivity, SingleRemoteActivity::class.java)
                                            val dataOnThePosition = menuItem
                                            intent.putExtra("Single", dataOnThePosition)
                                            intent.putExtra("Position", position)
                                            intent.putExtra("Object", menuItem)
                                            startActivity(intent)
//                                                Handler().postDelayed({
//
//                                                }, 5)
                                        }
                                    }

                                    mAdMobInterstitialAd!!.adListener = object : AdListener() {
                                        override fun onAdLoaded() {
                                            // Code to be executed when an ad finishes loading.
                                        }

                                        override fun onAdFailedToLoad(errorCode: Int) {
                                            // Code to be executed when an ad request fails.
                                        }

                                        override fun onAdOpened() {
                                            // Code to be executed when the ad is displayed.
                                        }

                                        override fun onAdLeftApplication() {
                                            // Code to be executed when the user has left the app.
                                        }

                                        override fun onAdClosed() {
                                            // Code to be executed when when the interstitial ad is closed.
                                            mAdMobInterstitialAd!!.loadAd(adBRequest)
                                            val menuItem = beanObjectList!![position] as DataModel

                                            if ((menuItem.totalFragments) > 1 && (menuItem.totalFragments) != 0) {
                                                val intent = Intent(this@TVListActivity, MultipleRemoteActivity::class.java)
                                                val dataOnThePosition = menuItem
                                                intent.putExtra("Single", dataOnThePosition)
                                                intent.putExtra("Position", position)
                                                intent.putExtra("Object", menuItem)
                                                startActivity(intent)
//                                                Handler().postDelayed({
//
//                                                }, 5)
                                            } else if ((menuItem.totalFragments) == 0) {
                                                val intent = Intent(this@TVListActivity, WifiTVRemoteActivity::class.java)
                                                val dataOnThePosition = menuItem
                                                intent.putExtra("Single", dataOnThePosition)
                                                intent.putExtra("Position", position)
                                                intent.putExtra("Object", menuItem)
                                                startActivity(intent)
                                            } else {
                                                val intent = Intent(this@TVListActivity, SingleRemoteActivity::class.java)
                                                val dataOnThePosition = menuItem
                                                intent.putExtra("Single", dataOnThePosition)
                                                intent.putExtra("Position", position)
                                                intent.putExtra("Object", menuItem)
                                                startActivity(intent)
//                                                Handler().postDelayed({
//
//                                                }, 5)
                                            }
                                        }
                                    }
                                } else {
                             //       Toast.makeText(applicationContext, "No Internet Connection Available", Toast.LENGTH_SHORT).show()
                                    val menuItem = beanObjectList!![position] as DataModel

                                    if ((menuItem.totalFragments) > 1 && (menuItem.totalFragments) != 0) {
                                        val intent = Intent(this@TVListActivity, MultipleRemoteActivity::class.java)
                                        val dataOnThePosition = menuItem
                                        intent.putExtra("Single", dataOnThePosition)
                                        intent.putExtra("Position", position)
                                        intent.putExtra("Object", menuItem)
                                        startActivity(intent)
//                                        Handler().postDelayed({
//
//                                        }, 5)
                                    } else if ((menuItem.totalFragments) == 0) {
                                        val intent = Intent(this@TVListActivity, WifiTVRemoteActivity::class.java)
                                        val dataOnThePosition = menuItem
                                        intent.putExtra("Single", dataOnThePosition)
                                        intent.putExtra("Position", position)
                                        intent.putExtra("Object", menuItem)
                                        startActivity(intent)
                                    } else {
                                        val intent = Intent(this@TVListActivity, SingleRemoteActivity::class.java)
                                        val dataOnThePosition = menuItem
                                        intent.putExtra("Single", dataOnThePosition)
                                        intent.putExtra("Position", position)
                                        intent.putExtra("Object", menuItem)
                                        startActivity(intent)
//                                        Handler().postDelayed({
//
//                                        }, 5)
                                    }
                                }
                            }
                        }
                    }
                }))
        recyclerView!!.adapter = RecyclerViewAdapter(beanObjectList!!, this@TVListActivity)
        recyclerView!!.adapter!!.notifyDataSetChanged()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                filterRecyclerView(p0!!)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filterRecyclerView(p0!!)
                return true
            }
        })
    }


    // method for ads in list
    private fun loadNativeAds() {

        val builder = AdLoader.Builder(this, getString(R.string.native_ad))
        adLoader = builder.forUnifiedNativeAd { unifiedNativeAd ->

            mNativeAds = unifiedNativeAd

        }.withAdListener(
                object : AdListener() {
                    override fun onAdFailedToLoad(errorCode: Int) {

                    }
                    override fun onAdLoaded() {
                        try {
                            if (beanObjectList != null) {
                                if (beanObjectList!!.size > 0) {
                                    for (i in 3 until beanObjectList!!.size - 4) {
                                        if (i % 5 == 0) {
                                            beanObjectList!!.add(i, mNativeAds!!)
                                            recyclerView!!.adapter!!.notifyDataSetChanged()
                                        }
                                    }
                                }
                            }
                        } catch (e: Exception) {
                        }

                        super.onAdLoaded()
                    }
                }).build()

        adLoader!!.loadAd(AdRequest.Builder().build())
    }


//    private fun refreshAd() {
//
//        val builder = AdLoader.Builder(this, getString(R.string.native_ad))
//
//        builder.forUnifiedNativeAd { unifiedNativeAd ->
//
//            val frameLayout = findViewById<FrameLayout>(R.id.fl_adplaceholder)
//            val adView = layoutInflater
//                    .inflate(R.layout.ad_unified, null) as UnifiedNativeAdView
//            populateUnifiedNativeAdView(unifiedNativeAd, adView)
//            frameLayout.removeAllViews()
//            frameLayout.addView(adView)
//        }
//
//        val videoOptions = VideoOptions.Builder()
//                .setStartMuted(true)
//                .build()
//
//        val adOptions = NativeAdOptions.Builder()
//                .setVideoOptions(videoOptions)
//                .build()
//
//        builder.withNativeAdOptions(adOptions)
//
//        val adLoader = builder.withAdListener(object : AdListener() {
//            override fun onAdFailedToLoad(errorCode: Int) {
//            }
//
//
//        }).build()
//
//        adLoader.loadAd(AdRequest.Builder().build())
//
//    }

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

        }
    }


    fun prepareDataList() {

        beanObjectList = java.util.ArrayList<Any>()
//        beanObjectList!!.add(DataModel(0, "Apple Smart", 0))//1
//        beanObjectList!!.add(DataModel(1, "Chromecast Smart", 0))//1
//        beanObjectList!!.add(DataModel(2, "LG Smart", 0))//1
        beanObjectList!!.add(DataModel(3, "Acer", 1,0))//1
        beanObjectList!!.add(DataModel(4, "Admiral", 1,0))//1
        beanObjectList!!.add(DataModel(5, "Aiwa", 2,0))//2
        beanObjectList!!.add(DataModel(6, "Akai", 12,0))//12
        beanObjectList!!.add(DataModel(7, "Alba", 2,0))//2
        beanObjectList!!.add(DataModel(8, "AOC", 3,0))//3
        beanObjectList!!.add(DataModel(9, "Apex", 3,0))//3
        beanObjectList!!.add(DataModel(10, "ASUS", 1,0))//1
        beanObjectList!!.add(DataModel(11, "Atec", 1,0))//1
        beanObjectList!!.add(DataModel(12, "Atlanta DTH/STB", 4,0))//4
        beanObjectList!!.add(DataModel(13, "AudioSonic", 1,0))//1
        beanObjectList!!.add(DataModel(14, "AudioVox", 3,0))//3
        beanObjectList!!.add(DataModel(15, "Bahun", 2,0))//2
        beanObjectList!!.add(DataModel(16, "BBK", 1,0))//1
        beanObjectList!!.add(DataModel(17, "Beko", 1,0))//1
        beanObjectList!!.add(DataModel(18, "BGH", 1,0))//1
        beanObjectList!!.add(DataModel(19, "Blaupunkt", 1,0))//1
        beanObjectList!!.add(DataModel(20, "Broksonic", 1,0))//1
        beanObjectList!!.add(DataModel(21, "Bush", 1,0))//1
        beanObjectList!!.add(DataModel(22, "CCE", 2,0))//2
        beanObjectList!!.add(DataModel(23, "Changhong", 2,0))//2
        beanObjectList!!.add(DataModel(24, "Challenger STB", 1,0))//1
        beanObjectList!!.add(DataModel(25, "Challenger TV", 1,0))//1
        beanObjectList!!.add(DataModel(26, "Coby", 4,0))//4
        beanObjectList!!.add(DataModel(27, "Colby", 1,0))//1
        beanObjectList!!.add(DataModel(28, "Comcast STB", 1,0))//1
        beanObjectList!!.add(DataModel(29, "Condor", 1,0))//1
        beanObjectList!!.add(DataModel(30, "Continental", 1,0))//1
        beanObjectList!!.add(DataModel(31, "Daewoo", 2,0))//2
        beanObjectList!!.add(DataModel(32, "Dell", 1,0))//1
        beanObjectList!!.add(DataModel(33, "Denon", 1,0))//1
        beanObjectList!!.add(DataModel(34, "Dick Smith", 2,0))//2
        beanObjectList!!.add(DataModel(35, "Durabrand", 2,0))//2
        beanObjectList!!.add(DataModel(36, "Dynex", 2,0))//2
        beanObjectList!!.add(DataModel(37, "Ecco", 1,0))//1
        beanObjectList!!.add(DataModel(38, "EchoStar STB", 1,0))//1
        beanObjectList!!.add(DataModel(39, "Elekta", 1,0))//1
        beanObjectList!!.add(DataModel(40, "Element", 2,0))//2
        beanObjectList!!.add(DataModel(41, "Emerson", 10,0))//10
        beanObjectList!!.add(DataModel(42, "Fujitsu", 1,0))//1
        beanObjectList!!.add(DataModel(43, "Funai", 2,0))//2
        beanObjectList!!.add(DataModel(44, "GoldMaster STB", 2,0))//2
        beanObjectList!!.add(DataModel(45, "GoldStar", 2,0))//2
        beanObjectList!!.add(DataModel(46, "Grundig", 2,0))//2
        beanObjectList!!.add(DataModel(47, "Haier", 11,0))//11
        beanObjectList!!.add(DataModel(48, "Hisense", 1,0))//1
        beanObjectList!!.add(DataModel(49, "Hitachi", 5,0))//5
        beanObjectList!!.add(DataModel(50, "Horizon STB", 1,0))//1
        beanObjectList!!.add(DataModel(51, "Humax", 1,0))//1
        beanObjectList!!.add(DataModel(52, "Hyundai", 3,0))//3
        beanObjectList!!.add(DataModel(53, "Ilo", 1,0))//1
        beanObjectList!!.add(DataModel(54, "Insignia", 3,0))//3
        beanObjectList!!.add(DataModel(55, "ISymphony", 1,0))//1
        beanObjectList!!.add(DataModel(56, "Jensen", 2,0))//2
        beanObjectList!!.add(DataModel(57, "JVC", 4,0))//4
        beanObjectList!!.add(DataModel(58, "Kendo", 1,0))//1
        beanObjectList!!.add(DataModel(59, "Kogan", 1,0))//1
        beanObjectList!!.add(DataModel(60, "Kolin", 1,0))//1
        beanObjectList!!.add(DataModel(61, "Konka", 1,0))//1
        beanObjectList!!.add(DataModel(62, "LG", 1,0))//1
        beanObjectList!!.add(DataModel(63, "Logik", 6,0))//6
        beanObjectList!!.add(DataModel(64, "Loewe", 2,0))//2
        beanObjectList!!.add(DataModel(65, "Magnavox", 5,0))//5
        beanObjectList!!.add(DataModel(66, "Mascom", 5,0))//5
        beanObjectList!!.add(DataModel(67, "Medion STB", 7,0))//7
        beanObjectList!!.add(DataModel(68, "Medion TV", 4,0))//4
        beanObjectList!!.add(DataModel(69, "Micromax", 1,0))//1
        beanObjectList!!.add(DataModel(70, "Mitsai", 1,0))//1
        beanObjectList!!.add(DataModel(71, "Mitsubishi", 2,0))//2
        beanObjectList!!.add(DataModel(72, "Mystery", 2,0))//2
        beanObjectList!!.add(DataModel(73, "NEC", 5,0))//5
        beanObjectList!!.add(DataModel(74, "Next STB", 3,0))//3
        beanObjectList!!.add(DataModel(75, "Nexus", 2,0))//2
        beanObjectList!!.add(DataModel(76, "NFusion STB", 1,0))//1
        beanObjectList!!.add(DataModel(77, "Nikai", 1,0))//1
        beanObjectList!!.add(DataModel(78, "Niko", 2,0))//2
        beanObjectList!!.add(DataModel(79, "Noblex", 1,0))//1
        beanObjectList!!.add(DataModel(80, "OKI", 1,0))//1
        beanObjectList!!.add(DataModel(81, "Olevia", 2,0))//2
        beanObjectList!!.add(DataModel(82, "Onida", 1,0))//1
        beanObjectList!!.add(DataModel(83, "Orange STB", 1,0))//1
        beanObjectList!!.add(DataModel(84, "Orion", 5,0))//5
        beanObjectList!!.add(DataModel(85, "Palsonic", 2,0))//2
        beanObjectList!!.add(DataModel(86, "Panasonic", 1,0))//1
        beanObjectList!!.add(DataModel(87, "Philco", 3,0))//3
        beanObjectList!!.add(DataModel(88, "PHILIPS", 1,0))//1
        beanObjectList!!.add(DataModel(89, "Pioneer", 7,0))//7
        beanObjectList!!.add(DataModel(90, "Polaroid", 5,0))//5
        beanObjectList!!.add(DataModel(91, "Prima", 2,0))//2
        beanObjectList!!.add(DataModel(92, "Promac", 1,0))//1
        beanObjectList!!.add(DataModel(93, "Proscan", 1,0))//1
        beanObjectList!!.add(DataModel(94, "RCA", 6,0))//6
        beanObjectList!!.add(DataModel(95, "Reliance STB", 1,0))//1
        beanObjectList!!.add(DataModel(96, "Rubin", 1,0))//1
        beanObjectList!!.add(DataModel(97, "Saba", 1,0))//1
        beanObjectList!!.add(DataModel(98, "SAMSUNG", 1,0))//1
        beanObjectList!!.add(DataModel(99, "Sansui", 1,0))//1
        beanObjectList!!.add(DataModel(100, "Sanyo", 1,0))//1
        beanObjectList!!.add(DataModel(101, "Scott", 1,0))//1
        beanObjectList!!.add(DataModel(102, "SEG", 2,0))//2
        beanObjectList!!.add(DataModel(103, "Seiki", 1,0))//1
        beanObjectList!!.add(DataModel(104, "SHARP", 1,0))//1
        beanObjectList!!.add(DataModel(105, "Shivaki", 1,0))//1
        beanObjectList!!.add(DataModel(106, "Singer", 1,0))//1
        beanObjectList!!.add(DataModel(107, "Sinotec", 2,0))//2
        beanObjectList!!.add(DataModel(108, "Skyworth", 2,0))//2
        beanObjectList!!.add(DataModel(109, "Soniq", 4,0))//4
        beanObjectList!!.add(DataModel(110, "SONY", 7,0))//7
        beanObjectList!!.add(DataModel(111, "Supra", 4,0))//4
        beanObjectList!!.add(DataModel(112, "Sylvania", 3,0))//3
        beanObjectList!!.add(DataModel(113, "Symphonic", 1,0))//1
        beanObjectList!!.add(DataModel(114, "TataSKY STB", 1,0))//1
        beanObjectList!!.add(DataModel(115, "TelStar STB", 1,0))//1
        beanObjectList!!.add(DataModel(116, "TCL", 1,0))//1
        beanObjectList!!.add(DataModel(117, "Teac", 1,0))//1
        beanObjectList!!.add(DataModel(118, "Technika", 3,0))//3
        beanObjectList!!.add(DataModel(119, "Telefunken", 1,0))//1
        beanObjectList!!.add(DataModel(120, "Thomson", 2,0))//2
        beanObjectList!!.add(DataModel(121, "Toshiba", 1,0))//1
        beanObjectList!!.add(DataModel(122, "Venturer", 1,0))//1
        beanObjectList!!.add(DataModel(123, "Veon", 1,0))//1
        beanObjectList!!.add(DataModel(124, "Vestel", 1,0))//1
        beanObjectList!!.add(DataModel(125, "Videocon", 1,0))//1
        beanObjectList!!.add(DataModel(126, "Videocon STB", 2,0))//2
        beanObjectList!!.add(DataModel(127, "Viore", 2,0))//2
        beanObjectList!!.add(DataModel(128, "Vivax", 1,0))//1
        beanObjectList!!.add(DataModel(129, "Vizio", 1,0))//1
        beanObjectList!!.add(DataModel(130, "VU", 1,0))//1
        beanObjectList!!.add(DataModel(131, "UMC", 1,0))//1
        beanObjectList!!.add(DataModel(132, "Wansa", 1,0))//1
        beanObjectList!!.add(DataModel(133, "Westinghouse", 3,0))//3
        beanObjectList!!.add(DataModel(134, "Wharfedale", 3,0))//3
        beanObjectList!!.add(DataModel(135, "Zenith", 5,0))//5

    }


    private fun filterRecyclerView(charText: String) {
        var charText = charText
        charText = charText.toLowerCase()
        clearDataSet()
        //menuItem= beanObjectList as ArrayList<DataModel>

        if (charText.length == 0) {
            menuItem!!.addAll(mDataset!!)
        } else {
            for (user in mDataset!!) {
                if (user.title.toLowerCase().contains(charText)) {
                    beanObjectList!!.add(user)
                }
            }
        }
        recyclerView!!.adapter!!.notifyDataSetChanged()

    }

    private fun clearDataSet() {
        menuItem!!.clear()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        mAdMobInterstitialAd!!.loadAd(adBRequest)
        super.onResume()
    }
}
